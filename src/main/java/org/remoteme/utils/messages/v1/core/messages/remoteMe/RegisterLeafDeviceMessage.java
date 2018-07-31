package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.LeafDeviceType;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;


@Getter
public class RegisterLeafDeviceMessage extends ARemoteMeMessage {


	int parentId;//2
	int deviceId;//2
	String deviceName;//size+1
	LeafDeviceType type;//2
	boolean restartParent;//1


	protected RegisterLeafDeviceMessage() {
	}

	public RegisterLeafDeviceMessage(ByteBuffer payload ) {
		payload.getShort();//taking size

		parentId = payload.getShort();
		deviceId = payload.getShort();
		deviceName = ByteBufferUtils.readString(payload);
		type= LeafDeviceType.getById(Short.toUnsignedInt(payload.getShort()));

		restartParent=  payload.get()==1;

	}


	public RegisterLeafDeviceMessage(int parentId, int deviceId, String deviceName, LeafDeviceType type,boolean restartParent ) {
		this.parentId = parentId;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.type = type;
		this.restartParent=restartParent;
	}

	@Override
	public ByteBuffer toByteBuffer() {
		byte[] deviceNameB=ByteBufferUtils.writeString(deviceName);
		int size=2+2+deviceNameB.length+1+2+1;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)parentId);
		byteBuffer.putShort((short)deviceId);
		byteBuffer.put(deviceNameB);
		byteBuffer.putShort((short)type.getId());
		byteBuffer.put((byte)(restartParent?1:0));


		byteBuffer.clear();

		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.REGISTER_CHILD_DEVICE;
	}

	public boolean isRestartParent() {
		return restartParent;
	}
}
