package org.remoteme.utils.messages.v1.core.messages.variables.values;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
public class TextVV extends AVariableValue implements Serializable {
	String t ="";


	public TextVV(String rendered) {
		t =rendered;
	}

	@Override
	public String toString() {
		return t;
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.put(ByteBufferUtils.writeString(getT()));
	}


	public TextVV(ByteBuffer output) {
		setT(ByteBufferUtils.readString(output));

	}

	@Override
	public int getDataSize() {
		return ByteBufferUtils.writeString(getT()).length;
	}

	@Override
	public VariableType getType() {
		return VariableType.TEXT;
	}

	@Override
	public double toDouble() {
		return 0;
	}
}