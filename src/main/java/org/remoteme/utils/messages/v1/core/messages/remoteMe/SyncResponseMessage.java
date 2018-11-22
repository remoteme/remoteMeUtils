package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.List;


@Getter
@Setter
public class SyncResponseMessage extends ARemoteMeMessage {

	long messageId;//8
	List<Integer> message;//size

	protected SyncResponseMessage() {
	}

	public SyncResponseMessage(long messageId , String hexData) {
		this( messageId,   ByteBufferUtils.hexStringToByteArray(hexData));
	}
	public SyncResponseMessage(long messageId , byte[] data) {
		this(messageId,ByteBufferUtils.toIntList(data));
	}
	public SyncResponseMessage(long messageId , List<Integer> data) {

		this.messageId = messageId;
		this.message =data;


	}


	public SyncResponseMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		messageId = payload.getLong();

		message =ByteBufferUtils.toIntList(ByteBufferUtils.readRest(payload));
	}



	@Override
	public ByteBuffer toByteBuffer() {


		int size=8+message.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);



		byteBuffer.putLong(messageId);
		byteBuffer.put(ByteBufferUtils.getByteArray(message));

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SYNC_MESSAGE_RESPONSE;
	}

}

