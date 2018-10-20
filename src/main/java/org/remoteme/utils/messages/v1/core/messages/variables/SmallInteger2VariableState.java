package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.core.messages.variables.values.SmallInteger2VV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class SmallInteger2VariableState extends AVariableState<SmallInteger2VV> {





	protected SmallInteger2VariableState() {
	}




	public SmallInteger2VariableState(String name, SmallInteger2VV data) {
		super(name, data);
	}

	public SmallInteger2VariableState(String name, int v1, int v2) {
		this(name, new SmallInteger2VV(v1, v2));
	}

	public SmallInteger2VariableState(ByteBuffer output) {
		super(output);
	}



	@Override
	protected VariableType getType() {
		return VariableType.SMALL_INTEGER_2;
	}
}
