package io.github.gutyerrez.market.misc.hooks;

import io.github.gutyerrez.core.shared.group.Group;
import io.github.gutyerrez.core.shared.group.GroupRegistry;
import io.github.gutyerrez.core.shared.misc.hooks.Hook;
import org.bukkit.Bukkit;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * @author SrGutyerrez
 */
public class PermissionsExHook<T extends PermissionManager> extends Hook<T> {

    @Override
    public T prepare() {
        if (Bukkit.getPluginManager().getPlugin("PermissionsEx") == null) {
            return null;
        }

        T permissionManager = (T) PermissionsEx.getPermissionManager();

        for (PermissionGroup group : permissionManager.getGroups()) {
            PermissionGroupHook permissionGroupHook = new PermissionGroupHook(
                    group.getName()
            );

            GroupRegistry.registerGroup(permissionGroupHook);
        }

        return this.setInstance(permissionManager);
    }

    class PermissionGroupHook extends Group {

        public PermissionGroupHook(String name) {
            super(name);
        }

        @Override
        public String getDisplayNameStriped() {
            return PermissionsExHook.this.provide().getGroup(this.getName()).getPrefix().split("\\[")[1].split("]")[0];
        }

    }

}
