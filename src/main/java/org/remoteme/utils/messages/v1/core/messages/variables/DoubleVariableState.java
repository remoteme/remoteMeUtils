package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.core.messages.variables.values.DoubleVV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class DoubleVariableState extends AVariableState<DoubleVV> {

	protected DoubleVariableState() {
	}


	public DoubleVariableState(String name, DoubleVV data) {
		super(name, data);
	}

	public DoubleVariableState(ByteBuffer output) {
		super(output);
	}

	public DoubleVariableState(String name, double data) {
		this(name, new DoubleVV());
	}


	@Override
	protected VariableType getType() {
		return VariableType.DOUBLE;
	}

}
