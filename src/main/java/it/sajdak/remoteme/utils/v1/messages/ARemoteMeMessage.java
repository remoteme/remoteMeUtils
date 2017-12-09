package it.sajdak.remoteme.utils.v1.messages;

import it.sajdak.remoteme.utils.v1.enums.MessageType;

import java.nio.ByteBuffer;


public abstract class ARemoteMeMessage {



	public abstract ByteBuffer toByteBuffer();

	public abstract MessageType getMessageType() ;

}
