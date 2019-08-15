package org.remoteme.utils.messages.v1.core.messages.remoteMe.guest;




import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.UserMessage;
import org.remoteme.utils.messages.v1.enums.MessageType;
import org.remoteme.utils.messages.v1.enums.UserMessageSettings;

import java.nio.ByteBuffer;
import java.util.List;


@Getter
@Setter
public class UserMessageGuest extends UserMessage {

	private int sessionId;//2
	private int credit;//2
	private int time;//2
	private int identifier;//2




	protected UserMessageGuest() {
	}




	public UserMessageGuest(UserMessageSettings userMessageSettings, int receiverDeviceId, int senderDeviceId, int sessionId, int identifier, int credit, int time, List<Integer> data) {
		super(userMessageSettings, receiverDeviceId, senderDeviceId, 0, data);
		this.sessionId=sessionId;
		this.identifier=identifier;
		this.credit=credit;
		this.time=time;

	}




	public UserMessageGuest(ByteBuffer payload) {
		payload.getShort();//taking size

		userMessageSettings = UserMessageSettings.getById(Byte.toUnsignedInt(payload.get()));
		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		sessionId=Short.toUnsignedInt(payload.getShort());
		identifier=Short.toUnsignedInt(payload.getShort());
		credit=Short.toUnsignedInt(payload.getShort());
		time=Short.toUnsignedInt(payload.getShort());


		message =ByteBufferUtils.toIntList(ByteBufferUtils.readRest(payload));
	}

	public UserMessageGuest(UserMessage userMessage, int deviceSessionId, int identifier, int credit, int time) {
		this(userMessage.getUserMessageSettings(), userMessage.getReceiverDeviceId(),userMessage.getSenderDeviceId(),deviceSessionId,identifier,
				credit, time,  userMessage.getMessage());
	}


	@Override
	public ByteBuffer toByteBuffer() {


		int size=12+1+message.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte) userMessageSettings.getId());
		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putShort((short)senderDeviceId);

		byteBuffer.putShort((short)sessionId);
		byteBuffer.putShort((short)identifier);
		byteBuffer.putShort((short)credit);
		byteBuffer.putShort((short)time);

		byteBuffer.put(ByteBufferUtils.getByteArray(message));

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.USER_MESSAGE_GUEST;
	}

	public List<Integer> getMessage() {
		return message;
	}
}
