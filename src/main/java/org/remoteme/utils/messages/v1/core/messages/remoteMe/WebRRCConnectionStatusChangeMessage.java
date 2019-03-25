package org.remoteme.utils.messages.v1.core.messages.remoteMe;



import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.MessageType;
import org.remoteme.utils.messages.v1.enums.WebRTCConnectionStatus;

import java.nio.ByteBuffer;


@Getter
@Setter
public class WebRRCConnectionStatusChangeMessage extends ARemoteMeMessage {



	int webPageDeviceId;//2
	int raspberryPiDeviceId;
	WebRTCConnectionStatus status;


	public WebRRCConnectionStatusChangeMessage() {
	}

	public WebRRCConnectionStatusChangeMessage(int webPageDeviceId, int raspberryPiDeviceId, WebRTCConnectionStatus status) {


		this.status=status;

		this.webPageDeviceId = webPageDeviceId;
		this.raspberryPiDeviceId = raspberryPiDeviceId;



	}




	public WebRRCConnectionStatusChangeMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		status = WebRTCConnectionStatus.getById(Byte.toUnsignedInt(payload.get()));
		webPageDeviceId =Short.toUnsignedInt(payload.getShort());

		raspberryPiDeviceId = Short.toUnsignedInt(payload.getShort());
	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2+1;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte)status.getId());
		byteBuffer.putShort((short)webPageDeviceId);
		byteBuffer.putShort((short)raspberryPiDeviceId);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.WEB_RTC_CONNECTION_CHANGE;
	}

}
