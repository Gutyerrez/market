package io.github.gutyerrez.market.api.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.gutyerrez.core.spigot.misc.material.MaterialData;
import lombok.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author SrGutyerrez
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "name" })
public class MarketCategory {

    @JsonProperty("id")
    private String name;

    @JsonIgnore
    private ItemStack icon;

    @JsonProperty("slot")
    private Integer slot;

    @JsonProperty("items")
    private List<MaterialData> items;

}
