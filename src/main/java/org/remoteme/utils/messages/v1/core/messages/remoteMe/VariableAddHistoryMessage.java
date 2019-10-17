package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.InstantUtils;
import org.remoteme.utils.general.Pair;
import org.remoteme.utils.messages.v1.core.messages.variables.AVariableState;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class VariableAddHistoryMessage extends ARemoteMeMessage {

	int senderDeviceId;//2


	List<Pair<AVariableState<?>,Instant>> states;


	public VariableAddHistoryMessage(int senderDeviceId,  List<Pair<AVariableState<?>,Instant>> states ) {

		this.senderDeviceId=senderDeviceId;
		this.states = new ArrayList<>(states);

	}


	protected VariableAddHistoryMessage() {
	}



	public VariableAddHistoryMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		senderDeviceId = Short.toUnsignedInt(payload.getShort());



		int count = Short.toUnsignedInt(payload.getShort());

		states = new ArrayList<>(count);
		for(int i=0;i<count;i++){
			AVariableState<?> aVariableState = AVariableState.get(payload);

			states.add(new Pair<>(aVariableState, InstantUtils.getFromMillis(payload.getLong())));
		}

	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2;
		for (Pair<AVariableState<?>, Instant> state : states) {
			size+=state.getFirst().getSize()+4;
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)senderDeviceId);



		byteBuffer.putShort((short)states.size());

		for (Pair<AVariableState<?>, Instant> state : states) {
			state.getFirst().serialize(byteBuffer);
			byteBuffer.putLong(state.getSecond().toEpochMilli());

		}

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.VARIABLE_ADD_HISTORY_MESSAGE;
	}


}
