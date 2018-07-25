package it.sajdak.remoteme.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapToCoupleArrayDeserializer extends JsonDeserializer<Map<?,?>> {

	@Override
	public Map<?, ?> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		Map map = new LinkedHashMap();

		ObjectCodec oc = p.getCodec();
		JsonNode anode = oc.readTree(p);

		for (int i = 0; i < anode.size(); i++) {
			JsonNode entry = anode.get(i);

			JsonNode keyNode = entry.get(0);
			JsonNode valueNode = entry.get(1);

			/*map.put(keyNode.traverse(oc).readValueAs(this.keyAs),
					valueNode.traverse(oc).readValueAs(this.contentAs));*/

			throw new RuntimeException("Doenst work maciek check the reason is we dont have type ");
		}

		return map;
	}

	@Override
	public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {


		ObjectCodec oc = p.getCodec();
		JsonNode anode = oc.readTree(p);

		String type=anode.get(0).asText();
		try {
			Map map= (Map) Class.forName(type).getConstructor().newInstance();

			anode=anode.get(1);


			for (int i = 0; i < anode.size(); i++) {
				JsonNode entry = anode.get(i);



				JsonNode keyNode = entry.get(0);
				JsonNode valueNode = entry.get(1);

				map.put(keyNode.traverse(oc).readValueAs(Object.class),
						valueNode.traverse(oc).readValueAs(Object.class));

			}

			return map;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}