package org.remoteme.utils.messages.v1.core.messages.observerStates;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class SmallInteger2ObserverState extends AObserverState<SmallInteger2ObserverState.SmallInteger2> {



	@EqualsAndHashCode
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SmallInteger2 implements Serializable {
		int i1;
		int i2;
	}

	protected SmallInteger2ObserverState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public SmallInteger2ObserverState(String name, SmallInteger2 data) {
		super(name, data);
	}

	public SmallInteger2ObserverState(String name, int v1, int v2) {
		this(name, new SmallInteger2(v1, v2));
	}

	public SmallInteger2ObserverState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.putShort((short)getData().getI1());
		output.putShort((short)getData().getI2());
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		SmallInteger2 si2 = new SmallInteger2();
		si2.setI1(output.getShort());
		si2.setI2(output.getShort());

		setData(si2);
	}

	@Override
	protected int getDataSize() {
		return 4;
	}

	@Override
	protected VariableOberverType getType() {
		return VariableOberverType.SMALL_INTEGER_2;
	}
}
