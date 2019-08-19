package org.remoteme.utils.messages.v1.core.messages.remoteMe;


import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class EventSubscriberMessage extends ARemoteMeMessage {

	public enum EventSubscriberType implements Id_Enum<EventSubscriberType> {

		DEVICE_CONNECTION(0), VARIABLE_SCHEDULER_STATE(50),FILE_CHANGE(70);

		private final int id;

		EventSubscriberType(int id) {
			this.id = id;
		}


		public int getId() {
			return id;
		}

		static SparseArrayIdEnum<MessageType> array = new SparseArrayIdEnum(EventSubscriberType.class);


		public static List<EventSubscriberType> getList(Collection<Integer> ids) {
			return Id_Enum.getListInner(ids, array);
		}

		public static EventSubscriberType getById(int id) {
			return Id_Enum.getListInner(id, array);
		}
	}


	Set<EventSubscriberType> eventSubscriberType;
	private int socketId;//only first time
	private int deviceId;

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getSocketId() {
		return socketId;
	}

	public void setSocketId(int socketId) {
		this.socketId = socketId;
	}

	public EventSubscriberMessage() {
	}

	public EventSubscriberMessage(Set<EventSubscriberType> eventSubscriberType) {
		this.eventSubscriberType = new HashSet<>(eventSubscriberType);
	}


	public EventSubscriberMessage(ByteBuffer payload) {
		int size =payload.getShort()/2;
		this.eventSubscriberType = new HashSet<>();
		while(size-->0){
			this.eventSubscriberType.add( EventSubscriberType.getById(Short.toUnsignedInt(payload.getShort())));
		}


	}


	@Override
	public ByteBuffer toByteBuffer() {
		int size = 2*this.eventSubscriberType.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size + 4);

		byteBuffer.putShort((short) getMessageType().getId());
		byteBuffer.putShort((short) size);

		this.eventSubscriberType.forEach(x->{
			byteBuffer.putShort((short) x.getId());
		});


		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.EVENT_SUBSCRIBER;
	}

}
