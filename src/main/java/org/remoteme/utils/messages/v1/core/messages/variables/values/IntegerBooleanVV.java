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
public class IntegerBooleanVV extends AVariableValue implements Serializable {
	int i;
	boolean b;


	public  IntegerBooleanVV(ByteBuffer output) {
		setI(output.getInt());
		setB(output.get()==1);

	}

	public IntegerBooleanVV(String rendered) {
		String[] split = rendered.replaceAll(", ", ",").split(",", 2);
		i = Integer.valueOf(split[0]);
		b = Boolean.valueOf(split[1]);
	}

	@Override
	public String toString() {
		return getI() + ", " + isB();
	}

	@Override
	public void serializeData(ByteBuffer output) {
		output.putInt(getI());
		output.put((byte)(isB()?1:0));
	}


	@Override
	public int getDataSize() {
		return 4+1;
	}

	@Override
	public VariableType getType() {
		return VariableType.INTEGER_BOOLEAN;
	}
}
