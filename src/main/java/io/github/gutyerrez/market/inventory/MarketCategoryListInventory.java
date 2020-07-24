package io.github.gutyerrez.market.inventory;

import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.ItemBuilder;
import io.github.gutyerrez.market.api.category.MarketCategoryRegistry;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class MarketCategoryListInventory extends CustomInventory {
    public MarketCategoryListInventory() {
        super(6, "Mercado");

        MarketCategoryRegistry.get().forEach(category -> {
            this.setItem(
                    category.getSlot()
                    ,
                    new ItemBuilder(
                            category.getIcon()
                    ).lore(
                            "§7Clique para atualizar os itens disponíveis atualmente.",
                            "",
                            String.format("§fItens disponíveis: §7%d", 0)
                    ).make(),
                    (event) -> {
                        Player player = (Player) event.getWhoClicked();

                        player.openInventory(
                                new MarketCategoryDetailInventory(player, category, this)
                        );
                    }
            );
        });
    }

}
