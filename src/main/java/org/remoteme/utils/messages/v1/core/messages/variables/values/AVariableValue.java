package org.remoteme.utils.messages.v1.core.messages.variables.values;

import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.exceptions.CannotParseVariableValue;
import org.remoteme.utils.jackson.JacksonHelper;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter

public abstract class AVariableValue {


	/**/

	@SuppressWarnings("unchecked")
	public static   <T extends AVariableValue> Class<T>  getValueClass( VariableType type)   {
		switch (type) {
			case BOOLEAN:
				return (Class<T>) BooleanVV.class;
			case TEXT_2:
				return (Class<T>) Text2VV.class;
			case INTEGER:
				return (Class<T>) IntegerVV.class;
			case DOUBLE:
				return (Class<T>) DoubleVV.class;
			case INTEGER_BOOLEAN:
				return (Class<T>) IntegerBooleanVV.class;
			case SMALL_INTEGER_2:
				return (Class<T>) SmallInteger2VV.class;
			case SMALL_INTEGER_3:
				return (Class<T>) SmallInteger3VV.class;
			case TEXT:
				return (Class<T>) TextVV.class;
			case SMALL_INTEGER_2_TEXT_2:
				return (Class<T>) SmallInteger2Text2VV.class;
			default:
				throw new RuntimeException("no recongnized type of variable in persistance");

		}
	}

	public static List<? extends AVariableValue> get(VariableType type, List<String> rendered) throws CannotParseVariableValue {
		List<? extends AVariableValue> ret = new ArrayList<>(rendered.size());
		for (String render : rendered) {
			ret.add(AVariableValue.get( render, type));
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static   <T extends AVariableValue> T  get(String rendered, VariableType type) throws CannotParseVariableValue {
		try {
			return (T) getValueClass(type).getConstructor(String.class).newInstance(rendered);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new CannotParseVariableValue(rendered,type );
		}
	}

	@SuppressWarnings("unchecked")
	public static  <T extends AVariableValue> T get(ByteBuffer bb,VariableType type){

		try {
			return (T) getValueClass(type).getConstructor(ByteBuffer.class).newInstance(bb);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}


	public static <T extends AVariableValue> T deserialize(String serialized, Class<T> clazz){
		if (serialized==null){
			return null;
		}
		try {
			return JacksonHelper.deserialize(serialized, clazz);
		}catch (Exception ex){//back compatibility
			if (clazz ==IntegerVV.class){
				return (T) new IntegerVV(JacksonHelper.deserialize(serialized, Integer.class));
			}else if (clazz ==DoubleVV.class){
				return (T) new DoubleVV(JacksonHelper.deserialize(serialized, Double.class));
			}else if (clazz == TextVV.class){
				return (T) new TextVV(JacksonHelper.deserialize(serialized, String.class));
			}else if (clazz ==BooleanVV.class){
				return (T) new BooleanVV(JacksonHelper.deserialize(serialized, Boolean.class));
			}else{
				throw ex;
			}
		}
	}


	public static <T> String serialize(T value){
		if (value==null){
			return null;
		}else{
			return JacksonHelper.serialize(value);
		}
	}

	public static AVariableValue getRandom(VariableType type) {
		switch (type){

			case BOOLEAN: return new BooleanVV(Math.random()>0.5);
			case INTEGER:return new IntegerVV(randomInt());
			case TEXT:return new TextVV(randomString());
			case SMALL_INTEGER_3:return new SmallInteger3VV(randomInt(),randomInt(),randomInt());
			case SMALL_INTEGER_2:return new SmallInteger2VV(randomInt(),randomInt());
			case INTEGER_BOOLEAN:return new IntegerBooleanVV(randomInt(),Math.random()>0.5);
			case DOUBLE:return new DoubleVV((Math.random()*1000));
			case TEXT_2:return new Text2VV(randomString(),randomString());
			case SMALL_INTEGER_2_TEXT_2:return new SmallInteger2Text2VV(randomInt(),randomInt(), randomString(),randomString());
			default: throw new RuntimeException();
		}

	}

	private static int randomInt() {
		return (int)(Math.random()*100);

	}

	private static String randomString() {
		return UUID.randomUUID().toString();

	}


	public abstract void serializeData(ByteBuffer output);

	public abstract int getDataSize();

	public abstract VariableType getType() ;

	public abstract double toDouble();

}
