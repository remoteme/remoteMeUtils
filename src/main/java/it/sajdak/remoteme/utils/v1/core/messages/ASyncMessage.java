package it.sajdak.remoteme.utils.v1.core.messages;


import it.sajdak.remoteme.utils.v1.core.messages.ARemoteMeMessage;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class ASyncMessage extends ARemoteMeMessage {

	protected int receiverDeviceId;//2
	long messageId;//8
	byte message[];//size

	/**
	 * for this timeout should be not longer then 1 min
	 * @param receiverDeviceId
	 * @return
	 */
	protected static long generateRandom(int receiverDeviceId ) {
		return ((((long)receiverDeviceId << 32)+(System.currentTimeMillis()%(2<<16)))<<32)+Math.round(Math.random()*(2<<20));
	}




	public int getReceiverDeviceId() {
		return receiverDeviceId;
	}
}
