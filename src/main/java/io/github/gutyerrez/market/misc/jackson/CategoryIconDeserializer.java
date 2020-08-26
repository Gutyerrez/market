package io.github.gutyerrez.market.misc.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * @author SrGutyerrez
 */
public class CategoryIconDeserializer extends StdDeserializer<ItemStack> {

    public CategoryIconDeserializer() {
        super(ItemStack.class);
    }

    @Override
    public ItemStack deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();

        Integer id = node.get("id").asInt(), data = node.get("data").asInt();
        String displayName = node.get("display_name").asText();
        Boolean glow = node.get("glow").asBoolean();

        JsonNode _node = node.get("description");

        String[] description = new String[_node.size()];

        IntStream.range(0, _node.size())
                .forEach(index -> {
                    String line = _node.get(index).asText();

                    description[index] = line;
                });

        ItemBuilder builder = new ItemBuilder(Material.getMaterial(id))
                .name(displayName)
                .data(data)
                .lore(description)
                .flags(
                        ItemFlag.HIDE_ATTRIBUTES,
                        ItemFlag.HIDE_ENCHANTS
                );

        if (glow) {
            builder.enchantment(Enchantment.DURABILITY);
        }

        return builder.make();
    }
}
