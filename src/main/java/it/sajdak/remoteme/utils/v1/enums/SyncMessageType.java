package it.sajdak.remoteme.utils.v1.enums;

import it.sajdak.remoteme.utils.general.Id_Enum;
import it.sajdak.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum SyncMessageType implements Id_Enum<SyncMessageType> {

	USER(0),
	GET_WEBRTC_CONENCTED_DEVICE_ID(1);// 1 byte  1 - there is devivce 2 other bytes  deviceId 3-7 32int = sessionId

	private final int id;


	SyncMessageType(int id ) {
		this.id = id;

	}
	public int getId() {
		return id;
	}


	static SparseArrayIdEnum<DeviceType> array = new SparseArrayIdEnum(SyncMessageType.class);

	public static List<SyncMessageType> getList(Collection<Integer> ids) {
		return Id_Enum.getListInner(ids, array);
	}

	public static SyncMessageType getById(int id) {
		return Id_Enum.getListInner(id, array);
	}


}