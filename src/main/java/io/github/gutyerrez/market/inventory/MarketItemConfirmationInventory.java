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

                    if (marketItem.getOwner().equals(player.getUniqueId())) {
                        // remover do banco de dados, caso ele não esteja
                        // cancelar e falar pro jogador que não deu pra comprar

                        InventoryUtils.give(player, marketItem.getItem());

                        player.sendMessage("§eVocê coletou seu item do mercado!");
                    } else {
                        if (MarketProvider.Hooks.ECONOMY.provide().getBalance(player.getName()) < marketItem.getPrice()) {
                            player.sendMessage(String.format("§cVocê precisa de %s coins para comprar este item.", marketItem.getPrice()));
                            return;
                        }

                        // remover do banco de dados, caso ele não esteja
                        // cancelar e falar pro jogador que não deu pra comprar

                        MarketProvider.Hooks.ECONOMY.provide().withdrawPlayer(player.getName(), marketItem.getPrice());
                        MarketProvider.Hooks.ECONOMY.provide().depositPlayer(marketItem.getOwnerName(), marketItem.getPrice());

                        InventoryUtils.give(player, marketItem.getItem());

                        player.sendMessage("§aVocê comprou um item no mercado!");
                    }
                },
                onDeny -> {
                    // nada
                },
                itemBuilder.make()
        );
    }
}
