package org.remoteme.utils.messages.v1.core.messages.change;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import org.remoteme.utils.general.ByteBufferUtils;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Getter
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = BooleanObserverState.class, name = "BOOLEAN"),
		@JsonSubTypes.Type(value = IntegerObserverState.class, name = "INTEGER"),
		@JsonSubTypes.Type(value = TextObserverState.class, name = "TEXT"),
		@JsonSubTypes.Type(value = SmallInteger3ObserverState.class, name = "SMALL_INTEGER_3"),
		@JsonSubTypes.Type(value = SmallInteger2ObserverState.class, name = "SMALL_INTEGER_2"),
		@JsonSubTypes.Type(value = IntegerBooleanObserverState.class, name = "INTEGER_BOOLEAN"),
		@JsonSubTypes.Type(value = DoubleObserverState.class, name = "DOUBLE"),

})
public abstract class AObserverState<T> implements Serializable {


	String name;
	T data;

	protected AObserverState(){

	}

	public AObserverState(String name, T data){
		this.name=name;
		this.data=data;

	}
	public static AObserverState<?> get(ByteBuffer output){
		VariableOberverType type = VariableOberverType.getById(Short.toUnsignedInt(output.getShort()));
		AObserverState<?> ret;
		switch (type){
			case BOOLEAN:
				ret  = new BooleanObserverState(output);
				break;
			case INTEGER:
				ret  = new IntegerObserverState(output);
				break;
			case TEXT:
				ret  = new TextObserverState(output);
				break;
			case SMALL_INTEGER_3:
				ret  = new SmallInteger3ObserverState(output);
				break;
			case SMALL_INTEGER_2:
				ret  = new SmallInteger2ObserverState(output);
				break;
			case INTEGER_BOOLEAN:
				ret  = new IntegerBooleanObserverState(output);
				break;
			case DOUBLE:
				ret  = new DoubleObserverState(output);
				break;

			default:
				throw new RuntimeException("no state for type "+type);
		}


		return ret;
	}


	protected abstract void serializeData(ByteBuffer output);


	protected abstract void deSerializeData(ByteBuffer output);





	public int getSize() {
		return 2+getName().length()+1+getDataSize();
	}

	protected abstract int getDataSize();
	protected abstract VariableOberverType getType();

	public AObserverState(ByteBuffer output){
		name =ByteBufferUtils.readString(output);
		deSerializeData(output);
	}

	protected  void serialize(ByteBuffer output){

		output.putShort((short) getType().getId());
		output.put(getName().getBytes(StandardCharsets.UTF_8));
		output.put((byte)0);
		serializeData(output);


	}
	public ObserverIdentifier getIdentifier(){
		return new ObserverIdentifier(name, getType());
	}

	protected void setData(T data) {
		this.data = data;
	}
	protected T getData() {
		return data;
	}

	public abstract String getDataString();

}
