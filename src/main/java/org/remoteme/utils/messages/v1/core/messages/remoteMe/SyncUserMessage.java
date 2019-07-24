package org.remoteme.utils.messages.v1.core.messages.remoteMe;


import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.MessageType;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class SyncUserMessage extends ASyncMessage {


	protected int senderDeviceId;//2

	@JsonIgnore
	boolean sentFromGui;

	protected SyncUserMessage() {

	}



	public SyncUserMessage(int receiverDeviceId ,int senderDeviceId,  String hexData,boolean sentFromGui ) {
		this( receiverDeviceId, senderDeviceId,  ByteBufferUtils.hexStringToByteArray(hexData),sentFromGui);
	}

	public SyncUserMessage(int receiverDeviceId ,int senderDeviceId,  String hexData ) {
		this( receiverDeviceId, senderDeviceId,  hexData,false);
	}
	public SyncUserMessage(int receiverDeviceId,int senderDeviceId,long messageId , byte[] data,boolean sentFromGui ) {
		this(receiverDeviceId,senderDeviceId,messageId,ByteBufferUtils.toIntList(data),sentFromGui);
	}
	public SyncUserMessage(int receiverDeviceId, int senderDeviceId, long messageId , List<Integer> data, boolean sentFromGui ) {

		this.receiverDeviceId = receiverDeviceId;
		this.senderDeviceId=senderDeviceId;
		this.messageId = messageId;
		this.message =new ArrayList<>(data);
		this.sentFromGui=sentFromGui;

	}

	public SyncUserMessage(int receiverDeviceId,int senderDeviceId,  byte[] data,boolean sentFromGui ) {
		this(receiverDeviceId,senderDeviceId,generateRandom(receiverDeviceId),data, sentFromGui);

	}



	public SyncUserMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		messageId = payload.getLong();
		message = ByteBufferUtils.toIntList(ByteBufferUtils.readRest(payload));

	}



	@Override
	public ByteBuffer toByteBuffer() {


		int size=4+8+message.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putShort((short)senderDeviceId);
		byteBuffer.putLong(messageId);
		byteBuffer.put(ByteBufferUtils.getByteArray(message));

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.USER_SYNC_MESSAGE;
	}

}
