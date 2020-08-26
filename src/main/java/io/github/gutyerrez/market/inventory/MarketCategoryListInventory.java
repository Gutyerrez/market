package io.github.gutyerrez.market.inventory;

import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import io.github.gutyerrez.market.api.category.MarketCategoryRegistry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author SrGutyerrez
 */
public class MarketCategoryListInventory extends CustomInventory {

    public static ItemStack PERSONAL_MARKET;

    public static ItemStack MY_ITEMS;

    public MarketCategoryListInventory() {
        super(6, "Mercado");

        MarketCategoryRegistry.get().forEach(category -> {
            this.setItem(
                    category.getSlot(),
                    new ItemBuilder(
                            category.getIcon()
                    ).lore(
                            "§7Clique para atualizar os itens disponíveis atualmente.",
                            "",
                            String.format(
                                    "§fItens disponíveis: §7%d",
                                    category.getAvailableItems()
                            )
                    ).amount(category.getAvailableItems())
                    .make(),
                    (event) -> {
                        Player player = (Player) event.getWhoClicked();

                        if (category.getAvailableItems() <= 0) {
                            return;
                        }

                        player.openInventory(
                                new MarketCategoryDetailInventory(player, category, this)
                        );
                    }
            );
        });
    }

}
