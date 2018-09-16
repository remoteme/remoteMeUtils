package org.remoteme.utils.messages.v1.core.messages.variables;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class IntegerBooleanVariableState extends AVariableState<IntegerBooleanVariableState.IntegerBoolean> {



	@EqualsAndHashCode
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class IntegerBoolean implements Serializable {
		int i;
		boolean b;
	}

	protected IntegerBooleanVariableState() {
	}

	@Override
	public String getDataString() {
		return  getData().getI()+", "+getData().isB();
	}

	public IntegerBooleanVariableState(String name, IntegerBoolean data) {
		super(name, data);
	}

	public IntegerBooleanVariableState(String name, int value, boolean bool) {
		this(name, new IntegerBoolean(value, bool));
	}

	public IntegerBooleanVariableState(ByteBuffer output) {
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
	protected VariableType getType() {
		return VariableType.INTEGER_BOOLEAN;
	}
}
