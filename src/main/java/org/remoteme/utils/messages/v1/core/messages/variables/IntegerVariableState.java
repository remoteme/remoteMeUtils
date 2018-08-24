package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.enums.VariableType;

import java.nio.ByteBuffer;

public class IntegerVariableState extends AVariableState<Integer> {

	protected IntegerVariableState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public IntegerVariableState(String name, Integer data) {
		super(name, data);
	}

	public IntegerVariableState(ByteBuffer output) {
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
	protected VariableType getType() {
		return VariableType.INTEGER;
	}
}
