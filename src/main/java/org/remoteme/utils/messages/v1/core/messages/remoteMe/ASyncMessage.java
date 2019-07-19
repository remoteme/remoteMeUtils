package org.remoteme.utils.messages.v1.core.messages.remoteMe;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public abstract class ASyncMessage extends ARemoteMeMessage {

	protected int receiverDeviceId;//2
	protected long messageId;//8
	protected List<Integer> message;

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
