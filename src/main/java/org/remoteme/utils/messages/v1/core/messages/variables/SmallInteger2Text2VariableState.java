package org.remoteme.utils.messages.v1.core.messages.variables;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class SmallInteger2Text2VariableState extends AVariableState<SmallInteger2Text2VariableState.SmallInteger2Text2> {

	@EqualsAndHashCode
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SmallInteger2Text2 implements Serializable {
		int i1;
		int i2;
		String s1;
		String s2;
	}

	protected SmallInteger2Text2VariableState() {
	}

	@Override
	public String getDataString() {
		return getData().getI1()+", "+getData().getI2()+", "+getData().getS1()+", "+getData().getS2();
	}
	public SmallInteger2Text2VariableState(String name, int v1, int v2, String s1,String s2) {
		this(name, new SmallInteger2Text2(v1, v2, s1,s2));
	}

	public SmallInteger2Text2VariableState(String name, SmallInteger2Text2VariableState.SmallInteger2Text2 data) {
		super(name, data);
	}

	public SmallInteger2Text2VariableState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.putShort((short)getData().getI1());
		output.putShort((short)getData().getI2());
		output.put(ByteBufferUtils.writeString(getData().getS1()));
		output.put(ByteBufferUtils.writeString(getData().getS2()));
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		SmallInteger2Text2VariableState.SmallInteger2Text2 temp = new SmallInteger2Text2VariableState.SmallInteger2Text2();
		temp.setI1(output.getShort());
		temp.setI2(output.getShort());
		temp.setS1(ByteBufferUtils.readString(output));
		temp.setS2(ByteBufferUtils.readString(output));

		setData(temp);
	}

	@Override
	protected int getDataSize() {
		return 4+ ByteBufferUtils.writeString(getData().getS1()).length+ByteBufferUtils.writeString(getData().getS2()).length;
	}

	@Override
	protected VariableType getType() {
		return VariableType.SMALL_INTEGER_2_TEXT_2;
	}
}
