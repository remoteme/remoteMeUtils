package it.sajdak.remoteme.utils.test;

import io.swagger.annotations.ApiModelProperty;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import it.sajdak.remoteme.utils.v1.messages.struct.SyncUserMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SyncUserMessageDto extends ARemoteMeMessageDto {

	@ApiModelProperty(notes = "deviceId to where message will be send",required = false)
	int receiverDeviceId;
	@ApiModelProperty(notes = "messageId has to be random You will get it in reponse")
	long messageId;
	@ApiModelProperty(notes = "Array of bytes",required = true)
	List<Integer> message;
	@ApiModelProperty(notes = "Sending deviceId",required = true)
	int senderDeviceId;



	@Override
	public ARemoteMeMessage getRemoteMeMessage() {
		return new SyncUserMessage(receiverDeviceId, senderDeviceId,messageId, ByteBufferUtils.getByteArray(message),false );
	}
}
