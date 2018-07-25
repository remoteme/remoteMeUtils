package it.sajdak.remoteme.utils.v1.ar.messages;

import io.swagger.annotations.ApiModelProperty;
import it.sajdak.remoteme.utils.v1.enums.UserMessageSettings;
import it.sajdak.remoteme.utils.v1.core.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.core.messages.UserMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserMessageDto extends ARemoteMeMessageDto {


	@ApiModelProperty(notes = "Message  settings if we wait with deliver - for now not supported. two possibilities NO_RENEWAL, RENEWAL_IF_FAILED",required = false)
	UserMessageSettings messageSettings=UserMessageSettings.NO_RENEWAL;
	@ApiModelProperty(notes = "Device from where it was send",required = true)
	int senderDeviceId;
	@ApiModelProperty(notes = "Where should be delivered",required = true)
	int receiveDeviceId;
	@ApiModelProperty(notes = "not supported yet",required = false)
	int messageId=0;
	@ApiModelProperty(notes = "Array of bytes",required = true)
	List<Integer> message;


	@Override
	public ARemoteMeMessage getRemoteMeMessage() {


		return new UserMessage(this.getMessageSettings(), this.getReceiveDeviceId(), this.getSenderDeviceId(), this.getMessageId(),this.getMessage());

	}
}
