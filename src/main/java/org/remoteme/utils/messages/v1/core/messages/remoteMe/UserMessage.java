package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.MessageType;
import org.remoteme.utils.messages.v1.enums.UserMessageSettings;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class UserMessage extends ARemoteMeMessage {

	protected UserMessageSettings userMessageSettings;//1
	protected int receiverDeviceId;//2
	protected int senderDeviceId;//2
	protected int messageId;//2

	protected List<Integer> message;//size


	protected UserMessage() {
	}


	public UserMessage(UserMessageSettings userMessageSettings, int receiverDeviceId, int senderDeviceId, int messageId, String hexData) {
		this(userMessageSettings, receiverDeviceId, senderDeviceId, messageId, ByteBufferUtils.hexStringToListInteger(hexData));
	}

	public UserMessage(UserMessageSettings userMessageSettings, int receiverDeviceId, int senderDeviceId, int messageId, List<Integer> data) {
		if (data.size()==1 && data.get(0)==null){
			data.clear();
		}

		this.userMessageSettings = userMessageSettings;
		this.receiverDeviceId = receiverDeviceId;
		this.senderDeviceId = senderDeviceId;
		this.messageId = messageId;
		this.message =new ArrayList<>(data);


	}




	public UserMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		userMessageSettings = UserMessageSettings.getById(Byte.toUnsignedInt(payload.get()));
		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		messageId = Short.toUnsignedInt(payload.getShort());

		message =ByteBufferUtils.toIntList(ByteBufferUtils.readRest(payload));
	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=1+2+2+2+message.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte) userMessageSettings.getId());
		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putShort((short)senderDeviceId);
		byteBuffer.putShort((short)messageId);
		byteBuffer.put(ByteBufferUtils.getByteArray(message));

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.USER_MESSAGE;
	}

	public List<Integer> getMessage() {
		return message;
	}
}
