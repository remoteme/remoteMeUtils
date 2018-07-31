package org.remoteme.utils.general;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Admin on 2016-04-28.
 */
public interface Id_Enum<T extends Id_Enum> {
	String name();

	int getId();



	static <T extends Id_Enum> T getListInner(Integer id, SparseArrayIdEnum arrays) {
		T t = (T) arrays.get(id);
		if (t == null) {
			throw new NullPointerException();
		} else {
			return t;
		}
	}

	static <T extends Id_Enum> List<T> getListInner(Collection<Integer> ids, SparseArrayIdEnum arrays) {
		List<T> ret = new ArrayList<>();
		for (Integer id : ids) {
			ret.add(getListInner(id, arrays));
		}
		return ret;

	}

	static <T extends Id_Enum> List<T> getAll(Class<T> clazz) {
		List<T> ret = new ArrayList<>();

		try {
			for (T values : ((T[]) clazz.getMethod("values").invoke(clazz))) {
				ret.add(values);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}


	static <T extends Id_Enum> List<Integer> convertFrom(Collection<T> list, Class<T> clazz) {
		List<Integer> ret = new ArrayList<>(list.size());
		for (T t : list) {
			ret.add(t.getId());
		}

		return ret;
	}
}
