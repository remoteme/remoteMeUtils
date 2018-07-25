package it.sajdak.remoteme.utils.v1.core.messages;


import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.enums.WebRTCConnectionStatus;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;


@Getter
@Setter
public class WebRRCConnectionStatusChangeMessage extends ARemoteMeMessage {



	int webPageDeviceId;//2
	int rasbperryPiDeviceId;
	WebRTCConnectionStatus status;


	public WebRRCConnectionStatusChangeMessage() {
	}

	public WebRRCConnectionStatusChangeMessage(int webPageDeviceId, int rasbperryPiDeviceId, WebRTCConnectionStatus status) {


		this.status=status;

		this.webPageDeviceId = webPageDeviceId;
		this.rasbperryPiDeviceId = rasbperryPiDeviceId;



	}




	public WebRRCConnectionStatusChangeMessage(ByteBuffer payload) {
		payload.getShort();//taking size

		status = WebRTCConnectionStatus.getById(Byte.toUnsignedInt(payload.get()));
		webPageDeviceId =Short.toUnsignedInt(payload.getShort());

		rasbperryPiDeviceId = Short.toUnsignedInt(payload.getShort());
	}




	@Override
	public ByteBuffer toByteBuffer() {


		int size=2+2+1;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte)status.getId());
		byteBuffer.putShort((short)webPageDeviceId);
		byteBuffer.putShort((short)rasbperryPiDeviceId);

		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.WEB_RTC_CONNECTION_CHANGE;
	}

}
