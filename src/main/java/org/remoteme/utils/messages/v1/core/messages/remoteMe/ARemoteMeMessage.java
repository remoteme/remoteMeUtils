package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.remoteme.utils.messages.v1.core.messages.AMessage;
import org.remoteme.utils.messages.v1.core.messages.change.ObserverChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.change.ObserverChangePropagateMessage;
import org.remoteme.utils.messages.v1.core.messages.change.ObserverRegisterMessage;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;

@Getter
@Setter
@Slf4j
public abstract class ARemoteMeMessage extends AMessage {

	public abstract ByteBuffer toByteBuffer();

	public abstract MessageType getMessageType() ;


	public static ARemoteMeMessage decode(ByteBuffer payload){
		int messageTypeId = Short.toUnsignedInt(payload.getShort());
		MessageType messageType;
		try {
			messageType = MessageType.getById(messageTypeId);
		}catch (NullPointerException npe){
			log.error("Message type:{} not recopgnized",messageTypeId);
			throw npe;
		}
		switch (messageType){
			case USER_MESSAGE:return new UserMessage(payload);
			case USER_MESSAGE_DELIVER_STATUS: return null;//todo

			case REGISTER_DEVICE:return new RegisterDeviceMessage(payload);
			case REGISTER_CHILD_DEVICE:return new RegisterLeafDeviceMessage(payload);

			case ADD_DATA:return new AddDataMessage(payload);
			case LOG:return new LogMessage(payload);
			case USER_SYNC_MESSAGE:return new SyncUserMessage(payload);
			case SYNC_MESSAGE:return new SyncMessage(payload);
			case SYNC_MESSAGE_RESPONSE:return new SyncResponseMessage(payload);
			case SYSTEM_MESSAGE:return new SystemMessage(payload);
			case WEB_RTC_CONNECTION_CHANGE:return new WebRRCConnectionStatusChangeMessage(payload);

			case OBSERVER_CHANGE_MESSAGE:return new ObserverChangeMessage(payload);
			case REBISTER_OBSERVER_MESSAGE:return new ObserverRegisterMessage(payload);
			case OBSERVER_CHANGE_PROPAGATE_MESSAGE:return new ObserverChangePropagateMessage(payload);
		}

		throw new RuntimeException("should not happen  ");//we have all in switch
	}

}
