package org.remoteme.utils.messages.v1.core.messages.variables.values;

import org.remoteme.utils.exceptions.CannotParseVariableValue;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AVariableValue {
	/*public static <T extends AVariableValue> T get(VariableType type) {
		switch (type){
			case BOOLEAN: return (T) new BooleanVV();
			case INTEGER:return (T) new IntegerVV();
			case TEXT:return (T)  new TextVV();
			case SMALL_INTEGER_3:return (T) new SmallInteger3VV();
			case SMALL_INTEGER_2:return (T) new SmallInteger2VV();
			case INTEGER_BOOLEAN: return (T) new IntegerBooleanVV();
			case DOUBLE: return (T) new DoubleVV();
			case TEXT_2:return (T) new Text2VV();
			case SMALL_INTEGER_2_TEXT_2:return (T) new SmallInteger2Text2VV();
		}

		throw new RuntimeException("no such varibale value");
	}*/

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
			switch (type) {
				case BOOLEAN:
					return (T) new BooleanVV(rendered);
				case TEXT_2:
					return (T) new Text2VV(rendered);
				case INTEGER:
					return (T) new IntegerVV(rendered);
				case DOUBLE:
					return (T) new DoubleVV(rendered);
				case INTEGER_BOOLEAN:
					return (T) new IntegerBooleanVV(rendered);
				case SMALL_INTEGER_2:
					return (T) new SmallInteger2VV(rendered);
				case SMALL_INTEGER_3:
					return (T) new SmallInteger3VV(rendered);
				case TEXT:
					return (T) new TextVV(rendered);
				case SMALL_INTEGER_2_TEXT_2:
					return (T) new SmallInteger2Text2VV(rendered);
				default:
					throw new RuntimeException("no recongnized type of variable in persistance");
			}
		}catch (Exception ex){
			throw new CannotParseVariableValue(rendered,type);
		}
	}

	@SuppressWarnings("unchecked")
	public static  <T extends AVariableValue> T get(ByteBuffer bb,VariableType type){

		switch (type){

			case BOOLEAN:return (T) new BooleanVV(bb);
			case TEXT_2:return (T) new Text2VV(bb);
			case INTEGER:return (T) new IntegerVV(bb);
			case DOUBLE:return (T) new DoubleVV(bb);
			case INTEGER_BOOLEAN:return (T) new IntegerBooleanVV(bb);
			case SMALL_INTEGER_2:return (T) new SmallInteger2VV(bb);
			case SMALL_INTEGER_3:return (T) new SmallInteger3VV(bb);
			case TEXT:return (T) new TextVV(bb);
			case SMALL_INTEGER_2_TEXT_2:return (T) new SmallInteger2Text2VV(bb);
			default:throw new RuntimeException("no recongnized type of variable in persistance");
		}
	}

	public abstract void serializeData(ByteBuffer output);

	public abstract int getDataSize();

	public abstract VariableType getType() ;

}
