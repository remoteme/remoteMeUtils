package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import org.remoteme.utils.general.Pair;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
public class SetVariableSchedulerStateMessage extends VariableSchedulerStateChangeMessage {



	protected SetVariableSchedulerStateMessage() {
	}

	public SetVariableSchedulerStateMessage(ByteBuffer payload) {
		super(payload);

	}


	@Override
	public MessageType getMessageType() {
		return MessageType.SET_VARIABLE_SCHEDULER_STATE;
	}




}
