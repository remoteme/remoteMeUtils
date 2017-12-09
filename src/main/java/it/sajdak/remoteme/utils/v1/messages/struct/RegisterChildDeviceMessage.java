package it.sajdak.remoteme.utils.v1.messages.struct;


import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.enums.LeafDeviceType;
import lombok.Getter;

import java.nio.ByteBuffer;


@Getter
public class RegisterChildDeviceMessage extends ARemoteMeMessage {



	final int parentId;//2
	final int deviceId;//2
	final String deviceName;//size+1
	final LeafDeviceType type;//1




	public RegisterChildDeviceMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		parentId = payload.getShort();
		deviceId = payload.getShort();
		deviceName = ByteBufferUtils.readString(payload);
		type= LeafDeviceType.getById(payload.get());


	}


	public RegisterChildDeviceMessage(int parentId, int deviceId, String deviceName,LeafDeviceType type ) {
		this.parentId = parentId;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.type = type;

	}

	@Override
	public ByteBuffer toByteBuffer() {
		byte[] deviceNameB=ByteBufferUtils.writeString(deviceName);
		int size=2+2+deviceNameB.length+1+1;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)parentId);
		byteBuffer.putShort((short)deviceId);
		byteBuffer.put(deviceNameB);
		byteBuffer.put((byte)type.getId());


		byteBuffer.clear();

		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.REGISTER_CHILD_DEVICE;
	}

}
