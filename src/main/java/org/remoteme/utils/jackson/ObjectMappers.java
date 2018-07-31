package org.remoteme.utils.jackson;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ObjectMappers {
	public static ObjectMapper getMapper() {
		return new ObjectMapper();
	}

	public static ObjectMapper getMapperWithVisibilitySet() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("module");
		mapper.registerModule(module);
		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
		                           .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
		                           .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
		                           .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		return mapper;
	}
}
