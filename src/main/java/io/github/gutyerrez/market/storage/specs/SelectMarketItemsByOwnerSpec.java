package io.github.gutyerrez.market.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.market.MarketConstants;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class SelectMarketItemsByOwnerSpec extends SelectMarketItemsSpec {

    private final String ownerUUIDString;

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "SELECT * FROM `%s` WHERE `owner`=?;",
                    MarketConstants.Databases.Mysql.Tables.MARKET_ITEM_TABLE_NAME
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.ownerUUIDString);

            return preparedStatement;
        };
    }

}
