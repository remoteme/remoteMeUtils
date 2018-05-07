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



	 int senderDeviceId;//2

	final boolean processReponse;

	protected SyncUserMessage() {
		processReponse=true;
	}

	public SyncUserMessage(int receiverDeviceId ,int senderDeviceId,  String hexData,boolean processReponse) {
		this( receiverDeviceId, senderDeviceId,  ByteBufferUtils.hexStringToByteArray(hexData),processReponse);
	}

	public SyncUserMessage(int receiverDeviceId,int senderDeviceId,  byte[] data,boolean processReponse) {


		this.receiverDeviceId = receiverDeviceId;
		this.senderDeviceId=senderDeviceId;
		this.messageId = generateRandom(receiverDeviceId);
		this.message =data;
		this.processReponse=processReponse;

	}



	public SyncUserMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		messageId = payload.getLong();
		message = ByteBufferUtils.readRest(payload);
		processReponse=true;
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
