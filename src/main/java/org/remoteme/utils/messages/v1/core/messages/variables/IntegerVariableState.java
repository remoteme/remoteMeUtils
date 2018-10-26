package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.core.messages.variables.values.IntegerVV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class IntegerVariableState extends AVariableState<IntegerVV> {

	protected IntegerVariableState() {
	}

	public IntegerVariableState(String name, Integer data) {
		super(name, new IntegerVV(data));
	}

	public IntegerVariableState(String name, IntegerVV data) {
		super(name, data);
	}

	public IntegerVariableState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected VariableType getType() {
		return VariableType.INTEGER;
	}
}
