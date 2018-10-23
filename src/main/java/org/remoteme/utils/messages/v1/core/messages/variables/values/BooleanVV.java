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
public class BooleanVV extends AVariableValue implements Serializable {
	boolean b;


	public BooleanVV(String rendered) {
		b=Boolean.valueOf(rendered);
	}

	@Override
	public String toString() {
		return String.valueOf(b);
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.put((byte)(b?1:0));
	}


	public BooleanVV(ByteBuffer output) {
		setB(output.get()==1);
	}

	@Override
	public int getDataSize() {
		return 1;
	}

	@Override
	public VariableType getType() {
		return VariableType.BOOLEAN;
	}

}