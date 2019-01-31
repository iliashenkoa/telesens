package com.yahoo.ailiashenko.mod3;

import java.text.ParseException;
import java.util.Iterator;

public class Demo {
	public static void main(String[] args) throws ParseException {
		MyQueue<Integer> q = new MyQueue<>(15);
		q.printState();
		q.add(1);
		q.printState();
		q.add(2);
		q.add(3);
		q.add(4);
		Iterator<Integer> i = q.iterator();

		while (i.hasNext()) {
			System.out.println(i.next());
		}

		q.remove();
		q.printState();
		Iterator<Integer> ii = q.iterator();

		System.out.println();

		while (ii.hasNext()) {
			System.out.println(ii.next());
		}

		MyQueue<String>.MyStringQueue stringQ = new MyQueue<String>().new MyStringQueue(15);
		stringQ.printState();
		stringQ.addWithRegexCheck("Anna");
		stringQ.printState();
		stringQ.addWithRegexCheck("old");
		stringQ.printState();
		stringQ.addWithRegexCheck("Їжа");
		stringQ.addWithRegexCheck("Яма");
		stringQ.addWithRegexCheck("Арка");
		stringQ.addWithRegexCheck("Єва");
		stringQ.printState();
		stringQ.printInOrder();

	}
}
