package it.sajdak.remoteme.utils.v1.core.messages;


import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.enums.SystemMessageType;
import it.sajdak.remoteme.utils.general.ByteBufferUtils;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;


@Getter
@Setter
public class SystemMessage extends ARemoteMeMessage {


	public static SystemMessage getRestartMessage(int receiverDeviceId){
		return new SystemMessage(receiverDeviceId, SystemMessageType.RESTART, Collections.emptyList());
	}

	int receiverDeviceId;//2
	byte message[];//size
	SystemMessageType systemMessageType;


	public SystemMessage() {
	}

	public SystemMessage( int receiverDeviceId,SystemMessageType systemMessageType, List<Integer> data) {


		this.systemMessageType=systemMessageType;

		this.receiverDeviceId = receiverDeviceId;
		this.message = new byte[data.size()];

		for(int i=0;i<data.size();i++){
			this.message[i]=data.get(i).byteValue();
		}


	}




	public SystemMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		systemMessageType =SystemMessageType.getById(Short.toUnsignedInt(payload.getShort()));

		message = ByteBufferUtils.readRest(payload);
	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2+message.length;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putShort((short)systemMessageType.getId());

		byteBuffer.put(message);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SYSTEM_MESSAGE;
	}

}
