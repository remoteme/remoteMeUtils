package org.remoteme.utils.messages.v1.enums;



import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;


public enum SystemMessageType implements Id_Enum<MessageType> {
    RESTART(1),
	DEVICE_CONNECT_CHANGE(2);//deviceid short then byte 0 not connected 1 connected sent to server


	private final int id;

	SystemMessageType(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

	static SparseArrayIdEnum<SystemMessageType> array = new SparseArrayIdEnum(SystemMessageType.class);


	public static List<SystemMessageType> getList(Collection<Integer> ids) {
		return Id_Enum.getListInner(ids, array);
	}

	public static SystemMessageType getById(int id) {
		return Id_Enum.getListInner(id, array);
	}

}