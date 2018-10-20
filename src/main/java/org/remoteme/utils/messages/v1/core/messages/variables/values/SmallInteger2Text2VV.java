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
public  class SmallInteger2Text2VV extends AVariableValue implements Serializable {
	int i1;
	int i2;
	String s1="";
	String s2="";

	public SmallInteger2Text2VV(String rendered) {
		String[] split = rendered.replaceAll(", ", ",").split(",", 4);
		i1 = Integer.valueOf(split[0]);
		i2 = Integer.valueOf(split[1]);
		s1 = split[3];
		s1 = split[4];
	}

	public  SmallInteger2Text2VV(ByteBuffer output) {

		setI1(output.getShort());
		setI2(output.getShort());
		setS1(ByteBufferUtils.readString(output));
		setS2(ByteBufferUtils.readString(output));


	}

	@Override
	public String toString() {
		return getI1() + ", " + getI2() + ", " + getS1() + ", " + getS2();
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.putShort((short)getI1());
		output.putShort((short)getI2());
		output.put(ByteBufferUtils.writeString(getS1()));
		output.put(ByteBufferUtils.writeString(getS2()));
	}


	@Override
	public int getDataSize() {
		return 4+ ByteBufferUtils.writeString(getS1()).length+ByteBufferUtils.writeString(getS2()).length;
	}
}