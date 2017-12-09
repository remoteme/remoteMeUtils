package it.sajdak.remoteme.utils.general;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016-04-29.
 */
public class SparseArrayIdEnum<E extends Id_Enum> extends SparseArray<E> {


	public SparseArrayIdEnum(Class<E> clazz) {
		super(Id_Enum.getAll(clazz).size());

		List<Pair<Integer, E>> temp = new ArrayList<>();

		for (E e : Id_Enum.getAll(clazz)) {
			temp.add(new Pair<>(e.getId(), e));
		}
		temp.sort((t0, t1) -> t0.getFirst().compareTo(t1.getFirst()));

		for (int i = 0; i < temp.size(); i++) {
			mKeys[i] = temp.get(i).getFirst();
			mValues[i] = temp.get(i).getSecond();

		}

		mSize = temp.size();

	}


}
