package org.remoteme.utils.messages.v1.core.messages.variables.values;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;

import java.io.Serializable;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
public class TextVV extends AVariableValue implements Serializable {
	String s="";


	public TextVV(String rendered) {
		s=rendered;
	}

	@Override
	public String toString() {
		return s;
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.put(ByteBufferUtils.writeString(getS()));
	}


	public TextVV(ByteBuffer output) {
		setS(ByteBufferUtils.readString(output));

	}

	@Override
	public int getDataSize() {
		return ByteBufferUtils.writeString(getS()).length;
	}
}