package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import org.remoteme.utils.general.Pair;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
public class VariableSchedulerStateChangeMessage extends ARemoteMeMessage {



	List<Pair<Integer,Boolean>> status =new ArrayList<>();



	protected VariableSchedulerStateChangeMessage() {
	}

	public VariableSchedulerStateChangeMessage(ByteBuffer payload) {
		int size =payload.getShort()/5;
		while(size-->0){
			status.add(new Pair<>( payload.getInt(),payload.get()==1));
		}

	}



	public VariableSchedulerStateChangeMessage(int variableSchedulerId, boolean status) {
		this.status.add(new Pair<>( variableSchedulerId,status));

	}

	public VariableSchedulerStateChangeMessage(Collection<Pair<Integer, Boolean>> states) {
		status.addAll(states);
	}

	public static Collection<Pair<Integer, Boolean>> convert(Set<Integer> allCurrentConnected) {
		return allCurrentConnected.stream().map(x->new Pair<>(x,true)).collect(Collectors.toList());
	}


	@Override
	public ByteBuffer toByteBuffer() {

		int size=status.size()*5;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		for (Pair<Integer, Boolean> statusTemp : status) {
				byteBuffer.putInt((statusTemp.getFirst()));
				byteBuffer.put((byte)(statusTemp.getSecond()?1:0));
		}


		byteBuffer.clear();

		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.VARIABLE_SCHEDULER_STATE_CHANGE;
	}




}
