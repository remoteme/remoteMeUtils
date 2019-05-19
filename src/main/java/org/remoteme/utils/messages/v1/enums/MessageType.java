package org.remoteme.utils.messages.v1.enums;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum MessageType implements Id_Enum<MessageType> {

		PING(0), AUTHENTIFICATE(50),
		USER_MESSAGE(100), USER_MESSAGE_DELIVER_STATUS(101),
		USER_SYNC_MESSAGE(102), VARIABLE_CHANGE_MESSAGE(103), VARIABLE_CHANGE_PROPAGATE_MESSAGE(104), SEND_PUSH_NOTIFICATION(105),

		SET_VARIABLE_SCHEDULER_STATE(106),

		SYNC_MESSAGE(120), SYNC_MESSAGE_RESPONSE(121),
		VARIABLE_OBSERVE_MESSAGE(122),


		REGISTER_DEVICE(200), REGISTER_CHILD_DEVICE(201),

		DEVICE_CONNECTION_CHANGE(301),
		VARIABLE_SCHEDULER_STATE_CHANGE(302),
		DEVICE_FILE_CHANGE(303),

		ADD_DATA(300),
		UPDATEFILE(400), //?? used in rpi
		SET_FILE_CONTENT(500),


		LOG(20000),//systems
		SYSTEM_MESSAGE(20001),
		WEB_RTC_CONNECTION_CHANGE(20002),

		EVENT_SUBSCRIBER(20003);
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


