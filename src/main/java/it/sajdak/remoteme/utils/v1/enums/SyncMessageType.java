package it.sajdak.remoteme.utils.v1.enums;

import it.sajdak.remoteme.utils.general.Id_Enum;
import it.sajdak.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;

public enum SyncMessageType implements Id_Enum<SyncMessageType> {

	USER(0),

	GET_WEBRTC_CONENCTED_DEVICE_ID(1),//reponse // 1 byte  1 - there is devivce 2 other bytes  deviceId 3-7 32int = sessionId
	GET_FILES(2),// response files name as string with 0 at end of string
	GET_FILE_CONTENT(3),// request filename int32 offset short maxByteSize - response zlib file content
	SAVE_FILE_CONTENT(4), // request (byte) 1 - append 0 addtoNew, filename, uncompreesedsize (int32),  zlib
	REMOVE_FILE(5),// request filename
	GET_FILE_SIZE(6);// request filename reponse byte 1-noproblem 0 problem, int32 size in bytes
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