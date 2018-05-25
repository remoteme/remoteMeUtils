package it.sajdak.remoteme.utils.general;

import java.util.Objects;

public class Single<T1> {
	T1 first;

	public T1 get() {
		return first;
	}

	public void set(T1 first) {
		this.first = first;
	}

	public Single() {
	}

	public Single(T1 first) {
		this.first = first;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Single)) return false;
		Single<?> single = (Single<?>) o;
		return Objects.equals(first, single.first);
	}

	@Override
	public int hashCode() {

		return Objects.hash(first);
	}
}
