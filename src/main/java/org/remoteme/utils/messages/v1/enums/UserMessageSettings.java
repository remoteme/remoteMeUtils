package org.remoteme.utils.messages.v1.enums;



import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;


public enum UserMessageSettings implements Id_Enum<MessageType> {
    NO_RENEWAL(0), RENEWAL_IF_FAILED(1);


		private final int id;

		UserMessageSettings(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}

		static SparseArrayIdEnum<UserMessageSettings> array = new SparseArrayIdEnum(UserMessageSettings.class);


		public static List<UserMessageSettings> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static UserMessageSettings getById(int id) {
			return Id_Enum.getListInner(id, array);
		}

	}