package org.remoteme.utils.messages.v1.core.messages.remoteMe;


import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.general.Id_Enum;
import org.remoteme.utils.general.SparseArrayIdEnum;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class SetFileContent extends ARemoteMeMessage {

	private int deviceId;
	private String fileName;
	private byte[] content;
	private boolean append;





	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}



	public SetFileContent() {
	}



	public SetFileContent(ByteBuffer payload) {
		int size =payload.getShort();
		deviceId=Short.toUnsignedInt(payload.getShort());
		fileName=ByteBufferUtils.readString(payload);
		append=payload.get()==1;
		content=ByteBufferUtils.readRest(payload);


	}


	@Override
	public ByteBuffer toByteBuffer() {
		byte[] fileName = ByteBufferUtils.writeString(getFileName());

		int size = content.length+fileName.length+1;



		ByteBuffer byteBuffer = ByteBuffer.allocate(size + 4);

		byteBuffer.putShort((short) getMessageType().getId());
		byteBuffer.putShort((short) size);
		byteBuffer.put(fileName);
		//byteBuffer.put(append?1:0);




		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SET_FILE_CONTENT;
	}

}
