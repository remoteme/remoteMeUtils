package it.sajdak.remoteme.utils.general;

import java.util.Objects;

public class Solo<T1> {
	T1 first;

	public T1 get() {
		return first;
	}

	public void set(T1 first) {
		this.first = first;
	}

	public Solo() {
	}

	public Solo(T1 first) {
		this.first = first;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Solo)) return false;
		Solo<?> solo = (Solo<?>) o;
		return Objects.equals(first, solo.first);
	}

	@Override
	public int hashCode() {

		return Objects.hash(first);
	}
}
