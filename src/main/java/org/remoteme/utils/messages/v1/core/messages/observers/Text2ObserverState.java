package org.remoteme.utils.messages.v1.core.messages.observers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.enums.VariableOberverType;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class Text2ObserverState extends AObserverState<Text2ObserverState.Text2> {



	@EqualsAndHashCode
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Text2 implements Serializable {
		String t1;
		String t2;
	}
	protected Text2ObserverState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public Text2ObserverState(String name, String data,String data2) {
		this(name, new Text2(data, data2));
	}

	public  Text2ObserverState(String name, Text2ObserverState.Text2 value) {
		super(name, value);
	}

	public Text2ObserverState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.put(ByteBufferUtils.writeString(getData().getT1()));
		output.put(ByteBufferUtils.writeString(getData().getT2()));
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		setData(new Text2(ByteBufferUtils.readString(output),ByteBufferUtils.readString(output)));

	}

	@Override
	protected int getDataSize() {
		return ByteBufferUtils.writeString(getData().getT1()).length+ByteBufferUtils.writeString(getData().getT2()).length;
	}

	@Override
	protected VariableOberverType getType() {
		return VariableOberverType.TEXT_2;
	}
}
