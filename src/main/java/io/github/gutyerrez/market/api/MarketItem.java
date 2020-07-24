package io.github.gutyerrez.market.api;

import io.github.gutyerrez.market.MarketProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.PermissionUser;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author SrGutyerrez
 */
@Getter
@RequiredArgsConstructor
public class MarketItem {

    private final Integer id;
    private final UUID owner;
    private final UUID target;
    private final ItemStack item;
    private final Double price;
    private final Date createdAt;

    public String getOwnerName() {
        return Bukkit.getOfflinePlayer(this.owner).getName();
    }

    public String getFancyOwnerName() {
        if (MarketProvider.Hooks.PERMISSIONS_EX.isActive()) {
            PermissionUser permissionUser =  MarketProvider.Hooks.PERMISSIONS_EX.provide().getUser(this.owner);

            return String.format("%s %s", permissionUser.getPrefix(), permissionUser.getName());
        }

        return this.getOwnerName();
    }

    public String getOwnerUUID() {
        return this.owner.toString();
    }

    public String getTargetUUID() {
        return this.target == null ? null : this.target.toString();
    }

    public Boolean isExpired() {
        return this.createdAt.getTime() < (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1));
    }

}
