package org.remoteme.utils.messages.v1.core.messages.observers;

import java.nio.ByteBuffer;

public class DoubleObserverState extends AObserverState<Double> {

	protected DoubleObserverState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public DoubleObserverState(String name, Double data) {
		super(name, data);
	}

	public DoubleObserverState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.putDouble(getData());
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		setData(output.getDouble());
	}

	@Override
	protected int getDataSize() {
		return 8;
	}

	@Override
	protected VariableOberverType getType() {
		return VariableOberverType.DOUBLE;
	}
}
