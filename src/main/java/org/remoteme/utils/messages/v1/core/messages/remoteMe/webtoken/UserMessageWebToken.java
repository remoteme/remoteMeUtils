package org.remoteme.utils.messages.v1.core.messages.remoteMe.webtoken;




import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.ARemoteMeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.UserMessage;
import org.remoteme.utils.messages.v1.enums.MessageType;
import org.remoteme.utils.messages.v1.enums.UserMessageSettings;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class UserMessageWebToken extends ARemoteMeMessage {

	private UserMessageSettings userMessageSettings;//1
	private int receiverDeviceId;//2
	private int senderDeviceId;//2
	private int sessionId;//2
	private int credit;//2
	private int time;//2



	List<Integer> message;//size


	protected UserMessageWebToken() {
	}


	public UserMessageWebToken(UserMessageSettings userMessageSettings, int receiverDeviceId, int senderDeviceId, int sessionId,	int credit,	int time, String hexData) {
		this(userMessageSettings, receiverDeviceId, senderDeviceId,sessionId, credit, time, ByteBufferUtils.hexStringToListInteger(hexData));
	}

	public UserMessageWebToken(UserMessageSettings userMessageSettings, int receiverDeviceId, int senderDeviceId, int sessionId,	int credit,	int time, List<Integer> data) {
		if (data.size()==1 && data.get(0)==null){
			data.clear();
		}

		this.userMessageSettings = userMessageSettings;
		this.receiverDeviceId = receiverDeviceId;
		this.senderDeviceId = senderDeviceId;
		this.sessionId=sessionId;
		this.credit=credit;
		this.time=time;

		this.message =new ArrayList<>(data);


	}




	public UserMessageWebToken(ByteBuffer payload) {
		payload.getShort();//taking size

		userMessageSettings = UserMessageSettings.getById(Byte.toUnsignedInt(payload.get()));
		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		sessionId=Short.toUnsignedInt(payload.getShort());
		credit=Short.toUnsignedInt(payload.getShort());
		time=Short.toUnsignedInt(payload.getShort());


		message =ByteBufferUtils.toIntList(ByteBufferUtils.readRest(payload));
	}

	public UserMessageWebToken(UserMessage userMessage, int deviceSessionId, int credit, int time) {
		this(userMessage.getUserMessageSettings(), userMessage.getReceiverDeviceId(),userMessage.getSenderDeviceId(),deviceSessionId,
				credit, time,  userMessage.getMessage());
	}


	@Override
	public ByteBuffer toByteBuffer() {


		int size=10+1+message.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte) userMessageSettings.getId());
		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putShort((short)senderDeviceId);

		byteBuffer.putShort((short)sessionId);
		byteBuffer.putShort((short)credit);
		byteBuffer.putShort((short)time);

		byteBuffer.put(ByteBufferUtils.getByteArray(message));

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.USER_MESSAGE_WEBPAGE_TOKEN;
	}

	public List<Integer> getMessage() {
		return message;
	}
}
