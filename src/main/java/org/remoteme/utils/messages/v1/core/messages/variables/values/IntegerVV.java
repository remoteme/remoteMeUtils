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
public class IntegerVV extends AVariableValue implements Serializable {
	Integer i;


	public IntegerVV(String rendered) {
		i = Integer.valueOf(rendered);
	}

	public  IntegerVV(ByteBuffer output) {
		setI(output.getInt());
	}

	@Override
	public String toString() {
		return String.valueOf(i);
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.putInt(getI());
	}

	@Override
	public int getDataSize() {
		return 4;
	}

	@Override
	public VariableType getType() {
		return VariableType.INTEGER;
	}
}