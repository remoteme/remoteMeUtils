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

public class Text2VariableState extends AVariableState<Text2VariableState.Text2> {



	@EqualsAndHashCode
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Text2 implements Serializable {
		String s1;
		String s2;
	}
	protected Text2VariableState() {
	}

	@Override
	public String getDataString() {
		return  getData().getS1()+", "+getData().getS2();
	}

	public Text2VariableState(String name, String data,String data2) {
		this(name, new Text2(data, data2));
	}

	public  Text2VariableState(String name, Text2VariableState.Text2 value) {
		super(name, value);
	}

	public Text2VariableState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.put(ByteBufferUtils.writeString(getData().getS1()));
		output.put(ByteBufferUtils.writeString(getData().getS2()));
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		setData(new Text2(ByteBufferUtils.readString(output),ByteBufferUtils.readString(output)));

	}

	@Override
	protected int getDataSize() {
		return ByteBufferUtils.writeString(getData().getS1()).length+ByteBufferUtils.writeString(getData().getS2()).length;
	}

	@Override
	protected VariableType getType() {
		return VariableType.TEXT_2;
	}
}
