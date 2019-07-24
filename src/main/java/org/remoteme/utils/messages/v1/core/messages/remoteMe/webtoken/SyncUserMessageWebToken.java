package org.remoteme.utils.messages.v1.core.messages.remoteMe.webtoken;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ASyncMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncUserMessage;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class SyncUserMessageWebToken extends SyncUserMessage {



	private int sessionId;//2
	private int credit;//2
	private int time;//2


	protected SyncUserMessageWebToken() {

	}




	public SyncUserMessageWebToken(int receiverDeviceId, int senderDeviceId, int sessionId,	int credit,	int time, long messageId , List<Integer> data ) {
		super(receiverDeviceId, senderDeviceId, messageId, data, false);



		this.sessionId=sessionId;
		this.credit=credit;
		this.time=time;

	}




	public SyncUserMessageWebToken(ByteBuffer payload) {
		payload.getShort();//taking size

		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		sessionId=Short.toUnsignedInt(payload.getShort());
		credit=Short.toUnsignedInt(payload.getShort());
		time=Short.toUnsignedInt(payload.getShort());


		messageId = payload.getLong();
		message = ByteBufferUtils.toIntList(ByteBufferUtils.readRest(payload));

	}

	public SyncUserMessageWebToken(SyncUserMessage userSync, int deviceSessionId, int credit, int time) {
		this(userSync.getReceiverDeviceId(),userSync.getSenderDeviceId(), deviceSessionId,credit,time,userSync.getMessageId(),userSync.getMessage());
	}


	@Override
	public ByteBuffer toByteBuffer() {


		int size=10+8+message.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putShort((short)senderDeviceId);

		byteBuffer.putShort((short)sessionId);
		byteBuffer.putShort((short)credit);
		byteBuffer.putShort((short)time);

		byteBuffer.putLong(messageId);
		byteBuffer.put(ByteBufferUtils.getByteArray(message));

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.USER_SYNC_MESSAGE_WEBPAGE_TOKEN;
	}

}
