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
public class SmallInteger2VV extends AVariableValue implements Serializable {
	int i1;
	int i2;

	public SmallInteger2VV(String rendered) {
		String[] split = rendered.replaceAll(", ", ",").split(",");
		i1 = Integer.valueOf(split[0]);
		i2 = Integer.valueOf(split[1]);
	}

	public SmallInteger2VV(ByteBuffer output) {
		setI1(output.getShort());
		setI2(output.getShort());


	}

	@Override
	public String toString() {
		return getI1() + ", " + getI2();
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.putShort((short)getI1());
		output.putShort((short)getI2());
	}


	@Override
	public int getDataSize() {
		return 4;
	}

	@Override
	public VariableType getType() {
		return VariableType.SMALL_INTEGER_2;
	}

	@Override
	public double toDouble() {
		return (i1+i2)/2.0;
	}

}