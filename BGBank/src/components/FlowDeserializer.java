package components;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class FlowDeserializer extends StdDeserializer<Flow> {

	public FlowDeserializer() {
		this(null);
	}

	public FlowDeserializer(final Class<?> vc) {
		super(vc);
	}

	@Override
	public Flow deserialize(JsonParser parser, DeserializationContext arg1) throws IOException, JacksonException {
		final JsonNode node = parser.getCodec().readTree(parser);
		final ObjectMapper mapper = (ObjectMapper) parser.getCodec();
		if (node.has("issuing_target_account")) {
			return mapper.treeToValue(node, Transfert.class);
		} else {
			return mapper.treeToValue(node, Credit.class);
		}
	}
}
