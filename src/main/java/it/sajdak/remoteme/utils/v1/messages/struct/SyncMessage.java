package it.sajdak.remoteme.utils.v1.messages.struct;


import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.enums.SyncMessageType;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;


@Getter
@Setter
public class SyncMessage extends ARemoteMeMessage {


	int receiverDeviceId;//2
	final long messageId;//8
	final SyncMessageType type;//2
	byte message[];//size


	public SyncMessage(int receiverDeviceId , SyncMessageType type, String hexData) {
		this( receiverDeviceId, type,  ByteBufferUtils.hexStringToByteArray(hexData));
	}

	public SyncMessage(int receiverDeviceId, SyncMessageType type, byte[] data) {


		this.receiverDeviceId = receiverDeviceId;
		this.messageId = generateRandom(receiverDeviceId);
		this.type=type;
		this.message =data;


	}

	/**
	 * for this timeout should be not longer then 1 min
	 * @param receiverDeviceId
	 * @return
	 */
	private static long generateRandom(int receiverDeviceId ) {
		return ((((long)receiverDeviceId << 32)+(System.currentTimeMillis()%(2<<16)))<<32)+Math.round(Math.random()*(2<<20));
	}



	public SyncMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		type= SyncMessageType.getById(Short.toUnsignedInt(payload.get()));
		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		messageId = payload.getLong();
		message = ByteBufferUtils.readRest(payload);
	}



	@Override
	public ByteBuffer toByteBuffer() {


		int size=3+8+message.length;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte)type.getId());
		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putLong(messageId);
		byteBuffer.put(message);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SYNC_MESSAGE;
	}

}
