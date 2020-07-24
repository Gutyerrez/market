package io.github.gutyerrez.market.command;

import io.github.gutyerrez.core.shared.commands.CommandRestriction;
import io.github.gutyerrez.core.spigot.commands.CustomCommand;
import io.github.gutyerrez.market.inventory.MarketCategoryListInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class MarketCommand extends CustomCommand {

    public MarketCommand() {
        super("mercado", CommandRestriction.IN_GAME);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        player.openInventory(
                new MarketCategoryListInventory()
        );
    }
}
