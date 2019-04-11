package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.MessageType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Getter
public class SendPushNotificationMessage extends ARemoteMeMessage {

	@Getter

	public static class PushNotification{
		String title;
		String body;
		String badge;
		String icon;
		String image;
		List<Integer> vibrate= null;

		public PushNotification(String title, String body, String badge, String icon, String image, List<Integer> vibrate) {

			this.title =nullIfEmpty( title);
			this.body =nullIfEmpty( body);
			this.badge =nullIfEmpty( badge);
			this.icon =nullIfEmpty( icon);
			this.image =nullIfEmpty( image);
			this.vibrate = vibrate;
		}


		private String nullIfEmpty(String title) {
			if (title==null || title.isEmpty()){
				return null;
			}else{
				return title;
			}
		}
	}


	int deviceId;
	PushNotification pushNotification;


	protected SendPushNotificationMessage() {

	}

	public SendPushNotificationMessage(ByteBuffer payload) {
		int size=payload.getShort();//taking size

		deviceId=Short.toUnsignedInt(payload.getShort());
		String title=ByteBufferUtils.readString(payload);
		String body=ByteBufferUtils.readString(payload);
		String badge=ByteBufferUtils.readString(payload);
		String icon=ByteBufferUtils.readString(payload);
		String image=ByteBufferUtils.readString(payload);

		int vibrateCount=Byte.toUnsignedInt(payload.get());
		List<Integer> vibrate = new ArrayList<>(vibrateCount);
		for(int i=0;i<vibrateCount;i++){
			vibrate.add(Byte.toUnsignedInt(payload.get())*10);
		}

		pushNotification = new PushNotification(title, body, badge, icon, image, vibrate);

	}



	@Override
	public ByteBuffer toByteBuffer() {

		byte[] title= ByteBufferUtils.writeString(pushNotification.getTitle());
		byte[] body= ByteBufferUtils.writeString(pushNotification.getBody());
		byte[] badge= ByteBufferUtils.writeString(pushNotification.getBadge());
		byte[] icon= ByteBufferUtils.writeString(pushNotification.getIcon());
		byte[] image= ByteBufferUtils.writeString(pushNotification.getImage());



		int size=2+title.length+body.length+icon.length+image.length+badge.length+ 1+pushNotification.vibrate.size();

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);
		byteBuffer.putShort((short)deviceId);

		byteBuffer.put(title);
		byteBuffer.put(body);
		byteBuffer.put(badge);
		byteBuffer.put(icon);
		byteBuffer.put(image);
		byteBuffer.put((byte)pushNotification.vibrate.size());

		for (Integer vibrate : pushNotification.vibrate) {
			byteBuffer.put((byte)(vibrate/10));
		}



		byteBuffer.clear();

		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SEND_PUSH_NOTIFICATION;
	}




}
