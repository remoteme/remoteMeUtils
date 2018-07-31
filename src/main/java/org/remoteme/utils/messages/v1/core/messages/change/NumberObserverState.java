package org.remoteme.utils.messages.v1.core.messages.change;

import java.nio.ByteBuffer;

public class NumberObserverState extends AObserverState<Integer> {

	protected NumberObserverState() {
	}

	public NumberObserverState(String name, Integer data) {
		super(name, data);
	}

	public NumberObserverState(ByteBuffer output) {
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
		return VariableOberverType.NUMBER;
	}
}
