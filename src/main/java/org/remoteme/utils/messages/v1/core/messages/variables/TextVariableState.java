package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.core.messages.variables.values.TextVV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class TextVariableState extends AVariableState<TextVV> {

	protected TextVariableState() {
	}


	public TextVariableState(String name, TextVV data) {
		super(name, data);

	}

	public TextVariableState(String name, String data) {
		this(name, new TextVV(data));
	}


	public TextVariableState(ByteBuffer output) {
		super(output);
	}




	@Override
	protected VariableType getType() {
		return VariableType.TEXT;
	}
}
