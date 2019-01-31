package com.yahoo.ailiashenko.mod3;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyQueue<E> implements Queue<E> {

	private final int size;
	private E[] elements;
	private int cursorBegin;
	private int cursorEnd;

	public void printState() {
		System.out.println(
				"CURSOR BEGIN: " + cursorBegin + "\nCURSOR END: " + cursorEnd + "\n" + Arrays.asList(elements));
	}

	public MyQueue() {
		super();
		this.size = 0;
	}

	public MyQueue(int size) {
		super();
		if (size <= 0) {
			throw new IllegalArgumentException("Size cannot be less than 1");
		}
		this.cursorBegin = -1;
		this.cursorEnd = -1;
		this.size = size;
		this.elements = (E[]) new Object[size];
	}

	@Override
	public int size() {
		if (cursorBegin == -1 && cursorEnd == -1) {
			return 0;
		}
		return cursorEnd - cursorBegin + 1;
	}

	@Override
	public boolean isEmpty() {
		return (cursorBegin == -1 && cursorEnd == -1);
	}

	public boolean isFull() {
		return (cursorEnd + 1) % size == cursorBegin;
	}

	@Override
	public boolean contains(Object o) {
		if (o == null) {
			throw new IllegalArgumentException("Object cannot be null");
		}
		return Arrays.asList(elements).contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		MyQueueIterator iterator = new MyQueueIterator();
		return iterator;
	}

	@Override
	public Object[] toArray() {
		return elements;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return (T[]) elements;
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (int counter = 0; counter < c.size(); counter++) {
			if (!contains(c.toArray()[counter])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c.size() > size - cursorEnd - 1) {
			return false;
		}
		for (int i = 0; i < c.size(); i++) {
			add((E) c.toArray()[i]);
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		Arrays.fill(elements, null);
	}

	@Override
	public boolean add(E e) {
		if (isFull()) {
			throw new IllegalStateException("Queue is Full");
		}
		return offer(e);
	}

	@Override
	public boolean offer(E e) {
		if (isFull()) {
			return false;
		} else if (isEmpty()) {
			cursorBegin++;
			cursorEnd++;
			elements[cursorEnd] = e;
		} else {
			cursorEnd = (cursorEnd + 1) % size;
			elements[cursorEnd] = e;
		}
		return true;
	}

	@Override
	public E remove() {
		if (isEmpty()) {
			throw new IllegalStateException("Queue is Empty");
		}
		return poll();
	}

	@Override
	public E poll() {
		E data;
		if (isEmpty()) {
			return null;
		} else if (cursorBegin == cursorEnd) {
			data = elements[cursorBegin];
			elements[cursorBegin] = null;
			cursorBegin = -1;
			cursorBegin = -1;
		} else {
			data = elements[cursorBegin];
			elements[cursorBegin] = null;
			cursorBegin = (cursorBegin + 1) % size;
		}
		return data;
	}

	@Override
	public E element() {
		if (isFull()) {
			throw new IllegalStateException("Queue is Empty");
		}
		return elements[0];
	}

	@Override
	public E peek() {
		if (isEmpty()) {
			return null;
		} else {
			return elements[0];
		}
	}

	@Override
	public String toString() {
		return "MyQueue: " + Arrays.toString(elements);
	}

	private class MyQueueIterator implements Iterator<E> {

		private int index;

		public MyQueueIterator() {
			this.index = cursorBegin;
		}

		@Override
		public boolean hasNext() {
			return index < size();
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("There are no more elements");
			}
			E element = elements[index];
			index++;
			return element;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Cannot remove this element");
		}
	}

	class MyStringQueue {

		private MyQueue<E> queue;
		private Collator ukCollator = Collator.getInstance(new Locale("uk"));

		public MyStringQueue(int size) {
			this.queue = new MyQueue<>(size);
		}

		private Pattern pattern = Pattern.compile("(^|\\s)\\p{Lu}.*");

		public void printState() {
			queue.printState();
		}

		public boolean addWithRegexCheck(String s) {
			Matcher m = pattern.matcher(s);
			if (m.matches()) {
				return queue.add((E) s);
			}
			return false;
		}

		public void printInOrder() {
			ukCollator.setStrength(Collator.PRIMARY);
			List<String> temp = Arrays.asList(Arrays.copyOf(this.queue.elements, this.queue.size(), String[].class));
			temp.removeIf(Objects::isNull);
			Collections.sort(temp, (String o1, String o2) -> ukCollator.compare(o1, o2));
			System.out.println(temp.toString());
		}

	}
}