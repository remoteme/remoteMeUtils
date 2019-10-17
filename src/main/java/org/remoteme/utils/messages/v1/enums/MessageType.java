package org.remoteme.utils.messages.v1.enums;


import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum MessageType implements Id_Enum<MessageType> {

		PING(0,true), AUTHENTIFICATE(50,false),
		USER_MESSAGE(100,false),USER_MESSAGE_GUEST(108,true), USER_MESSAGE_DELIVER_STATUS(101,true),
		USER_SYNC_MESSAGE(102,false), USER_SYNC_MESSAGE_GUEST(109,true),
		VARIABLE_CHANGE_MESSAGE(103,true), VARIABLE_CHANGE_PROPAGATE_MESSAGE(104,false), SEND_PUSH_NOTIFICATION(105,false),
		VARIABLE_ADD_HISTORY_MESSAGE(111,false),
		VARIABLE_CHANGE_PROPAGATE_MESSAGE_GUEST(107,true),

		SET_VARIABLE_SCHEDULER_STATE(106,true),

		DECREASE_GUEST_CREDIT_AND_TIME(110,true),


		SYNC_MESSAGE(120,false), SYNC_MESSAGE_RESPONSE(121,false),
		VARIABLE_OBSERVE_MESSAGE(122,true),


		REGISTER_DEVICE(200,true), REGISTER_CHILD_DEVICE(201,false),

		DEVICE_CONNECTION_CHANGE(301,true),
		VARIABLE_SCHEDULER_STATE_CHANGE(302,false),
		DEVICE_FILE_CHANGE(303,false),

		ADD_DATA(300,false),
		UPDATEFILE(400,false), //?? used in rpi
		SET_FILE_CONTENT(500,false),


		LOG(20000,false),//systems
		SYSTEM_MESSAGE(20001,true),
		WEB_RTC_CONNECTION_CHANGE(20002,true),

		EVENT_SUBSCRIBER(20003,true);

		private final int id;
		private final boolean allowGuestMode;

	MessageType(int id,boolean allowGuestMode) {
			this.id = id;
			this.allowGuestMode=allowGuestMode;
		}


	public boolean isAllowGuestMode() {
		return allowGuestMode;
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


