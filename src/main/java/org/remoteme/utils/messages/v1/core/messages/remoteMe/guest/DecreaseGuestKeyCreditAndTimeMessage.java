package org.remoteme.utils.messages.v1.core.messages.remoteMe.guest;




import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ARemoteMeMessage;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;


@Getter
@Setter
public class DecreaseGuestKeyCreditAndTimeMessage extends ARemoteMeMessage {


	private int senderDeviceId;//2
	private int sessionId;//2
	private int credit;//2
	private int time;//2




	protected DecreaseGuestKeyCreditAndTimeMessage() {
	}


	public DecreaseGuestKeyCreditAndTimeMessage(int senderDeviceId, int sessionId, int credit, int time){
		this.senderDeviceId=senderDeviceId;
		this.sessionId=sessionId;
		this.credit=credit;
		this.time=time;
	}





	public DecreaseGuestKeyCreditAndTimeMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		sessionId =  Short.toUnsignedInt(payload.getShort());
		credit = (int)payload.getShort();
		time =  (int)payload.getShort();


	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2+2+2;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);

		byteBuffer.putShort((short)senderDeviceId);
		byteBuffer.putShort((short)sessionId);
		byteBuffer.putShort((short)credit);
		byteBuffer.putShort((short)time);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.DECREASE_GUEST_CREDIT_AND_TIME;
	}


}
