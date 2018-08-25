package org.remoteme.utils.messages.v1.core.messages.arLite;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ARemoteMeMessage;
import org.remoteme.utils.messages.v1.enums.VariableType;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;


@Getter
@Setter
public class VariableRemoveMessage extends AARLiteMessage {


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







}
