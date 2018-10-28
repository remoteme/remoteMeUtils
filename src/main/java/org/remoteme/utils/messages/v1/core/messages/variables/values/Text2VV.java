package org.remoteme.utils.messages.v1.core.messages.variables.values;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Text2VV extends AVariableValue implements Serializable {
	String t1="";
	String t2="";


	public Text2VV(String rendered) {
		String[] split = rendered.replaceAll(", ", ",").split(",", 2);
		t1 = split[0];
		t2 = split[1];
	}

	@Override
	public String toString() {
		return getT1() + ", " + getT2();
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.put(ByteBufferUtils.writeString(getT1()));
		output.put(ByteBufferUtils.writeString(getT2()));
	}


	public Text2VV(ByteBuffer output) {
		setT1(ByteBufferUtils.readString(output));
		setT2(ByteBufferUtils.readString(output));


	}

	@Override
	public int getDataSize() {
		return ByteBufferUtils.writeString(getT1()).length+ByteBufferUtils.writeString(getT2()).length;
	}

	@Override
	public VariableType getType() {
		return VariableType.TEXT_2;
	}
}