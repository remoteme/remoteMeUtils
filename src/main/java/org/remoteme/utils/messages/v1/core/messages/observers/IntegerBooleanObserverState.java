package org.remoteme.utils.messages.v1.core.messages.observers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.VariableOberverType;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class IntegerBooleanObserverState extends AObserverState<IntegerBooleanObserverState.IntegerBoolean> {



	@EqualsAndHashCode
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class IntegerBoolean implements Serializable {
		int i;
		boolean b;
	}

	protected IntegerBooleanObserverState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public IntegerBooleanObserverState(String name, IntegerBoolean data) {
		super(name, data);
	}

	public IntegerBooleanObserverState(String name, int value, boolean bool) {
		this(name, new IntegerBoolean(value, bool));
	}

	public IntegerBooleanObserverState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.putInt(getData().getI());
		output.put((byte)(getData().isB()?1:0));
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		IntegerBoolean ib = new IntegerBoolean();
		ib.setI(output.getInt());
		ib.setB(output.get()==1);
		setData(ib);
	}

	@Override
	protected int getDataSize() {
		return 4+1;
	}

	@Override
	protected VariableOberverType getType() {
		return VariableOberverType.INTEGER_BOOLEAN;
	}
}
