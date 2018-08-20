package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.observerStates.AObserverState;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class ObserverChangeMessage extends ARemoteMeMessage {

	int senderDeviceId;//2
	Set<Integer> ignoreReceivers;

	List<AObserverState> states;


	public ObserverChangeMessage(int senderDeviceId, Collection<Integer> ignoreReceivers, List<AObserverState> states) {

		this.senderDeviceId=senderDeviceId;
		this.ignoreReceivers=new HashSet<>(ignoreReceivers);
		this.states = new ArrayList<>(states);
	}


	protected ObserverChangeMessage() {
	}



	public ObserverChangeMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		senderDeviceId = Short.toUnsignedInt(payload.getShort());

		int receiverSize = Byte.toUnsignedInt(payload.get());
		ignoreReceivers= new HashSet<>(receiverSize);
		for (int i = 0; i < receiverSize; i++) {
			ignoreReceivers.add(Short.toUnsignedInt(payload.getShort()));
		}

		int count = Short.toUnsignedInt(payload.getShort());

		states = new ArrayList<>(count);
		for(int i=0;i<count;i++){
			states.add(AObserverState.get(payload));
		}

	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2+1+ignoreReceivers.size()*2;
		for (AObserverState state : states) {
			size+=state.getSize();
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)senderDeviceId);

		byteBuffer.put((byte)(ignoreReceivers.size()));
		for (int ignoreReceiver : ignoreReceivers) {
			byteBuffer.putShort((short)ignoreReceiver);
		}

		byteBuffer.putShort((short)states.size());

		for (AObserverState state : states) {
			state.serialize(byteBuffer);
		}

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OBSERVER_CHANGE_MESSAGE;
	}


}
