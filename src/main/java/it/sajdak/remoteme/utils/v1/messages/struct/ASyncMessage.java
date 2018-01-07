package it.sajdak.remoteme.utils.v1.messages.struct;


import it.sajdak.remoteme.utils.v1.enums.MessageType;
import it.sajdak.remoteme.utils.v1.enums.SyncMessageType;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;


@Getter
@Setter
public abstract class ASyncMessage extends ARemoteMeMessage {



	/**
	 * for this timeout should be not longer then 1 min
	 * @param receiverDeviceId
	 * @return
	 */
	protected static long generateRandom(int receiverDeviceId ) {
		return ((((long)receiverDeviceId << 32)+(System.currentTimeMillis()%(2<<16)))<<32)+Math.round(Math.random()*(2<<20));
	}



	public abstract long getMessageId();


}
