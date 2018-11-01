package org.remoteme.utils.messages.v1.core.messages.variables;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.remoteme.utils.general.ByteBufferUtils;
import org.remoteme.utils.jackson.JacksonHelper;
import org.remoteme.utils.messages.v1.core.messages.variables.values.AVariableValue;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeIdResolver(AVariableState.OperationTypeIdResolver.class)
public abstract class AVariableState<T extends AVariableValue> implements Serializable {



	public static final class OperationTypeIdResolver extends TypeIdResolverBase {


		@Override
		public String idFromValue(Object value) {
			return idFromValueAndType(value, value.getClass());
		}

		@Override
		public String idFromValueAndType(Object value, Class<?> suggestedType) {
			return ((AVariableState)value).getType().toString();
		}

		@Override
		public JavaType typeFromId(DatabindContext context, String id)   {
			return  SimpleType.constructUnsafe (getStateClass(VariableType.valueOf(id)));
		}

		@Override
		public JsonTypeInfo.Id getMechanism() {
			return JsonTypeInfo.Id.CUSTOM;
		}
	}


	String name;
	T data;


	public static String serialize(AVariableState<?> state) {
		return JacksonHelper.serialize(state);
	}
	public static AVariableState<?> deserialize(String s) {
		return JacksonHelper.deserialize(s,AVariableState.class);
	}
	public static Optional<AVariableState<?>> get(String variableName, Optional<AVariableValue> value) {
		if (value.isPresent()){
			return Optional.of(get(variableName, value.get()));
		}else{
			return Optional.empty();
		}
	}
	public static <T extends  AVariableState<?>> Class<T> getStateClass(VariableType type) {
		switch (type) {

			case BOOLEAN:
				return (Class<T>) BooleanVariableState.class;
			case INTEGER:
				return  (Class<T>)IntegerVariableState.class;
			case TEXT:
				return  (Class<T>)TextVariableState.class;
			case SMALL_INTEGER_3:
				return  (Class<T>)SmallInteger3VariableState.class;
			case SMALL_INTEGER_2:
				return  (Class<T>)SmallInteger2VariableState.class;
			case INTEGER_BOOLEAN:
				return  (Class<T>)IntegerBooleanVariableState.class;
			case DOUBLE:
				return  (Class<T>)DoubleVariableState.class;
			case TEXT_2:
				return  (Class<T>)Text2VariableState.class;
			case SMALL_INTEGER_2_TEXT_2:
				return  (Class<T>) SmallInteger2Text2VariableState.class;
		}

		throw new RuntimeException("cannot find peroper type");
	}

	public static AVariableState get(String variableName, AVariableValue value) {
		try {
			return getStateClass(value.getType()).getConstructor(String.class,value.getClass()).newInstance(variableName,value);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	public static AVariableState<?> get(ByteBuffer output) {
		VariableType type = VariableType.getById(Short.toUnsignedInt(output.getShort()));
		try {
			return getStateClass(type).getConstructor(ByteBuffer.class).newInstance(output);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}

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
		if (data.getType()!=getType() ){
			throw new RuntimeException("Wrnong value tries to set");
		}
		this.data = data;

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


	protected abstract VariableType getType();

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
		if (data.getType()!=getType() ){
			throw new RuntimeException("Wrnong value tries to set");
		}
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public final String getDataString() {
		return String.valueOf(getData());
	}

}
