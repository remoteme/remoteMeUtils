package org.remoteme.utils.messages.v1.core.messages.observers;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum VariableOberverType implements Id_Enum<VariableOberverType> {
    	BOOLEAN(0), INTEGER(1),TEXT(2),SMALL_INTEGER_3(3),SMALL_INTEGER_2(4),INTEGER_BOOLEAN(5),DOUBLE(6),TEXT_2(7);


		private final int id;

		VariableOberverType(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}

		static SparseArrayIdEnum<VariableOberverType> array = new SparseArrayIdEnum(VariableOberverType.class);


		public static List<VariableOberverType> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static VariableOberverType getById(int id) {
			return Id_Enum.getListInner(id, array);
		}

	}