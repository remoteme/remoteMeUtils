package org.remoteme.utils.messages.v1.enums.variables;

import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum VariableHistoryRound implements Id_Enum<VariableHistoryRound> {
    NO_ROUND(0),

		_1S(1),_2S(2),_5S(3),_10S(4),_15S(5),_20S(6),_30S(7),_1M(8),_5M(9);


		private final int id;

		VariableHistoryRound(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}

		static SparseArrayIdEnum<VariableHistoryRound> array = new SparseArrayIdEnum(VariableHistoryRound.class);


		public static List<VariableHistoryRound> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static VariableHistoryRound getById(int id) {
			return Id_Enum.getListInner(id, array);
		}

	}
