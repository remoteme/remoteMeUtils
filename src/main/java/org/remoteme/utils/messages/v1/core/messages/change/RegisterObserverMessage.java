package org.remoteme.utils.messages.v1.core.messages.change;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ARemoteMeMessage;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class RegisterObserverMessage extends ARemoteMeMessage {



	int senderDeviceId;//2

	List<ObserverTypeAndName> observers;


	public RegisterObserverMessage(int senderDeviceId, List<ObserverTypeAndName> observers) {

		this.senderDeviceId=senderDeviceId;
		this.observers = new ArrayList<>(observers);
	}


	protected RegisterObserverMessage() {
	}



	public RegisterObserverMessage(ByteBuffer payload) {
		payload.getShort();//taking size


		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		int count = Short.toUnsignedInt(payload.getShort());

		observers = new ArrayList<>(count);
		for(int i=0;i<count;i++){
			observers.add(new ObserverTypeAndName(payload));
		}

	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2;
		for (ObserverTypeAndName state : observers) {
			size+=2+state.getName().length()+1;
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);



		byteBuffer.putShort((short)senderDeviceId);
		byteBuffer.putShort((short)observers.size());

		for (ObserverTypeAndName state : observers) {
			state.serialize(byteBuffer);
		}

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.REBISTER_OBSERVER_MESSAGE;
	}


}
