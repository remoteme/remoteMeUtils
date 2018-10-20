package org.remoteme.utils.messages.v1.core.messages.variables.values;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}