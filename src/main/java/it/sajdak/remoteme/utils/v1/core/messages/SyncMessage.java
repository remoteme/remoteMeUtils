package it.sajdak.remoteme.utils.v1.core.messages;


import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.enums.SyncMessageType;
import it.sajdak.remoteme.utils.general.ByteBufferUtils;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;


@Getter
@Setter
public class SyncMessage extends ASyncMessage {


	SyncMessageType type;//2



	protected SyncMessage() {
	}

	public SyncMessage(int receiverDeviceId , SyncMessageType type, String hexData) {
		this( receiverDeviceId, type,  ByteBufferUtils.hexStringToByteArray(hexData));
	}

	public SyncMessage(int receiverDeviceId, SyncMessageType type, byte[] data) {

		this.receiverDeviceId = receiverDeviceId;
		this.messageId = generateRandom(receiverDeviceId);
		this.type=type;
		this.message =data;

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
