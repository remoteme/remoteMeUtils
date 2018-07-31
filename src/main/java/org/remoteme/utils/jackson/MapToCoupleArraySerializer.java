package org.remoteme.utils.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.util.Map;

public class MapToCoupleArraySerializer extends JsonSerializer<Map> {

	@Override
	public void serialize(Map value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeStartArray();
		for (Object entry_ : value.entrySet()) {
			Map.Entry entry = (Map.Entry)entry_;
			gen.writeStartArray();
			gen.writeObject(entry.getKey());
			gen.writeObject(entry.getValue());
			gen.writeEndArray();
		}
		gen.writeEndArray();
	}


	public void serializeWithType(Map value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
		typeSer.writeTypePrefixForArray(value, gen);
		gen.setCurrentValue(value);

		for (Object entry_ : value.entrySet()) {
			Map.Entry entry = (Map.Entry)entry_;
			gen.writeStartArray();
			gen.writeObject(entry.getKey());
			gen.writeObject(entry.getValue());
			gen.writeEndArray();
		}

		typeSer.writeTypeSuffixForArray(value, gen);
	}
}