package org.remoteme.utils.messages.v1.core.messages.variables;

import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;

public class BooleanVariableState extends AVariableState<Boolean> {


	protected BooleanVariableState() {
	}

	public BooleanVariableState(String name, Boolean data) {
		super(name, data);
	}

	public BooleanVariableState(ByteBuffer output) {
		super(output);
	}

	@Override
	public String getDataString() {
		return getData()?"true":"false";
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.put((byte)(getData()?1:0));
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		setData(output.get()==1);
	}

	@Override
	protected int getDataSize() {
		return 1;
	}

	@Override
	protected VariableType getType() {
		return VariableType.BOOLEAN;
	}
}
