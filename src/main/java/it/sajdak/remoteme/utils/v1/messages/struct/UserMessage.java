package it.sajdak.remoteme.utils.v1.messages.struct;


import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.enums.UserMessageSettings;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.List;


@Getter
@Setter
public class UserMessage extends ARemoteMeMessage {






	UserMessageSettings renevalWhenFailType;//1
	int receiverDeviceId;//2
	int senderDeviceId;//2
	int messageId;//2
	byte message[];//size




	public UserMessage(UserMessageSettings renevalWhenFailType, int receiverDeviceId, int senderDeviceId, int messageId, String hexData) {
		this(renevalWhenFailType, receiverDeviceId, senderDeviceId, messageId, ByteBufferUtils.hexStringToListInteger(hexData));
	}

	public UserMessage(UserMessageSettings renevalWhenFailType, int receiverDeviceId, int senderDeviceId, int messageId, List<Integer> data) {
		if (data.size()==1 && data.get(0)==null){
			data.clear();
		}

		this.renevalWhenFailType = renevalWhenFailType;
		this.receiverDeviceId = receiverDeviceId;
		this.senderDeviceId = senderDeviceId;
		this.messageId = messageId;
		this.message = new byte[data.size()];

		for(int i=0;i<data.size();i++){
			this.message[i]=data.get(i).byteValue();
		}


	}




	public UserMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		renevalWhenFailType = UserMessageSettings.getById(Byte.toUnsignedInt(payload.get()));
		receiverDeviceId = Short.toUnsignedInt(payload.getShort());
		senderDeviceId = Short.toUnsignedInt(payload.getShort());
		messageId = Short.toUnsignedInt(payload.getShort());

		message = ByteBufferUtils.readRest(payload);
	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=1+2+2+2+message.length;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte)renevalWhenFailType.getId());
		byteBuffer.putShort((short)receiverDeviceId);
		byteBuffer.putShort((short)senderDeviceId);
		byteBuffer.putShort((short)messageId);
		byteBuffer.put(message);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.USER_MESSAGE;
	}

}
