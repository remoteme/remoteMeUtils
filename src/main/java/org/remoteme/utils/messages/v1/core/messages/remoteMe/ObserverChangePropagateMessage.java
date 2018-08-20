package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.observers.AObserverState;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


/*

ta wiadomosc jest wysylana do urzadzen
 */

@Getter
@Setter
public class ObserverChangePropagateMessage extends ARemoteMeMessage {

	int senderDeviceId;//2
	int receiveDeviceId;//2

	List<AObserverState<?>> states;


	public ObserverChangePropagateMessage(int senderDeviceId ,int receiveDeviceId, List<AObserverState<?>> states) {

		this.senderDeviceId=senderDeviceId;
		this.states = new ArrayList<>(states);
		this.receiveDeviceId=receiveDeviceId;

	}


	protected ObserverChangePropagateMessage() {
	}



	public ObserverChangePropagateMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		receiveDeviceId = Short.toUnsignedInt(payload.getShort());
		int count = Short.toUnsignedInt(payload.getShort());

		states = new ArrayList<>(count);
		for(int i=0;i<count;i++){
			states.add(AObserverState.get(payload));
		}

	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2+2;
		for (AObserverState state : states) {
			size+=state.getSize();
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.putShort((short)senderDeviceId);
		byteBuffer.putShort((short)receiveDeviceId);
		byteBuffer.putShort((short)states.size());

		for (AObserverState state : states) {
			state.serialize(byteBuffer);
		}

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OBSERVER_CHANGE_PROPAGATE_MESSAGE;
	}


}
