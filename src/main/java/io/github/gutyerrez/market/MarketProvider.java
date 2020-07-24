package io.github.gutyerrez.market;

import com.google.common.collect.Lists;
import io.github.gutyerrez.core.shared.misc.hooks.Hook;
import io.github.gutyerrez.core.shared.providers.LocalCacheProvider;
import io.github.gutyerrez.core.shared.providers.MysqlDatabaseProvider;
import io.github.gutyerrez.core.shared.providers.MysqlRepositoryProvider;
import io.github.gutyerrez.market.cache.local.MarketItemLocalCache;
import io.github.gutyerrez.market.misc.hooks.EconomyHook;
import io.github.gutyerrez.market.misc.hooks.PermissionsExHook;
import io.github.gutyerrez.market.storage.MarketItemRepository;

import java.util.List;

/**
 * @author SrGutyerrez
 */
public class MarketProvider {

    private static final List<Hook> HOOKS = Lists.newLinkedList();

    static {
        HOOKS.add(MarketProvider.Hooks.PERMISSIONS_EX);
        HOOKS.add(MarketProvider.Hooks.ECONOMY);
    }

    public static void prepare(MysqlDatabaseProvider databaseProvider) {
        Repositories.MARKET_ITEM = new MysqlRepositoryProvider<>(
                () -> databaseProvider,
                MarketItemRepository.class
        );

        // Prepare repositories

        Repositories.MARKET_ITEM.prepare();

        // Prepare caches

        Cache.Local.MARKET_ITEM.prepare();

        // Prepare hooks

        HOOKS.forEach(Hook::prepare);
    }

    public static class Repositories {

        public static MysqlRepositoryProvider<MarketItemRepository> MARKET_ITEM;

    }

    public static class Cache {

        public static class Local {

            public static LocalCacheProvider<MarketItemLocalCache> MARKET_ITEM = new LocalCacheProvider<>(
                    new MarketItemLocalCache()
            );

        }

    }

    public static class Hooks {

        public static PermissionsExHook<?> PERMISSIONS_EX = new PermissionsExHook<>();

        public static EconomyHook<?> ECONOMY = new EconomyHook<>();

    }

}
