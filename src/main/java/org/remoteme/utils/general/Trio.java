package org.remoteme.utils.general;

import java.util.Objects;

public class Trio<T1, T2,T3> {
	T1 first;
	T2 second;
	T3 third;

	public Trio() {

	}

	public T1 getFirst() {
		return first;
	}

	public void setFirst(T1 first) {
		this.first = first;
	}

	public T2 getSecond() {
		return second;
	}

	public void setSecond(T2 second) {
		this.second = second;
	}

	public T3 getThird() {
		return third;
	}

	public void setThird(T3 third) {
		this.third = third;
	}

	public Trio(T1 first, T2 second, T3 third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Trio)) return false;
		Trio<?, ?, ?> trio = (Trio<?, ?, ?>) o;
		return Objects.equals(getFirst(), trio.getFirst()) &&
				Objects.equals(getSecond(), trio.getSecond()) &&
				Objects.equals(getThird(), trio.getThird());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getFirst(), getSecond(), getThird());
	}
}
