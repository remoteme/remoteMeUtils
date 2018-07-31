package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.MessageType;
import org.remoteme.utils.messages.v1.enums.SyncMessageType;

import java.nio.ByteBuffer;
import java.util.List;


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
		this(receiverDeviceId,type,ByteBufferUtils.toIntList(data));
	}
	public SyncMessage(int receiverDeviceId, SyncMessageType type,List<Integer> data) {

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
		message = ByteBufferUtils.toIntList(ByteBufferUtils.readRest(payload));
	}



	@Override
	public ByteBuffer toByteBuffer() {


		int size=3+8+message.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte)type.getId());
		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putLong(messageId);
		byteBuffer.put(ByteBufferUtils.getByteArray(message));

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SYNC_MESSAGE;
	}

}
