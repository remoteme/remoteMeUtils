package it.sajdak.remoteme.utils.v1.ar.messages;

import it.sajdak.remoteme.utils.v1.core.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.general.ByteBufferUtils;
import it.sajdak.remoteme.utils.v1.core.messages.AddDataMessage;
import it.sajdak.remoteme.utils.v1.core.messages.SyncResponseMessage;
import it.sajdak.remoteme.utils.v1.core.messages.SyncUserMessage;
import it.sajdak.remoteme.utils.v1.core.messages.UserMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
public abstract class ARemoteMeMessageDto extends AMessageDto {

	public abstract ARemoteMeMessage getRemoteMeMessage();

	public static ARemoteMeMessageDto convert(ARemoteMeMessage aMessage){
		if (aMessage instanceof UserMessage){
			UserMessage userMessage = (UserMessage) aMessage;

			UserMessageDto ret = new UserMessageDto();
			ret.setMessageSettings(userMessage.getUserMessageSettings());
			ret.setSenderDeviceId(userMessage.getSenderDeviceId());
			ret.setReceiveDeviceId(userMessage.getReceiverDeviceId());
			ret.setMessageId(userMessage.getMessageId());
			ret.setMessage(ByteBufferUtils.toIntList(userMessage.getMessage()));

			return ret;
		}else if (aMessage instanceof AddDataMessage){
			AddDataMessage dataMessage = (AddDataMessage) aMessage;
			AddDataMessageDto ret = new AddDataMessageDto();
			ret.setTime(dataMessage.getTime());
			ret.setSettings(dataMessage.getSettings());
			ret.setDataSeries(dataMessage.getDataSeries().stream().map(x->new AddDataMessageDto.DataSeriesDto(x.getSeriesId(),x.getValue())).collect(Collectors.toList()));


			return ret;
		}else if (aMessage instanceof SyncResponseMessage){
			SyncResponseMessage syncResponse = (SyncResponseMessage) aMessage;
			SyncResponseMessageDto ret = new SyncResponseMessageDto();
			ret.setMessageId(syncResponse.getMessageId());
			ret.setMessage(ByteBufferUtils.toIntList(syncResponse.getMessage()));


			return ret;
		}else if (aMessage instanceof SyncUserMessage){
			SyncUserMessage syncResponse = (SyncUserMessage) aMessage;
			SyncUserMessageDto ret = new SyncUserMessageDto();
			ret.setReceiverDeviceId(syncResponse.getReceiverDeviceId());
			ret.setMessageId(syncResponse.getMessageId());
			ret.setMessage(ByteBufferUtils.toIntList(syncResponse.getMessage()));
			ret.setSenderDeviceId(syncResponse.getSenderDeviceId());


			return ret;
		}
		throw new RuntimeException(aMessage.getClass().getSimpleName() +" is not supported yet");
	}
}
