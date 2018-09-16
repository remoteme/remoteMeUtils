package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class DoubleVariableState extends AVariableState<Double> {

	protected DoubleVariableState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public DoubleVariableState(String name, Double data) {
		super(name, data);
	}

	public DoubleVariableState(ByteBuffer output) {
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
	protected VariableType getType() {
		return VariableType.DOUBLE;
	}
}
