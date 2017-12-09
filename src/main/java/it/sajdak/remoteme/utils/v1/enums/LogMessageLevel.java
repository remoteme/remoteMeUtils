package it.sajdak.remoteme.utils.v1.enums;

import it.sajdak.remoteme.utils.general.Id_Enum;
import it.sajdak.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum LogMessageLevel implements Id_Enum<MessageType>  {
    INFO(1), WARN(2),ERROR(3);


		private final int id;

		LogMessageLevel(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}

		static SparseArrayIdEnum<LogMessageLevel> array = new SparseArrayIdEnum(LogMessageLevel.class);


		public static List<LogMessageLevel> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static LogMessageLevel getById(int id) {
			return Id_Enum.getListInner(id, array);
		}

	}