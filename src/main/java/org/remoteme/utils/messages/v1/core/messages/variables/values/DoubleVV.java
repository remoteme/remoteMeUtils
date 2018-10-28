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
public class DoubleVV extends AVariableValue implements Serializable {
	double d;
/*
	public DoubleVV(Double value)
	{
		d=value;
	}*/

	public DoubleVV(String rendered) {
		d=Double.valueOf(rendered);
	}

	public DoubleVV(ByteBuffer output) {
		setD(output.getDouble());
	}

	@Override
	public String toString() {
		return String.valueOf(d);
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.putDouble(getD());
	}


	@Override
	public int getDataSize() {
		return 8;
	}

	@Override
	public VariableType getType() {
		return VariableType.DOUBLE;
	}
}