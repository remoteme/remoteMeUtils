package it.sajdak.remoteme.utils.test;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use=JsonTypeInfo.Id.MINIMAL_CLASS, include=JsonTypeInfo.As.PROPERTY, property="type")
@ApiModel(value="SuperModel", discriminator = "foo", subTypes = {UserMessageDto.class
		,AddDataMessageDto.class,SyncUserMessageDto.class,SyncResponseMessageDto.class
		,PingMessageDto.class})
public abstract class AMessageDto {


}
