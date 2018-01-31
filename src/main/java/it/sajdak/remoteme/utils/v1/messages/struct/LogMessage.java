package it.sajdak.remoteme.utils.v1.messages.struct;


import it.sajdak.remoteme.utils.v1.enums.LogMessageLevel;
import it.sajdak.remoteme.utils.v1.messages.ARemoteMeMessage;
import it.sajdak.remoteme.utils.v1.messages.ByteBufferUtils;
import it.sajdak.remoteme.utils.v1.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;


@Getter
@Setter
public class LogMessage extends ARemoteMeMessage {






	LogMessageLevel level;//1
	String message;

	protected LogMessage() {
	}




	public LogMessage(LogMessageLevel level, String message) {

		this.level = level;
		this.message = message;

	}




	public LogMessage(ByteBuffer payload) {
		payload.getShort();//taking size
		level = LogMessageLevel.getById(Byte.toUnsignedInt(payload.get()));
		message = ByteBufferUtils.readString(payload);
	}




	@Override
	public ByteBuffer toByteBuffer() {
		byte[] mesageB=ByteBufferUtils.writeString(message);

		int size=1+mesageB.length;

		ByteBuffer byteBuffer = ByteBuffer.allocate(size+4);

		byteBuffer.putShort((short)getMessageType().getId());
		byteBuffer.putShort((short)size);


		byteBuffer.put((byte)level.getId());


		byteBuffer.put(ByteBufferUtils.writeString(message));


		byteBuffer.clear();


		return byteBuffer;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.LOG;
	}


	public static void main(String[] args) {

		int marriages = 40;
		int boys = 0;
		int girls = 0;

		for (int i = 0; i < marriages; ) {
			if (Math.random() > 0.5) {
				boys++;
				i++;
			} else {
				girls++;
			}
		}

		System.out.println("boys: " + boys + " girls:" + girls);
	}
}
