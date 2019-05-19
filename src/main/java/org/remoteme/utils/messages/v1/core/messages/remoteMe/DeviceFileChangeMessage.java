package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.general.Pair;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.Collection;


@Getter
public class DeviceFileChangeMessage extends ARemoteMeMessage {



	int deviceId;
	String fileName;


	public DeviceFileChangeMessage(int deviceId, String fileName) {
		this.deviceId = deviceId;
		this.fileName = fileName;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}



	public DeviceFileChangeMessage() {
	}



	public DeviceFileChangeMessage(ByteBuffer payload) {
		int size =payload.getShort();
		deviceId=Short.toUnsignedInt(payload.getShort());
		fileName= ByteBufferUtils.readString(payload);
	}


	@Override
	public ByteBuffer toByteBuffer() {
		byte[] fileName = ByteBufferUtils.writeString(getFileName());

		int size = fileName.length+1+2;



		ByteBuffer byteBuffer = ByteBuffer.allocate(size + 4);

		byteBuffer.putShort((short) getMessageType().getId());
		byteBuffer.putShort((short) size);
		byteBuffer.putShort((short) deviceId);
		byteBuffer.put(fileName);



		byteBuffer.clear();


		return byteBuffer;
	}
	@Override
	public MessageType getMessageType() {
		return MessageType.DEVICE_FILE_CHANGE;
	}




}
