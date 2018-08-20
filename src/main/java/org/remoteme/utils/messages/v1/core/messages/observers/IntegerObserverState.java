package org.remoteme.utils.messages.v1.core.messages.observers;

import java.nio.ByteBuffer;

public class IntegerObserverState extends AObserverState<Integer> {

	protected IntegerObserverState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public IntegerObserverState(String name, Integer data) {
		super(name, data);
	}

	public IntegerObserverState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.putInt(getData());
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		setData(output.getInt());
	}

	@Override
	protected int getDataSize() {
		return 4;
	}

	@Override
	protected VariableOberverType getType() {
		return VariableOberverType.INTEGER;
	}
}
