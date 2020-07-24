package io.github.gutyerrez.market.api.category;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import io.github.gutyerrez.core.spigot.misc.material.MaterialData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * @author SrGutyerrez
 */
public class MarketCategoryRegistry {

    private static final Map<String, MarketCategory> CATEGORIES = Maps.newHashMap();

    public static void registerCategory(MarketCategory... categories) {
        for (MarketCategory marketCategory : categories) {
            MarketCategoryRegistry.CATEGORIES.put(
                    marketCategory.getName(),
                    marketCategory
            );
        }
    }

    public static MarketCategory getCategory(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return null;
        }
        
        for (MarketCategory marketCategory : MarketCategoryRegistry.CATEGORIES.values()) {
            List<MaterialData> datas = marketCategory.getItems();

            for (MaterialData data : datas) {
                if (data.asBukkitItemStackCopy.get().isSimilar(itemStack)) {
                    return marketCategory;
                }
            }
        }

        return null;
    }

    public static ImmutableSet<MarketCategory> get() {
        return ImmutableSet.copyOf(MarketCategoryRegistry.CATEGORIES.values());
    }

}
