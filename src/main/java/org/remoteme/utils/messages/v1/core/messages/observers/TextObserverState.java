package org.remoteme.utils.messages.v1.core.messages.observers;

import org.remoteme.utils.general.ByteBufferUtils;

import java.nio.ByteBuffer;

public class TextObserverState extends AObserverState<String> {

	protected TextObserverState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public TextObserverState(String name, String data) {
		super(name, data);
	}

	public TextObserverState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.put(ByteBufferUtils.writeString(getData()));
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		setData(ByteBufferUtils.readString(output));

	}

	@Override
	protected int getDataSize() {
		return ByteBufferUtils.writeString(getData()).length;
	}

	@Override
	protected VariableOberverType getType() {
		return VariableOberverType.TEXT;
	}
}
