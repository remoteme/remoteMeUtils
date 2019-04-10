package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import com.sun.org.apache.bcel.internal.generic.PushInstruction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.general.Pair;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
public class SendPushNotificationMessage extends ARemoteMeMessage {

	@Getter
	@AllArgsConstructor
	public static class PushNotification{
		String title;
		String body;
		String image;
		String icon;
		String badge;
		List<Integer> vibrate;

	}


	int deviceId;
	PushNotification pushNotification;


	protected SendPushNotificationMessage() {

	}

	public SendPushNotificationMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		deviceId=Short.toUnsignedInt(payload.getShort());
		String title=ByteBufferUtils.readString(payload);
		String body=ByteBufferUtils.readString(payload);
		String icon=ByteBufferUtils.readString(payload);
		String image=ByteBufferUtils.readString(payload);
		String badge=ByteBufferUtils.readString(payload);

		int vibrateCount=Byte.toUnsignedInt(payload.get());
		List<Integer> vibrate = new ArrayList<>(vibrateCount);
		for(int i=0;i<vibrateCount;i++){
			vibrate.add(Byte.toUnsignedInt(payload.get())*10);
		}

		pushNotification = new PushNotification(title, body, image, icon, badge, vibrate);

	}



	@Override
	public ByteBuffer toByteBuffer() {

		byte[] title= ByteBufferUtils.writeString(pushNotification.getTitle());
		byte[] body= ByteBufferUtils.writeString(pushNotification.getBody());
		byte[] icon= ByteBufferUtils.writeString(pushNotification.getIcon());
		byte[] image= ByteBufferUtils.writeString(pushNotification.getImage());
		byte[] badge= ByteBufferUtils.writeString(pushNotification.getBadge());



		int size=2+title.length+body.length+icon.length+image.length+badge.length+ 1+pushNotification.vibrate.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);
		byteBuffer.putShort((short)deviceId);

		byteBuffer.put(title);
		byteBuffer.put(body);
		byteBuffer.put(icon);
		byteBuffer.put(image);
		byteBuffer.put(badge);
		byteBuffer.put((byte)pushNotification.vibrate.size());

		for (Integer vibrate : pushNotification.vibrate) {
			byteBuffer.put((byte)(vibrate/10));
		}



		byteBuffer.clear();

		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SEND_PUSH_MESSAGE;
	}




}
