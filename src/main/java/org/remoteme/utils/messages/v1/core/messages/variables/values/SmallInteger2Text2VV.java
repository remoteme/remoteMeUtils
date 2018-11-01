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
public  class SmallInteger2Text2VV extends AVariableValue implements Serializable {
	int i1;
	int i2;
	String t1="";
	String t2 ="";

	public SmallInteger2Text2VV(String rendered) {
		String[] split = rendered.replaceAll(", ", ",").split(",", 4);
		i1 = Integer.valueOf(split[0]);
		i2 = Integer.valueOf(split[1]);
		t1 = split[2];
		t2 = split[3];
	}

	public  SmallInteger2Text2VV(ByteBuffer output) {
		setI1(output.getShort());
		setI2(output.getShort());
		setT1(ByteBufferUtils.readString(output));
		setT2(ByteBufferUtils.readString(output));
	}

	@Override
	public String toString() {
		return getI1() + ", " + getI2() + ", " + getT1() + ", " + getT2();
	}


	@Override
	public void serializeData(ByteBuffer output) {
		output.putShort((short)getI1());
		output.putShort((short)getI2());
		output.put(ByteBufferUtils.writeString(getT1()));
		output.put(ByteBufferUtils.writeString(getT2()));
	}


	@Override
	public int getDataSize() {
		return 4+ ByteBufferUtils.writeString(getT1()).length+ByteBufferUtils.writeString(getT2()).length;
	}
	@Override
	public VariableType getType() {
		return VariableType.SMALL_INTEGER_2_TEXT_2;
	}
}