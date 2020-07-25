package io.github.gutyerrez.market.command.subcommands;

import com.google.common.primitives.Doubles;
import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.spigot.commands.CustomCommand;
import io.github.gutyerrez.market.MarketProvider;
import io.github.gutyerrez.market.api.MarketItem;
import io.github.gutyerrez.market.api.category.MarketCategory;
import io.github.gutyerrez.market.api.category.MarketCategoryRegistry;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author SrGutyerrez
 */
public class MarketSellCommand extends CustomCommand {

    public MarketSellCommand() {
        super("vender");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Double price = Doubles.tryParse(args[0]);

        if (price == null || price.isNaN() || price <= 0) {
            sender.sendMessage("§cVocê informou um valor inválido.");
            return;
        }

        Player player = (Player) sender;

        ItemStack inHand = player.getItemInHand();

        switch (args.length) {
            case 1: {
                MarketCategory category = MarketCategoryRegistry.getCategory(inHand);

                if (category == null) {
                    sender.sendMessage("§cEste item não se encaixa em nenhuma categoria existente.");
                    return;
                }

                // adicionar ao banco de dados

                MarketItem marketItem = new MarketItem(
                        1,
                        player.getUniqueId(),
                        null,
                        inHand,
                        price,
                        new Date()
                );

                // mensagem de aguarde para anunciar novamente
                //
                // "§cVocê precisa esperar %s para anunciar novamente."

                String cooldownName = "MARKET_ANNOUNCE";

                if (CoreProvider.Cache.Local.COOLDOWNS.provide().inCooldown(player.getUniqueId(), cooldownName)) {
                    player.sendMessage(String.format("§cVocê precisa esperar %s para anunciar novamente.", 0));
                }

                CoreProvider.Cache.Local.COOLDOWNS.provide().startCooldown(
                        player.getUniqueId(),
                        cooldownName,
                        TimeUnit.SECONDS.toMillis(15)
                );

                MarketProvider.Cache.Local.MARKET_ITEM.provide().add(marketItem);

                player.sendMessage("§aItem colocado a venda!");

                // fazer o anúncio do item para todos no servidor
                return;
            }
            case 2: {
                // mercado pessoal
                return;
            }
            default: {
                return;
            }
        }
    }
}
