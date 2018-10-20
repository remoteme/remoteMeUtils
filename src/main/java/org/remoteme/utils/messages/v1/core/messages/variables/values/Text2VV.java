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
@AllArgsConstructor
@NoArgsConstructor
public class Text2VV extends AVariableValue implements Serializable {
	String s1="";
	String s2="";


	public Text2VV(String rendered) {
		String[] split = rendered.replaceAll(", ", ",").split(",", 2);
		s1 = split[0];
		s2 = split[1];
	}

	@Override
	public String toString() {
		return getS1() + ", " + getS2();
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.put(ByteBufferUtils.writeString(getS1()));
		output.put(ByteBufferUtils.writeString(getS2()));
	}


	public Text2VV(ByteBuffer output) {
		setS1(ByteBufferUtils.readString(output));
		setS2(ByteBufferUtils.readString(output));


	}

	@Override
	public int getDataSize() {
		return ByteBufferUtils.writeString(getS1()).length+ByteBufferUtils.writeString(getS2()).length;
	}
}