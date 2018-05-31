package it.sajdak.remoteme.utils.v1.enums;


import it.sajdak.remoteme.utils.general.Id_Enum;
import it.sajdak.remoteme.utils.general.SparseArrayIdEnum;

import java.util.Collection;
import java.util.List;


public enum WebRTCConnectionStatus implements Id_Enum<MessageType>  {
	CONNECTED(0),
	DISCONNECTED(1),
	FAILED(2),
	CONNECTING(3),
	DISCONNECTING(4),
	CHECKING(5);

	private final int id;

	WebRTCConnectionStatus(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

	static SparseArrayIdEnum<WebRTCConnectionStatus> array = new SparseArrayIdEnum(WebRTCConnectionStatus.class);


	public static List<WebRTCConnectionStatus> getList(Collection<Integer> ids) {
		return Id_Enum.getListInner(ids, array);
	}

	public static WebRTCConnectionStatus getById(int id) {
		return Id_Enum.getListInner(id, array);
	}

}