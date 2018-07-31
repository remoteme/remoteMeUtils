package org.remoteme.utils.general;

public class Pair<T1, T2> {
	T1 first;
	T2 second;

	public Pair() {

	}

	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}

	public void setFirst(T1 first) {
		this.first = first;
	}

	public void setSecond(T2 second) {
		this.second = second;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair) {
			Pair temp = (Pair) obj;
			return (first == null ? temp.getFirst() == null : first.equals(temp.getFirst())) && (second == null ? temp.getSecond() == null : second.equals(temp.getSecond()));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = second != null ? second.hashCode() : 0;
		hashCode = first != null ? (int) (hashCode + first.hashCode()) : hashCode;

		return hashCode;
	}

	@Override
	public String toString() {
		return "{" + (this.first == null ? "null" : first.toString()) + ", " + (this.second == null ? "null" : second.toString()) + "}";
	}

}
