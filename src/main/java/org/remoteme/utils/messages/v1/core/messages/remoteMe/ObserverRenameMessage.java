package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.VariableOberverType;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;


@Getter
@Setter
public class ObserverRenameMessage extends ARemoteMeMessage {


	int deviceId;
	String oldName;
	String newName;
	VariableOberverType type;


	public ObserverRenameMessage(int deviceId,	String oldName,	String newName,	VariableOberverType type){
		this.deviceId=deviceId;
		this.oldName=oldName;
		this.newName=newName;
		this.type=type;

	}


	protected ObserverRenameMessage() {
	}



	public ObserverRenameMessage(ByteBuffer payload) {
		payload.getShort();//taking size
		deviceId= Short.toUnsignedInt(payload.getShort());
		type= VariableOberverType.getById(Short.toUnsignedInt(payload.getShort()));
		oldName= ByteBufferUtils.readString(payload);
		newName= ByteBufferUtils.readString(payload);


	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2+ByteBufferUtils.getStringLength(oldName)+ByteBufferUtils.getStringLength(newName);

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);

		byteBuffer.putShort((short)deviceId);
		byteBuffer.putShort((short)type.getId());
		byteBuffer.put(ByteBufferUtils.writeString(oldName));
		byteBuffer.put(ByteBufferUtils.writeString(newName));


		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OBSERVER_RENAME_MESSAGE;
	}


}
