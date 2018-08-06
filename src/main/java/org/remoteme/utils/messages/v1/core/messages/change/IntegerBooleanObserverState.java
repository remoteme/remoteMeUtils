package org.remoteme.utils.messages.v1.core.messages.change;

import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.general.ByteBufferUtils;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IntegerBooleanObserverState extends AObserverState<IntegerBooleanObserverState.IntegerBoolean> {


	@Getter
	@Setter
	public static class IntegerBoolean implements Serializable {
		int i;
		boolean b;
	}

	protected IntegerBooleanObserverState() {
	}

	@Override
	public String getDataString() {
		return String.valueOf(getData());
	}

	public IntegerBooleanObserverState(String name, IntegerBoolean data) {
		super(name, data);
	}

	public IntegerBooleanObserverState(ByteBuffer output) {
		super(output);
	}

	@Override
	protected void serializeData(ByteBuffer output) {
		output.putInt(getData().getI());
		output.put((byte)(getData().isB()?1:0));
	}

	@Override
	protected void deSerializeData(ByteBuffer output) {
		IntegerBoolean ib = new IntegerBoolean();
		ib.setI(output.getInt());
		ib.setB(output.get()==1);
		setData(ib);
	}

	@Override
	protected int getDataSize() {
		return 4+1;
	}

	@Override
	protected VariableOberverType getType() {
		return VariableOberverType.INTEGER_BOOLEAN;
	}
}
