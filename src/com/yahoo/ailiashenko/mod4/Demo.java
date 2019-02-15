package com.yahoo.ailiashenko.mod4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {
	private static volatile AtomicInteger atomic = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {
		Map<String, BasicFileAttributes> f = new ConcurrentSkipListMap<>();

		Runnable task1 = () -> {
			// searchFilesFolderSubF(f, "/home/alex/Документы/temp/", "*.{txt,doc}");
			searchFilesFolderSubF(f, "/home/alex", "*.{txt,doc}");

		};
		Thread t1 = new Thread(task1);
		Runnable task2 = () -> {
			searchFilesBySize(f, 1);
		};
		Runnable task3 = () -> {
			searchFilesByAttr(f, "2019-02-13T16:56:20.000Z");
		};
		Runnable task4 = () -> {
			searchTextInFiles(f, "-");
		};

		new Thread(task1).start(); // запуск первой нити по поиску файлов в каталоге/подкаталогах с расширением
									// .doc, .txt

		ScheduledExecutorService ses = Executors.newScheduledThreadPool(3); // пул потоков с расписанием
		ScheduledFuture<?> scheduledFuture2 = ses.scheduleAtFixedRate(task2, 10, 10, TimeUnit.NANOSECONDS); //опрос каждые 10 наносекунд / задержка - 10 нс 
		ScheduledFuture<?> scheduledFuture3 = ses.scheduleAtFixedRate(task3, 10, 10, TimeUnit.NANOSECONDS);
		ScheduledFuture<?> scheduledFuture4 = ses.scheduleAtFixedRate(task4, 10, 10, TimeUnit.NANOSECONDS);

		while (true) {
			System.out.println("count :" + atomic); // вывод счетчика количества найденых файлов
			if (atomic.get() > 0) { // если найден хоть 1 файл, то начинается выполнение
				System.out.println("RESULT COUNT :" + atomic);
				Thread.sleep(100); // даем 0,1 с на обработку найденых файлов
				scheduledFuture2.cancel(true); // завершаем выполнение наших нитей
				scheduledFuture3.cancel(true);
				scheduledFuture4.cancel(true);
				ses.shutdown();
				System.out.println("All tasks are done");
				break;
			}
		}
	}

	private static void searchFilesFolderSubF(Map<String, BasicFileAttributes> files, String folder, String pattern) {
		Path startDir = Paths.get(folder);
		FileSystem fs = FileSystems.getDefault();
		final PathMatcher matcher = fs.getPathMatcher("glob:" + pattern);

		FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) throws IOException {
				Path name = file.getFileName();
				if (matcher.matches(name)) {
					System.out.print(String.format("Found matched file: '%s'.%n", file));
					files.put(file.toString(), Files.readAttributes(file, BasicFileAttributes.class));
					atomic.addAndGet(1);
					System.out.println(atomic);
				}
				return FileVisitResult.CONTINUE;
			}
		};
		try {
			Files.walkFileTree(startDir, matcherVisitor);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private static void searchFilesBySize(Map<String, BasicFileAttributes> files, long size) {
		String result = "";
		for (Map.Entry<String, BasicFileAttributes> entry : files.entrySet()) {
			if (entry.getValue().size() > size) {
				result += entry.getKey() + "\n";
			}
		}
		fileCreator(System.getProperty("user.dir") + "/res/tmpSize.txt", result);
	}

	private static void searchFilesByAttr(Map<String, BasicFileAttributes> files, String attr) {
		String result = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date dateAttr = null;
		try {
			dateAttr = df.parse(attr);
		} catch (ParseException e) {
			System.err.println(e);
		}

		for (Map.Entry<String, BasicFileAttributes> entry : files.entrySet()) {
			if (entry.getValue().creationTime().toMillis() == dateAttr.getTime()) {
				result += entry.getKey() + "\n";
			}
		}
		fileCreator(System.getProperty("user.dir") + "/res/tmpAttr.txt", result);
	}

	private static void searchTextInFiles(Map<String, BasicFileAttributes> files, String text) {
		Scanner txtscan;
		String result = "";
		for (Map.Entry<String, BasicFileAttributes> entry : files.entrySet()) {
			try {
				txtscan = new Scanner(new File(entry.getKey()));
				while (txtscan.hasNextLine()) {
					String str = txtscan.nextLine();
					if (str.indexOf(text) != -1) {
						result += entry.getKey() + "\n";
					}
				}
			} catch (FileNotFoundException e) {
				System.err.println(e);
			}
		}
		fileCreator(System.getProperty("user.dir") + "/res/tmpText.txt", result);
	}

	private static void fileCreator(String filename, String fileContent) {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e1) {
			System.err.println(e1);
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
			writer.append(' ');
			writer.append(fileContent);
		} catch (IOException e) {
			System.err.println(e);
		}

	}

}
