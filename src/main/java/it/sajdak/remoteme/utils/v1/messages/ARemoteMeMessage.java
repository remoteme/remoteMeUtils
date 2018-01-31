package it.sajdak.remoteme.utils.v1.messages;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.sajdak.remoteme.utils.v1.enums.MessageType;

import java.io.Serializable;
import java.nio.ByteBuffer;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "__class")
public abstract class ARemoteMeMessage implements Serializable {



	public abstract ByteBuffer toByteBuffer();

	public abstract MessageType getMessageType() ;

}
