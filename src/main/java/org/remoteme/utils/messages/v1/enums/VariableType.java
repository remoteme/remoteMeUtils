package org.remoteme.utils.messages.v1.enums;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum VariableType implements Id_Enum<VariableType> {
    	BOOLEAN(0), INTEGER(1),TEXT(2),SMALL_INTEGER_3(3),SMALL_INTEGER_2(4),INTEGER_BOOLEAN(5),DOUBLE(6),TEXT_2(7);


		private final int id;

		VariableType(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}

		static SparseArrayIdEnum<VariableType> array = new SparseArrayIdEnum(VariableType.class);


		public static List<VariableType> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static VariableType getById(int id) {
			return Id_Enum.getListInner(id, array);
		}

	}