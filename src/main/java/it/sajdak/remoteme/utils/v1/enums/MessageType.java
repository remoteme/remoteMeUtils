package it.sajdak.remoteme.utils.v1.enums;

import it.sajdak.remoteme.utils.general.Id_Enum;
import it.sajdak.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum MessageType implements Id_Enum<MessageType> {

		USER_MESSAGE(100), USER_MESSAGE_DELIVER_STATUS(101),
		USER_SYNC_MESSAGE(102),

		SYNC_MESSAGE(120), SYNC_MESSAGE_RESPONSE(121),



		REGISTER_DEVICE(200), REGISTER_CHILD_DEVICE(201),
		ADD_DATA(300),


		LOG(20000),//systems
		SYSTEM_MESSAGE(20001);

		private final int id;

		MessageType(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		static SparseArrayIdEnum<MessageType> array = new SparseArrayIdEnum(MessageType.class);


		public static List<MessageType> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static MessageType getById(int id) {
			return Id_Enum.getListInner(id, array);
		}


	}

