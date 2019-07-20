package org.remoteme.utils.messages.v1.core.messages.remoteMe.webtoken;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ARemoteMeMessage;
import org.remoteme.utils.messages.v1.core.messages.variables.AVariableState;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*

ta wiadomosc jest wysylana do urzadzen
 */

@Getter
@Setter
public class VariableChangePropagateMessageWebToken extends ARemoteMeMessage {

	int senderDeviceId;//2
	int receiveDeviceId;//2
	int sessionId;
	int credit;
	int time;
	int futureUse;

	List<AVariableState<?>> states;



	public VariableChangePropagateMessageWebToken(int senderDeviceId , int receiveDeviceId,int sessionId,	int credit,	int time,  AVariableState<?> state) {
		this(senderDeviceId, receiveDeviceId,sessionId,credit,time, Collections.singletonList(state));
	}


	public VariableChangePropagateMessageWebToken(int senderDeviceId , int receiveDeviceId,int sessionId,	int credit,	int time,  List<AVariableState<?>> states) {

		this.senderDeviceId=senderDeviceId;
		this.states = new ArrayList<>(states);
		this.receiveDeviceId=receiveDeviceId;

		this.sessionId=sessionId;
		this.credit=credit;
		this.time=time;

	}


	protected VariableChangePropagateMessageWebToken() {
	}



	public VariableChangePropagateMessageWebToken(ByteBuffer payload) {
		payload.getShort();//taking size

		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		receiveDeviceId = Short.toUnsignedInt(payload.getShort());

		sessionId=Short.toUnsignedInt(payload.getShort());
		credit=Short.toUnsignedInt(payload.getShort());
		time=Short.toUnsignedInt(payload.getShort());

		int count = Short.toUnsignedInt(payload.getShort());

		states = new ArrayList<>(count);
		for(int i=0;i<count;i++){
			states.add(AVariableState.get(payload));
		}

	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=12;
		for (AVariableState state : states) {
			size+=state.getSize();
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)senderDeviceId);
		byteBuffer.putShort((short)receiveDeviceId);

		byteBuffer.putShort((short)sessionId);
		byteBuffer.putShort((short)credit);
		byteBuffer.putShort((short)time);

		byteBuffer.putShort((short)states.size());

		for (AVariableState state : states) {
			state.serialize(byteBuffer);
		}

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.VARIABLE_CHANGE_PROPAGATE_MESSAGE_WEBPAGE_TOKEN;
	}


}