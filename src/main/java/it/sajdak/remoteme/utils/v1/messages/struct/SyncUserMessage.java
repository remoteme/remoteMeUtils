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
public class SyncUserMessage extends ASyncMessage {


	final int receiverDeviceId;//2
	final int senderDeviceId;//2
	final long messageId;//8
	final byte message[];//size


	public SyncUserMessage(int receiverDeviceId ,int senderDeviceId,  String hexData) {
		this( receiverDeviceId, senderDeviceId,  ByteBufferUtils.hexStringToByteArray(hexData));
	}

	public SyncUserMessage(int receiverDeviceId,int senderDeviceId,  byte[] data) {


		this.receiverDeviceId = receiverDeviceId;
		this.senderDeviceId=senderDeviceId;
		this.messageId = generateRandom(receiverDeviceId);
		this.message =data;


	}



	public SyncUserMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		messageId = payload.getLong();
		message = ByteBufferUtils.readRest(payload);
	}



	@Override
	public ByteBuffer toByteBuffer() {


		int size=4+8+message.length;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putShort((short)senderDeviceId);
		byteBuffer.putLong(messageId);
		byteBuffer.put(message);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.USER_SYNC_MESSAGE;
	}

}
