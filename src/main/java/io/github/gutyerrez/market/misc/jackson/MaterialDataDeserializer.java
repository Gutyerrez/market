package io.github.gutyerrez.market.misc.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.gutyerrez.core.spigot.misc.material.MaterialData;

import java.io.IOException;

/**
 * @author SrGutyerrez
 */
public class MaterialDataDeserializer extends StdDeserializer<MaterialData> {

    public MaterialDataDeserializer() {
        super(MaterialData.class);
    }

    @Override
    public MaterialData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();

        Integer id = node.get("id").asInt(), data = node.get("data").asInt();

        return new MaterialData(
                id,
                data.shortValue()
        );
    }
}
