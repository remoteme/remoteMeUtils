package org.remoteme.utils.messages.v1.core.messages.change;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class SmallInteger3ObserverState extends AObserverState<SmallInteger3ObserverState.SmallInteger3> {

	@Getter
	@Setter
	public static class SmallInteger3 implements Serializable {
		int i1;
		int i2;
		int i3;
	}

	protected SmallInteger3ObserverState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public SmallInteger3ObserverState(String name, SmallInteger3ObserverState.SmallInteger3 data) {
		super(name, data);
	}

	public SmallInteger3ObserverState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.putShort((short)getData().getI1());
		output.putShort((short)getData().getI2());
		output.putShort((short)getData().getI3());
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		SmallInteger3ObserverState.SmallInteger3 si2 = new SmallInteger3ObserverState.SmallInteger3();
		si2.setI1(output.getShort());
		si2.setI2(output.getShort());
		si2.setI3(output.getShort());

		setData(si2);
	}

	@Override
	protected int getDataSize() {
		return 6;
	}

	@Override
	protected VariableOberverType getType() {
		return VariableOberverType.SMALL_INTEGER_3;
	}
}
