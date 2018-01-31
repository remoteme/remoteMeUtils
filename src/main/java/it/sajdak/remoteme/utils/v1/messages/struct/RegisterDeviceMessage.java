package it.sajdak.remoteme.utils.v1.messages.struct;



import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.enums.DeviceType;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


@Getter
public class RegisterDeviceMessage extends ARemoteMeMessage {



	int deviceId;//2
	String deviceName;//size+1
	DeviceType deviceType;//1
	int aditionalProperties;//2  //leaf device type/ network devicetype


	protected RegisterDeviceMessage() {
	}

	public RegisterDeviceMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		deviceId = Short.toUnsignedInt(payload.getShort());
		deviceName = ByteBufferUtils.readString(payload);
		deviceType =  DeviceType.getById( Byte.toUnsignedInt(payload.get()));
		aditionalProperties =  Short.toUnsignedInt(payload.getShort());

	}

	public RegisterDeviceMessage(int deviceId, String deviceName, DeviceType deviceType,int anotherProperties) {
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.aditionalProperties=anotherProperties;
	}

	@Override
	public ByteBuffer toByteBuffer() {
		byte[] deviceNameB=ByteBufferUtils.writeString(deviceName);
		int size=2+2+deviceName.length()+1+1;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)deviceId);
		byteBuffer.put(deviceNameB);
		byteBuffer.put((byte)deviceType.getId());
		byteBuffer.putShort((short)aditionalProperties);

		byteBuffer.clear();

		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.REGISTER_DEVICE;
	}




}
