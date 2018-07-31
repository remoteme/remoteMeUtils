package org.remoteme.utils.messages.v1.core.messages.remoteMe;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public abstract class ASyncMessage extends ARemoteMeMessage {

	@ApiModelProperty(notes = "deviceId to where message will be send",required = false)
	protected int receiverDeviceId;//2
	@ApiModelProperty(notes = "messageId has to be random You will get it in reponse")
	long messageId;//8
	@ApiModelProperty(notes = "Array of bytes",required = true)
	List<Integer> message;

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
