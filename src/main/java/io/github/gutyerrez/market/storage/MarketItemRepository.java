package io.github.gutyerrez.market.storage;

import com.google.common.collect.Multimap;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.MysqlRepository;
import io.github.gutyerrez.core.shared.providers.MysqlDatabaseProvider;
import io.github.gutyerrez.market.api.MarketItem;
import io.github.gutyerrez.market.storage.specs.DeleteMarketItemSpec;
import io.github.gutyerrez.market.storage.specs.InsertMarketItemSpec;
import io.github.gutyerrez.market.storage.specs.SelectMarketItemsByOwnerSpec;
import io.github.gutyerrez.market.storage.specs.SelectNonExpiredMarketItemsSpec;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.UUID;

/**
 * @author SrGutyerrez
 */
public class MarketItemRepository extends MysqlRepository {

    public MarketItemRepository(MysqlDatabaseProvider databaseProvider) {
        super(databaseProvider);
    }

    public MarketItem create(UUID owner, UUID target, ItemStack item, Double price) {
        return this.query(new InsertMarketItemSpec(owner.toString(), target.toString(), item, price, new Date()));
    }

    public Multimap<UUID, MarketItem> fetchNonExpired() {
        return this.query(new SelectNonExpiredMarketItemsSpec());
    }

    public Multimap<UUID, MarketItem> fetch(UUID uuid) {
        return this.query(new SelectMarketItemsByOwnerSpec(uuid.toString()));
    }

    public Boolean delete(MarketItem marketItem) {
        return this.query(new DeleteMarketItemSpec(marketItem));
    }

}
