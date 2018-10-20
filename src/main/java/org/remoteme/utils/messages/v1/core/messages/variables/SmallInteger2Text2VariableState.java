package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.core.messages.variables.values.SmallInteger2Text2VV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class SmallInteger2Text2VariableState extends AVariableState<SmallInteger2Text2VV> {





	protected SmallInteger2Text2VariableState() {
	}


	public SmallInteger2Text2VariableState(String name, int v1, int v2, String s1,String s2) {
		this(name, new SmallInteger2Text2VV(v1, v2, s1,s2));
	}

	public SmallInteger2Text2VariableState(String name, SmallInteger2Text2VV data) {
		super(name, data);
	}

	public SmallInteger2Text2VariableState(ByteBuffer output) {
		super(output);
	}



	@Override
	protected VariableType getType() {
		return VariableType.SMALL_INTEGER_2_TEXT_2;
	}
}
