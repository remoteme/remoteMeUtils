package it.sajdak.remoteme.utils.general;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Admin on 2016-04-29.
 */
public class SparseArray<E> implements Map<Integer, E> {

	private static final Object DELETED = new Object();
	private boolean mGarbage = false;
	protected int[] mKeys;
	protected Object[] mValues;
	protected int mSize;

	/**
	 * Creates a new SparseArray containing no mappings.
	 */


	public SparseArray(int initialCapacity) {
		if (initialCapacity == 0) {
			initialCapacity = 10;
		}

		mKeys = new int[initialCapacity];
		mValues = new Object[initialCapacity];

		mSize = 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public SparseArray<E> clone() {
		SparseArray<E> clone = null;
		try {
			clone = (SparseArray<E>) super.clone();
			clone.mKeys = mKeys.clone();
			clone.mValues = mValues.clone();
		} catch (CloneNotSupportedException cnse) {
			/* ignore */
		}
		return clone;
	}

	/**
	 * Gets the Object mapped from the specified key, or <code>null</code>
	 * if no such mapping has been made.
	 */
	@Override
	public E get(Object key) {
		return get((int) key, null);
	}

	/**
	 * Gets the Object mapped from the specified key, or the specified Object
	 * if no such mapping has been made.
	 */
	@SuppressWarnings("unchecked")
	public E get(int key, E valueIfKeyNotFound) {
		int i = binarySearch(mKeys, mSize, key);
		if (i < 0 || mValues[i] == DELETED) {
			return valueIfKeyNotFound;
		} else {
			return (E) mValues[i];
		}
	}

	/**
	 * Removes the mapping from the specified key, if there was any.
	 */
	@Override
	public E remove(Object key) {

		int i = binarySearch(mKeys, mSize, (int) key);
		if (i >= 0) {
			if (mValues[i] != DELETED) {
				E mValue = (E) mValues[i];
				mValues[i] = DELETED;
				mGarbage = true;
				return mValue;
			}
		}
		return null;
	}

	/**
	 * Removes the mapping at the specified index.
	 */
	public void removeAt(int index) {
		if (mValues[index] != DELETED) {
			mValues[index] = DELETED;
			mGarbage = true;
		}
	}

	/**
	 * Remove a range of mappings as a batch.
	 *
	 * @param index Index to begin at
	 * @param size  Number of mappings to remove
	 */
	public void removeAtRange(int index, int size) {
		final int end = Math.min(mSize, index + size);
		for (int i = index; i < end; i++) {
			removeAt(i);
		}
	}

	private void gc() {
		// Log.e("SparseArray", "gc start with " + mSize);
		int n = mSize;
		int o = 0;
		int[] keys = mKeys;
		Object[] values = mValues;
		for (int i = 0; i < n; i++) {
			Object val = values[i];
			if (val != DELETED) {
				if (i != o) {
					keys[o] = keys[i];
					values[o] = val;
					values[i] = null;
				}
				o++;
			}
		}
		mGarbage = false;
		mSize = o;
		// Log.e("SparseArray", "gc end with " + mSize);
	}

	/**
	 * Adds a mapping from the specified key to the specified value,
	 * replacing the previous mapping from the specified key if there
	 * was one.
	 */
	@Override
	public E put(Integer key, E value) {
		int i = binarySearch(mKeys, mSize, key);
		if (i >= 0) {
			mValues[i] = value;
		} else {
			i = ~i;
			if (i < mSize && mValues[i] == DELETED) {
				mKeys[i] = key;
				mValues[i] = value;
				return value;
			}
			if (mGarbage && mSize >= mKeys.length) {
				gc();
				// Search again because indices may have changed.
				i = ~binarySearch(mKeys, mSize, key);
			}
			if (mSize >= mKeys.length) {
				int n = idealIntArraySize(mSize + 1);
				int[] nkeys = new int[n];
				Object[] nvalues = new Object[n];
				// Log.e("SparseArray", "grow " + mKeys.length + " to " + n);
				System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
				System.arraycopy(mValues, 0, nvalues, 0, mValues.length);
				mKeys = nkeys;
				mValues = nvalues;
			}
			if (mSize - i != 0) {
				// Log.e("SparseArray", "move " + (mSize - i));
				System.arraycopy(mKeys, i, mKeys, i + 1, mSize - i);
				System.arraycopy(mValues, i, mValues, i + 1, mSize - i);
			}
			mKeys[i] = key;
			mValues[i] = value;
			mSize++;
		}
		return value;
	}

	private int idealIntArraySize(int i) {
		return (int) Math.ceil((i + 1) * 1.5);
	}

	/**
	 * Returns the number of key-value mappings that this SparseArray
	 * currently stores.
	 */
	public int size() {
		if (mGarbage) {
			gc();
		}
		return mSize;
	}


	/**
	 * Given an index in the range <code>0...size()-1</code>, returns
	 * the key from the <code>index</code>th key-value mapping that this
	 * SparseArray stores.
	 * <p>
	 * <p>The keys corresponding to indices in ascending order are guaranteed to
	 * be in ascending order, e.g., <code>keyAt(0)</code> will return the
	 * smallest key and <code>keyAt(size()-1)</code> will return the largest
	 * key.</p>
	 */
	public int keyAt(int index) {
		if (mGarbage) {
			gc();
		}
		return mKeys[index];
	}

	/**
	 * Given an index in the range <code>0...size()-1</code>, returns
	 * the value from the <code>index</code>th key-value mapping that this
	 * SparseArray stores.
	 * <p>
	 * <p>The values corresponding to indices in ascending order are guaranteed
	 * to be associated with keys in ascending order, e.g.,
	 * <code>valueAt(0)</code> will return the value associated with the
	 * smallest key and <code>valueAt(size()-1)</code> will return the value
	 * associated with the largest key.</p>
	 */
	@SuppressWarnings("unchecked")
	public E valueAt(int index) {
		if (mGarbage) {
			gc();
		}
		return (E) mValues[index];
	}

	/**
	 * Given an index in the range <code>0...size()-1</code>, sets a new
	 * value for the <code>index</code>th key-value mapping that this
	 * SparseArray stores.
	 */
	public void setValueAt(int index, E value) {
		if (mGarbage) {
			gc();
		}
		mValues[index] = value;
	}

	/**
	 * Returns the index for which {@link #keyAt} would return the
	 * specified key, or a negative number if the specified
	 * key is not mapped.
	 */
	public int indexOfKey(int key) {
		if (mGarbage) {
			gc();
		}
		return binarySearch(mKeys, mSize, key);
	}

	/**
	 * Returns an index for which {@link #valueAt} would return the
	 * specified key, or a negative number if no keys map to the
	 * specified value.
	 * <p>Beware that this is a linear search, unlike lookups by key,
	 * and that multiple keys can map to the same value and this will
	 * find only one of them.
	 * <p>Note also that unlike most collections' {@code indexOf} methods,
	 * this method compares values using {@code ==} rather than {@code equals}.
	 */
	public int indexOfValue(E value) {
		if (mGarbage) {
			gc();
		}
		for (int i = 0; i < mSize; i++)
			if (mValues[i] == value)
				return i;
		return -1;
	}

	/**
	 * Removes all key-value mappings from this SparseArray.
	 */
	public void clear() {
		int n = mSize;
		Object[] values = mValues;
		for (int i = 0; i < n; i++) {
			values[i] = null;
		}
		mSize = 0;
		mGarbage = false;
	}


	/**
	 * Puts a key/value pair into the array, optimizing for the case where
	 * the key is greater than all existing keys in the array.
	 */
	public void append(Integer key, E value) {
		if (mSize != 0 && key <= mKeys[mSize - 1]) {
			put(key, value);
			return;
		}
		if (mGarbage && mSize >= mKeys.length) {
			gc();
		}
		int pos = mSize;
		if (pos >= mKeys.length) {
			int n = idealIntArraySize(pos);
			int[] nkeys = new int[n];
			Object[] nvalues = new Object[n];
			// Log.e("SparseArray", "grow " + mKeys.length + " to " + n);
			System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
			System.arraycopy(mValues, 0, nvalues, 0, mValues.length);
			mKeys = nkeys;
			mValues = nvalues;
		}
		mKeys[pos] = key;
		mValues[pos] = value;
		mSize = pos + 1;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * <p>This implementation composes a string by iterating over its mappings. If
	 * this map contains itself as a value, the string "(this Map)"
	 * will appear in its place.
	 */
	@Override
	public String toString() {
		if (size() <= 0) {
			return "{}";
		}
		StringBuilder buffer = new StringBuilder(mSize * 28);
		buffer.append('{');
		for (int i = 0; i < mSize; i++) {
			if (i > 0) {
				buffer.append(", ");
			}
			int key = keyAt(i);
			buffer.append(key);
			buffer.append('=');
			Object value = valueAt(i);
			if (value != this) {
				buffer.append(value);
			} else {
				buffer.append("(this Map)");
			}
		}
		buffer.append('}');
		return buffer.toString();
	}


	static int binarySearch(int[] array, int size, int value) {
		int lo = 0;
		int hi = size - 1;
		while (lo <= hi) {
			final int mid = (lo + hi) >>> 1;
			final int midVal = array[mid];
			if (midVal < value) {
				lo = mid + 1;
			} else if (midVal > value) {
				hi = mid - 1;
			} else {
				return mid;  // value found
			}
		}
		return ~lo;  // value not present
	}


	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new HashSet<>();

		for (int i = 0; i < size(); i++) {
			if (mValues[i] != DELETED) {
				ret.add(mKeys[i]);
			}
		}
		return ret;
	}

	@Override
	public Collection<E> values() {
		Set<E> ret = new HashSet<>();
		for (int i = 0; i < size(); i++) {
			if (mValues[i] != DELETED) {
				ret.add((E) mValues[i]);
			}
		}
		return ret;
	}

	@Override
	public Set<Entry<Integer, E>> entrySet() {
		Set<Entry<Integer, E>> ret = new HashSet<>();

		for (int i = 0; i < size(); i++) {
			if (mValues[i] != DELETED) {
				ret.add(new AbstractMap.SimpleEntry<Integer, E>(mKeys[i], (E) mValues[i]));
			}
		}
		return ret;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object o) {
		return keySet().contains(o);
	}

	@Override
	public boolean containsValue(Object o) {
		return values().contains(o);
	}


	@Override
	public void putAll(Map<? extends Integer, ? extends E> map) {

	}


}
