package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;


import org.remoteme.utils.messages.v1.core.messages.AMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.webtoken.DecreaseGuestKeyCreditMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.webtoken.SyncUserMessageWebToken;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.webtoken.UserMessageWebToken;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;

@Getter
@Setter
public abstract class ARemoteMeMessage extends AMessage {

	public abstract ByteBuffer toByteBuffer();

	public abstract MessageType getMessageType() ;


	public static ARemoteMeMessage decode(ByteBuffer payload){

		payload.rewind();
		int messageTypeId = Short.toUnsignedInt(payload.getShort());
		MessageType messageType;
		try {
			messageType = MessageType.getById(messageTypeId);
		}catch (NullPointerException npe){
			throw npe;
		}
		switch (messageType){
			case AUTHENTIFICATE: return new AuthentificateMessage(payload);
			case PING: return new PingMessage(payload);
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

			case VARIABLE_CHANGE_MESSAGE:return new VariableChangeMessage(payload);
			case VARIABLE_OBSERVE_MESSAGE:return new VariableObserveMessage(payload);
			case VARIABLE_CHANGE_PROPAGATE_MESSAGE:return new VariableChangePropagateMessage(payload);
			case DEVICE_CONNECTION_CHANGE:return new DeviceConnectionChangeMessage(payload);
			case SEND_PUSH_NOTIFICATION:return new SendPushNotificationMessage(payload);
			case EVENT_SUBSCRIBER: return new EventSubscriberMessage(payload);
			case SET_VARIABLE_SCHEDULER_STATE: return new SetVariableSchedulerStateMessage(payload);
			case VARIABLE_SCHEDULER_STATE_CHANGE: return new VariableSchedulerStateChangeMessage(payload);
			case SET_FILE_CONTENT:return new SetFileContentMessage(payload);
			case DEVICE_FILE_CHANGE:return new DeviceFileChangeMessage(payload);


			case DECREASE_GUEST_CREDIT:return new DecreaseGuestKeyCreditMessage(payload);
			case USER_MESSAGE_GUEST:return new UserMessageWebToken(payload);
			case USER_SYNC_MESSAGE_GUEST:return new SyncUserMessageWebToken(payload);
			case VARIABLE_CHANGE_PROPAGATE_MESSAGE_GUEST:return new VariableChangeMessage(payload);

		}

		throw new RuntimeException("should not happen . messaget with type "+messageType+" couldnt be decoded ");//we have all in switch
	}

}
