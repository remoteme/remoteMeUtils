package org.remoteme.utils.messages.v1.core.messages.change;

import java.nio.ByteBuffer;

public class BooleanObserverState extends AObserverState<Boolean> {


	protected BooleanObserverState() {
	}

	public BooleanObserverState(String name, Boolean data) {
		super(name, data);
	}

	public BooleanObserverState(ByteBuffer output) {
		super(output);
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
	protected VariableOberverType getType() {
		return VariableOberverType.BOOLEAN;
	}
}
