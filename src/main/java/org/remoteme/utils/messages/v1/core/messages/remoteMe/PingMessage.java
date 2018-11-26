package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;


@Getter
@Setter
public class PingMessage extends ARemoteMeMessage {


	Integer deviceId;//transient

	protected PingMessage() {
	}


	public PingMessage(int deviceId) {
		this.deviceId=deviceId;
	}



	public PingMessage(ByteBuffer payload) {
		payload.getShort();//taking size


	}




	@Override
	public ByteBuffer toByteBuffer() {

		int size=0;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.PING;
	}


}
