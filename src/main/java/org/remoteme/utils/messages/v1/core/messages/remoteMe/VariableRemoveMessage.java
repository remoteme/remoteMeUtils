package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.VariableType;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;


@Getter
@Setter
public class VariableRemoveMessage extends ARemoteMeMessage {


	int deviceId;
	String name;
	VariableType type;






	public VariableRemoveMessage(int deviceId, String name, VariableType type) {
		this.deviceId=deviceId;
		this.name=name;
		this.type=type;

	}


	protected VariableRemoveMessage() {
	}



	public VariableRemoveMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		deviceId= Short.toUnsignedInt(payload.getShort());
		type= VariableType.getById(Short.toUnsignedInt(payload.getShort()));
		name = ByteBufferUtils.readString(payload);


	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2+ByteBufferUtils.getStringLength(name);

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)deviceId);
		byteBuffer.putShort((short)type.getId());
		byteBuffer.put(ByteBufferUtils.writeString(name));



		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OBSERVER_REMOVE_MESSAGE;
	}


}
