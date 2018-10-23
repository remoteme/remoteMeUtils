package org.remoteme.utils.messages.v1.core.messages.variables;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.messages.v1.core.messages.variables.values.AVariableValue;
import org.remoteme.utils.messages.v1.core.messages.variables.values.BooleanVV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.DoubleVV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.IntegerBooleanVV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.IntegerVV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.SmallInteger2Text2VV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.SmallInteger2VV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.SmallInteger3VV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.Text2VV;
import org.remoteme.utils.messages.v1.core.messages.variables.values.TextVV;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = BooleanVariableState.class, name = "BOOLEAN"),
		@JsonSubTypes.Type(value = IntegerVariableState.class, name = "INTEGER"),
		@JsonSubTypes.Type(value = TextVariableState.class, name = "TEXT"),
		@JsonSubTypes.Type(value = SmallInteger3VariableState.class, name = "SMALL_INTEGER_3"),
		@JsonSubTypes.Type(value = SmallInteger2VariableState.class, name = "SMALL_INTEGER_2"),
		@JsonSubTypes.Type(value = IntegerBooleanVariableState.class, name = "INTEGER_BOOLEAN"),
		@JsonSubTypes.Type(value = DoubleVariableState.class, name = "DOUBLE"),
		@JsonSubTypes.Type(value = Text2VariableState.class, name = "TEXT_2"),
		@JsonSubTypes.Type(value = SmallInteger2Text2VariableState.class, name = "SMALL_INTEGER_2_TEXT_2"),

})
public abstract class AVariableState<T extends AVariableValue> implements Serializable {

	String name;
	T data;


	public static AVariableState get(String variableName, AVariableValue value) {
		switch (value.getType()) {

			case BOOLEAN:
				return new BooleanVariableState(variableName, (BooleanVV) value);
			case INTEGER:
				return new IntegerVariableState(variableName, (IntegerVV) value);
			case TEXT:
				return new TextVariableState(variableName, (TextVV) value);
			case SMALL_INTEGER_3:
				return new SmallInteger3VariableState(variableName, (SmallInteger3VV) value);
			case SMALL_INTEGER_2:
				return new SmallInteger2VariableState(variableName, (SmallInteger2VV) value);
			case INTEGER_BOOLEAN:
				return new IntegerBooleanVariableState(variableName, (IntegerBooleanVV) value);
			case DOUBLE:
				return new DoubleVariableState(variableName, (DoubleVV) value);
			case TEXT_2:
				return new Text2VariableState(variableName, (Text2VV) value);
			case SMALL_INTEGER_2_TEXT_2:
				return new SmallInteger2Text2VariableState(variableName, (SmallInteger2Text2VV) value);
		}

		throw new RuntimeException("cannot find peroper type");
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof AVariableState<?>)) {
			return false;
		}
		AVariableState<?> that = (AVariableState<?>) o;

		if (getData() == null || getData().getClass() != that.getData().getClass()) return false;

		return Objects.equals(getName(), that.getName()) && Objects.equals(getData(), that.getData());
	}


	@Override
	public int hashCode() {
		return Objects.hash(getName(), getData());
	}

	protected AVariableState() {

	}

	private String getName() {
		return name;
	}

	public AVariableState(String name, T data) {
		this.name = name;
		this.data = data;

	}

	public static AVariableState<?> get(ByteBuffer output) {
		VariableType type = VariableType.getById(Short.toUnsignedInt(output.getShort()));
		AVariableState<?> ret;
		switch (type) {
			case BOOLEAN:
				ret = new BooleanVariableState(output);
				break;
			case INTEGER:
				ret = new IntegerVariableState(output);
				break;
			case TEXT:
				ret = new TextVariableState(output);
				break;
			case SMALL_INTEGER_3:
				ret = new SmallInteger3VariableState(output);
				break;
			case SMALL_INTEGER_2:
				ret = new SmallInteger2VariableState(output);
				break;
			case INTEGER_BOOLEAN:
				ret = new IntegerBooleanVariableState(output);
				break;
			case DOUBLE:
				ret = new DoubleVariableState(output);
				break;
			case TEXT_2:
				ret = new Text2VariableState(output);
				break;
			case SMALL_INTEGER_2_TEXT_2:
				ret = new SmallInteger2Text2VariableState(output);
				break;
			default:
				throw new RuntimeException("no state for type " + type);
		}


		return ret;
	}


	protected final void serializeData(ByteBuffer output) {
		getData().serializeData(output);
	}


	protected final void deSerializeData(ByteBuffer output) {
		setData(AVariableValue.get(output, getType()));


	}

	protected final int getDataSize() {
		return getData().getDataSize();
	}

	public int getSize() {
		return 2 + getName().length() + 1 + getDataSize();
	}


	protected final VariableType getType() {
		return getData().getType();
	}

	public AVariableState(ByteBuffer output) {
		name = ByteBufferUtils.readString(output);
		deSerializeData(output);
	}

	public void serialize(ByteBuffer output) {

		output.putShort((short) getType().getId());
		output.put(getName().getBytes(StandardCharsets.UTF_8));
		output.put((byte) 0);
		serializeData(output);


	}

	public VariableIdentifier getIdentifier() {
		return new VariableIdentifier(name, getType());
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public final String getDataString() {
		return String.valueOf(getData());
	}

}
