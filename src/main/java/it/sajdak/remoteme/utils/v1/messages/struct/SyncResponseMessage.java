package it.sajdak.remoteme.utils.v1.messages.struct;


import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;


@Getter
@Setter
public class SyncResponseMessage extends ARemoteMeMessage {


	long messageId;//4
	byte message[];//size

	protected SyncResponseMessage() {
	}

	public SyncResponseMessage(long messageId , String hexData) {
		this( messageId,   ByteBufferUtils.hexStringToByteArray(hexData));
	}

	public SyncResponseMessage(long messageId , byte[] data) {

		this.messageId = messageId;
		this.message =data;


	}


	public SyncResponseMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		messageId = payload.getLong();

		message = ByteBufferUtils.readRest(payload);
	}



	@Override
	public ByteBuffer toByteBuffer() {


		int size=8+message.length;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);



		byteBuffer.putLong(messageId);
		byteBuffer.put(message);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SYNC_MESSAGE_RESPONSE;
	}

}

