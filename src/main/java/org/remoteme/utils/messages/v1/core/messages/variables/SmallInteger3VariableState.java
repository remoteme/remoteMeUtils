package org.remoteme.utils.messages.v1.core.messages.variables;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.VariableType;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class SmallInteger3VariableState extends AVariableState<SmallInteger3VariableState.SmallInteger3> {

	@EqualsAndHashCode
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SmallInteger3 implements Serializable {
		int i1;
		int i2;
		int i3;
	}

	protected SmallInteger3VariableState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public SmallInteger3VariableState(String name, int v1,int v2,int v3) {
		this(name, new SmallInteger3(v1, v2, v3));
	}

	public SmallInteger3VariableState(String name, SmallInteger3VariableState.SmallInteger3 data) {
		super(name, data);
	}

	public SmallInteger3VariableState(ByteBuffer output) {
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
		SmallInteger3VariableState.SmallInteger3 si2 = new SmallInteger3VariableState.SmallInteger3();
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
	protected VariableType getType() {
		return VariableType.SMALL_INTEGER_3;
	}
}
