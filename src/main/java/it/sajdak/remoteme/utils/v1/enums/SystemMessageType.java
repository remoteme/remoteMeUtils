package it.sajdak.remoteme.utils.v1.enums;


import it.sajdak.remoteme.utils.general.Id_Enum;
import it.sajdak.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;


public enum SystemMessageType implements Id_Enum<MessageType>  {
    RESTART(1);


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