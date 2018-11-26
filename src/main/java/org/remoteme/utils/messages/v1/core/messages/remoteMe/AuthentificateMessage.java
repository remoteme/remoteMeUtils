package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.MessageType;
import org.remoteme.utils.messages.v1.enums.UserMessageSettings;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class AuthentificateMessage extends ARemoteMeMessage {


	int deviceId;//2
	String token;


	protected AuthentificateMessage() {
	}



	public AuthentificateMessage(int deviceId,String token) {
		this.deviceId=deviceId;
		this.token=token;


	}




	public AuthentificateMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		deviceId = Short.toUnsignedInt(payload.getShort());
		token = ByteBufferUtils.readString(payload);

	}




	@Override
	public ByteBuffer toByteBuffer() {
		byte[] tokenB=ByteBufferUtils.writeString(token);

		int size=2+tokenB.length;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);

		byteBuffer.putShort((short)deviceId);
		byteBuffer.put(tokenB);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.AUTHENTIFICATE;
	}


}
