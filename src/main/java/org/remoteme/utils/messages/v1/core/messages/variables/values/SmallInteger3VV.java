package org.remoteme.utils.messages.v1.core.messages.variables.values;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmallInteger3VV extends AVariableValue implements Serializable {
	int i1;
	int i2;
	int i3;

	public SmallInteger3VV(String rendered) {
		String[] split = rendered.replaceAll(", ", ",").split(",");
		i1 = Integer.valueOf(split[0]);
		i2 = Integer.valueOf(split[1]);
		i3 = Integer.valueOf(split[2]);
	}

	public  SmallInteger3VV(ByteBuffer output) {

		setI1(output.getShort());
		setI2(output.getShort());

		setI3(output.getShort());
	}

	@Override
	public String toString() {
		return getI1() + ", " + getI2()+ ", " + getI3();
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.putShort((short)getI1());
		output.putShort((short)getI2());
		output.putShort((short)getI3());
	}


	@Override
	public int getDataSize() {
		return 6;
	}
	@Override
	public VariableType getType() {
		return VariableType.SMALL_INTEGER_3;
	}

	@Override
	public double toDouble() {
		return (i1+i2+i3)/3.0;
	}
}