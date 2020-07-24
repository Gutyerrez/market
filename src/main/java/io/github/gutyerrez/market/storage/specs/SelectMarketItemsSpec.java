package io.github.gutyerrez.market.storage.specs;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.ResultSetExtractor;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.SelectSqlSpec;
import io.github.gutyerrez.core.spigot.misc.utils.InventoryUtils;
import io.github.gutyerrez.market.api.MarketItem;

import java.util.UUID;

/**
 * @author SrGutyerrez
 */
public abstract class SelectMarketItemsSpec extends SelectSqlSpec<Multimap<UUID, MarketItem>> {

    @Override
    public ResultSetExtractor<Multimap<UUID, MarketItem>> getResultSetExtractor() {
        return resultSet -> {
            Multimap<UUID, MarketItem> cache = HashMultimap.create();

            while (resultSet.next()) {
                String targetUUIDString = resultSet.getString("target");

                cache.put(
                        UUID.fromString(resultSet.getString("owner")),
                        new MarketItem(
                                resultSet.getInt("id"),
                                UUID.fromString(resultSet.getString("owner")),
                                targetUUIDString == null ? null : UUID.fromString(targetUUIDString),
                                InventoryUtils.deserializeContents(
                                        resultSet.getString("item")
                                )[0],
                                resultSet.getDouble("price"),
                                resultSet.getTimestamp("created_at")
                        )
                );
            }

            return cache;
        };
    }

}
