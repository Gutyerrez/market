package io.github.gutyerrez.market.inventory;

import io.github.gutyerrez.core.spigot.inventory.ConfirmInventory;
import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.inventory.PaginateInventory;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import io.github.gutyerrez.market.MarketProvider;
import io.github.gutyerrez.market.api.category.MarketCategory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class MarketCategoryDetailInventory extends PaginateInventory {

    public MarketCategoryDetailInventory(Player player, MarketCategory marketCategory, CustomInventory backInventory) {
        super(marketCategory.getName());

        MarketProvider.Cache.Local.MARKET_ITEM.provide().get(marketCategory)
                .forEach(marketItem -> {
                    ItemBuilder itemBuilder = new ItemBuilder(
                            marketItem.getItem(),
                            false
                    ).lore(
                            "",
                            String.format("§7Vendedor: %s", marketItem.getFancyOwnerName())
                    );

                    if (marketItem.getOwner().equals(player.getUniqueId())) {
                        itemBuilder.lore(
                                "",
                                "§aClique para coletar."
                        );
                    } else {
                        if (MarketProvider.Hooks.ECONOMY.provide().getBalance(player.getName()) < marketItem.getPrice()) {
                            itemBuilder.lore("§cVocê não possui coins suficientes para comprar es item");
                        }
                    }

                    this.addItem(
                            itemBuilder.make(),
                            (event) -> {
                                ConfirmInventory confirmInventory = new MarketItemConfirmationInventory(
                                        marketItem,
                                        itemBuilder
                                );

                                player.openInventory(confirmInventory.make());
                            }
                    );
                });

        if (backInventory != null) {
            this.backItem(backInventory);
        }
    }

    public MarketCategoryDetailInventory(Player player, MarketCategory marketCategory) {
        this(player, marketCategory, null);
    }

    protected ChatColor getPriceColor(Player player, Double price) {
        if (MarketProvider.Hooks.ECONOMY.provide().getBalance(player.getName()) < price) {
            return ChatColor.RED;
        }

        return ChatColor.YELLOW;
    }

}
