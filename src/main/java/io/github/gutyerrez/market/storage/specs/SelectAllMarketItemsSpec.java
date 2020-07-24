package io.github.gutyerrez.market.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.market.MarketConstants;

/**
 * @author SrGutyerrez
 */
public class SelectAllMarketItemsSpec extends SelectMarketItemsSpec {

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "SELECT * FROM `%s`",
                    MarketConstants.Databases.Mysql.Tables.MARKET_ITEM_TABLE_NAME
            );

            return connection.prepareStatement(query);
        };
    }
}
