package org.remoteme.utils.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;


import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Map;

public class JacksonHelper {

	public static String serialize(Object object, boolean prettyPrint) {
		ObjectMapper mapper = getMapper(prettyPrint);

		try {
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, object);
			return sw.toString();
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static String serialize(Object object) {
		return serialize(object, true);

	}

	public static <T> T deserialize(String value, TypeReference<T> clazz) {
		if (value == null) {
			return null;
		}

		try {
			T ret = getMapper(false).readValue(value, clazz);
			callTriggers(ret);

			return ret;
		} catch (JsonParseException e) {

			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}


	private static void callTriggers(Object ret) {
		for (Method method : ret.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(AfterJacksonDeserialization.class)) {
				try {
					method.invoke(ret);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}

	}

	public static <T> T deserialize(String value, Class<T> clazz) {
		if (value == null) {
			return null;
		}

		try {
			T ret = getMapper(false).readValue(value, clazz);
			callTriggers(ret);

			return ret;
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}


	public static ObjectMapper getMapper(boolean prettyPrint) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("module");
		mapper.registerModule(module);
		mapper.registerModule(new Jdk8Module());
		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
		if (prettyPrint) {
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		}
		//mapper.registerModule(new JodaModule());
		return mapper;
	}




	private static ObjectMapper objectMapperWithType;
	protected  static ObjectMapper getObjectMapperWithType() {

		if (objectMapperWithType == null) {
			objectMapperWithType = new ObjectMapper();
			objectMapperWithType.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapperWithType.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

			objectMapperWithType.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);



			SimpleModule module = new SimpleModule("module");
			module.addSerializer(Map.class, new MapToCoupleArraySerializer());
			module.addDeserializer(Map.class,new MapToCoupleArrayDeserializer());

			objectMapperWithType.registerModule(module);
			objectMapperWithType.setVisibility(objectMapperWithType.getSerializationConfig().getDefaultVisibilityChecker()
					.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
					.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withCreatorVisibility(JsonAutoDetect.Visibility.ANY));

			//objectMapperWithType.registerModule(new JodaModule());


			// objectMapperWithType.configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, true);
			// objectMapperWithType.configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, true);
			// objectMapperWithType.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		}

		return objectMapperWithType;
	}

	public  static <T> T deserializeWithType(String value, Class<T> clazz) {
		if (value == null) {
			return null;
		}

		try {
			T ret = getObjectMapperWithType().readValue(value, clazz);

			return ret;
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}
	public static String serializeWithType(Object object ) {
		ObjectMapper mapper = getObjectMapperWithType();

		try {
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, object);
			return sw.toString();
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}



	public static boolean compareTwoJson(String json1,String json2) throws IOException {
		JsonNodeFactory intAsDouble= new JsonNodeFactory(true){

			public ValueNode get(Number value){
				return (ValueNode)(value == null?this.nullNode():DoubleNode.valueOf(value.doubleValue()));
			}

			public NumericNode getNoNull(Number value){
				return DoubleNode.valueOf(value.doubleValue());
			}

			public NumericNode numberNode(int v) {
				return getNoNull(v);
			}

			public ValueNode numberNode(Integer value) {
				return get(value);
			}

			public NumericNode numberNode(long v) {
				return getNoNull(v);
			}

			public ValueNode numberNode(Long value) {
				return get(value);
			}

			public NumericNode numberNode(BigInteger v) {
				return BigIntegerNode.valueOf(v);//wount work
			}

			public NumericNode numberNode(float v) {
				return getNoNull(v);
			}

			public ValueNode numberNode(Float value) {
				return get(value);
			}

			public NumericNode numberNode(double v) {
				return getNoNull(v);
			}

			public ValueNode numberNode(Double value) {
				return get(value);
			}

		/*	public NumericNode numberNode(BigDecimal v) {//also will be missing
				return get(v);
			}*/



		};
		ObjectMapper mapper = new ObjectMapper(){
			{
				_deserializationConfig=_deserializationConfig.with(intAsDouble);
			}

			@Override
			public JsonNodeFactory getNodeFactory() {
				return intAsDouble;
			}

		};


		JsonNode actualObj = mapper.readTree(json1);
		JsonNode expected = mapper.readTree(json2);



		return actualObj.equals(expected);
	}

/*	public static boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			// edited, to include @Arthur's comment
			// e.g. in case JSONArray is valid as well...
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}*/
}