package io.github.gutyerrez.market.inventory;

import io.github.gutyerrez.core.spigot.inventory.ConfirmInventory;
import io.github.gutyerrez.core.spigot.misc.utils.InventoryUtils;
import io.github.gutyerrez.core.spigot.misc.utils.ItemBuilder;
import io.github.gutyerrez.market.MarketProvider;
import io.github.gutyerrez.market.api.MarketItem;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class MarketItemConfirmationInventory extends ConfirmInventory {
    public MarketItemConfirmationInventory(MarketItem marketItem, ItemBuilder itemBuilder) {
        super(
                onAccept -> {
                    Player player = (Player) onAccept.getWhoClicked();

                    if (!InventoryUtils.fits(player.getInventory(), marketItem.getItem())) {
                        player.sendMessage("§cVocê não possui espaço suficiente no inventário.");
                        return;
                    }

                    // remover do banco de dados, caso ele não esteja
                    // cancelar e falar pro jogador que não deu pra comprar

                    if (marketItem.getOwner().equals(player.getUniqueId())) {
                        MarketProvider.Cache.Local.MARKET_ITEM.provide().remove(marketItem.getId());

                        InventoryUtils.give(player, marketItem.getItem());
                    } else {
                    }
                },
                onDeny -> {
                    // nada
                },
                itemBuilder.make()
        );
    }
}
