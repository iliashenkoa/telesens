package com.yahoo.ailiashenko.mod2;

public class NetworkSegment<T> {
	private T begin;
	private T end;
	private int distance;
	private int time;

	public NetworkSegment() {

	}

	public NetworkSegment(T begin, T end, int distance, int time) {
		super();
		isReal(begin, end);
		this.begin = begin;
		this.end = end;
		this.distance = distance;
		this.time = time;
	}

	public void isReal(T begin, T end) {
		if (begin.equals(end))
			throw new IllegalArgumentException("Begin station and end station same objects!");
	}

	public T getBegin() {
		return begin;
	}

	public void setBegin(T begin) {
		this.begin = begin;
	}

	public T getEnd() {
		return end;
	}

	public void setEnd(T end) {
		this.end = end;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getSpeed() {
		return (double) distance / time;
	}

	@Override
	public String toString() {
		return "NetworkSegment: from " + begin.toString() + " to " + end.toString() + "; distance = " + distance
				+ ", time of movement = " + time;
	}

}
