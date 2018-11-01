package org.remoteme.utils.messages.v1.core.messages.variables.values;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.SimpleType;
import lombok.Getter;
import lombok.Setter;
import org.remoteme.utils.exceptions.CannotParseVariableValue;
import org.remoteme.utils.jackson.JacksonHelper;
import org.remoteme.utils.messages.v1.core.messages.arLite.CreateVariablesMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.NotifyAboutVariablesMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.PingMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.VariableRemoveMessage;
import org.remoteme.utils.messages.v1.core.messages.arLite.VariableRenameMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.AddDataMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.LogMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.RegisterDeviceMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.RegisterLeafDeviceMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncResponseMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SyncUserMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.SystemMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.UserMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableChangePropagateMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.VariableObserveMessage;
import org.remoteme.utils.messages.v1.core.messages.remoteMe.WebRRCConnectionStatusChangeMessage;
import org.remoteme.utils.messages.v1.core.messages.variables.AVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.BooleanVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.DoubleVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.IntegerBooleanVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.IntegerVariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.SmallInteger2Text2VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.SmallInteger2VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.SmallInteger3VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.Text2VariableState;
import org.remoteme.utils.messages.v1.core.messages.variables.TextVariableState;
import org.remoteme.utils.messages.v1.enums.variables.VariableType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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






	public abstract void serializeData(ByteBuffer output);

	public abstract int getDataSize();

	public abstract VariableType getType() ;

}
