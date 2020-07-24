package io.github.gutyerrez.market.storage;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.MysqlRepository;
import io.github.gutyerrez.core.shared.providers.MysqlDatabaseProvider;

/**
 * @author SrGutyerrez
 */
public class MarketItemRepository extends MysqlRepository {

    public MarketItemRepository(MysqlDatabaseProvider databaseProvider) {
        super(databaseProvider);
    }

}
