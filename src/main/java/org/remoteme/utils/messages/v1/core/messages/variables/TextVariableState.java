package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class TextVariableState extends AVariableState<String> {

	protected TextVariableState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public TextVariableState(String name, String data) {
		super(name, data);
	}

	public TextVariableState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.put(ByteBufferUtils.writeString(getData()));
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		setData(ByteBufferUtils.readString(output));

	}

	@Override
	protected int getDataSize() {
		return ByteBufferUtils.writeString(getData()).length;
	}

	@Override
	protected VariableType getType() {
		return VariableType.TEXT;
	}
}
