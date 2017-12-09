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



	final int deviceId;//2
	final String deviceName;//size+1
	final DeviceType deviceType;//1




	public RegisterDeviceMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		deviceId = Short.toUnsignedInt(payload.getShort());
		deviceName = ByteBufferUtils.readString(payload);
		deviceType =  DeviceType.getById( Byte.toUnsignedInt(payload.get()));

	}

	public RegisterDeviceMessage(int deviceId, String deviceName, DeviceType deviceType) {
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
	}

	@Override
	public ByteBuffer toByteBuffer() {
		byte[] deviceNameB=ByteBufferUtils.writeString(deviceName);
		int size=2+deviceName.length()+1+1;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)deviceId);
		byteBuffer.put(deviceNameB);
		byteBuffer.put((byte)deviceType.getId());

		byteBuffer.clear();

		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.REGISTER_DEVICE;
	}


	public static void main(String[] args) {
		byte[] b = new byte[6];
		b[0]=(byte)0xe6;
		b[1]=(byte)0xbc;

		b[2]=(byte)0xa2;
		b[3]=(byte)0xe5;
		b[4]=(byte)0xad;
		b[5]=(byte)0x97;

		System.out.println(new String(b, StandardCharsets.UTF_8));
	}

}
