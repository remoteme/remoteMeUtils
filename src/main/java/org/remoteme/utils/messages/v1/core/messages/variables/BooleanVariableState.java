package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.core.messages.variables.values.BooleanVV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class BooleanVariableState extends AVariableState<BooleanVV> {


	protected BooleanVariableState() {
	}

	public BooleanVariableState(String name, Boolean data) {
		super(name, new BooleanVV(data));
	}
	public BooleanVariableState(String name, BooleanVV data) {
		super(name, data);
	}

	public BooleanVariableState(ByteBuffer output) {
		super(output);
	}




}
