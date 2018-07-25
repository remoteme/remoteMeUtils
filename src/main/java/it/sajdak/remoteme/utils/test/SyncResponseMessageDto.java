package it.sajdak.remoteme.utils.test;

import io.swagger.annotations.ApiModelProperty;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import it.sajdak.remoteme.utils.v1.messages.struct.SyncResponseMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SyncResponseMessageDto extends ARemoteMeMessageDto {


	@ApiModelProperty(notes = "messageId tok from messageId to which one we want to reponse")
	long messageId;
	@ApiModelProperty(notes = "Array of bytes",required = true)
	List<Integer> message;



	@Override
	public ARemoteMeMessage getRemoteMeMessage() {
		return new SyncResponseMessage(messageId, ByteBufferUtils.getByteArray(message) );
	}
}
