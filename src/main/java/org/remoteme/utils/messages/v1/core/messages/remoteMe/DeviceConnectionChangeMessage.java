package org.remoteme.utils.messages.v1.core.messages.remoteMe;




import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.general.Pair;
import org.remoteme.utils.messages.v1.enums.DeviceType;
import org.remoteme.utils.messages.v1.enums.MessageType;
import org.remoteme.utils.messages.v1.enums.NetworkDeviceType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
public class DeviceConnectionChangeMessage extends ARemoteMeMessage {



	List<Pair<Integer,Boolean>> status =new ArrayList<>();



	protected DeviceConnectionChangeMessage() {
	}

	public DeviceConnectionChangeMessage(ByteBuffer payload) {
		int size =payload.getShort()/3;
		while(size-->0){
			status.add(new Pair<>( Short.toUnsignedInt(payload.getShort()),payload.get()==1));
		}

	}

	public DeviceConnectionChangeMessage(Collection<Integer> connected) {
		for (Integer deviceId : connected) {
			status.add(new Pair<>(deviceId,true));
		}

	}

	public DeviceConnectionChangeMessage(int deviceId, boolean status) {
		this.status.add(new Pair<>( deviceId,status));

	}


	@Override
	public ByteBuffer toByteBuffer() {

		int size=status.size()*3;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);


		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		for (Pair<Integer, Boolean> statusTemp : status) {
			byteBuffer.putShort((statusTemp.getFirst().shortValue()));
			byteBuffer.put((byte)(statusTemp.getSecond()?1:0));
		}


		byteBuffer.clear();

		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.DEVICE_CONNECTION_CHANGE;
	}




}
