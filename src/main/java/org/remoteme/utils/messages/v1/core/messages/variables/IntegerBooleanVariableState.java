package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.core.messages.variables.values.IntegerBooleanVV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class IntegerBooleanVariableState extends AVariableState<IntegerBooleanVV> {




	protected IntegerBooleanVariableState() {
	}


	public IntegerBooleanVariableState(String name, IntegerBooleanVV data) {
		super(name, data);
	}

	public IntegerBooleanVariableState(String name, int value, boolean bool) {
		this(name, new IntegerBooleanVV(value, bool));
	}

	public IntegerBooleanVariableState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected VariableType getType() {
		return VariableType.INTEGER_BOOLEAN;
	}


}
