package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.variables.VariableIdentifier;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class VariableRegisterMessage extends ARemoteMeMessage {



	int deviceId;//2

	List<VariableIdentifier> variables;


	public VariableRegisterMessage(int deviceId, List<VariableIdentifier> variables) {

		this.deviceId=deviceId;
		this.variables = new ArrayList<>(variables);
	}


	protected VariableRegisterMessage() {
	}



	public VariableRegisterMessage(ByteBuffer payload) {
		payload.getShort();//taking size


		deviceId = Short.toUnsignedInt(payload.getShort());
		int count = Short.toUnsignedInt(payload.getShort());

		variables = new ArrayList<>(count);
		for(int i=0;i<count;i++){
			variables.add(new VariableIdentifier(payload));
		}

	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2;
		for (VariableIdentifier state : variables) {
			size+=2+state.getName().length()+1;
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);



		byteBuffer.putShort((short)deviceId);
		byteBuffer.putShort((short) variables.size());

		for (VariableIdentifier state : variables) {
			state.serialize(byteBuffer);
		}

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OBSERVER_REGISTER_MESSAGE;
	}


}
