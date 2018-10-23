package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.core.messages.variables.values.Text2VV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class Text2VariableState extends AVariableState<Text2VV> {




	protected Text2VariableState() {
	}



	public Text2VariableState(String name, String data,String data2) {
		this(name, new Text2VV(data, data2));
	}

	public  Text2VariableState(String name, Text2VV value) {
		super(name, value);
	}

	public Text2VariableState(ByteBuffer output) {
		super(output);
	}



}
