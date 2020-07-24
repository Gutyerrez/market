package io.github.gutyerrez.market.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.DeleteSqlSpec;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.market.MarketConstants;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class DeleteMarketItemSpec extends DeleteSqlSpec<Boolean> {

    private final Integer id;

    @Override
    public Boolean parser(int affectedRows) {
        return affectedRows == 1;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "DELETE FROM `%s` WHERE `id`=?;",
                    MarketConstants.Databases.Mysql.Tables.MARKET_ITEM_TABLE_NAME
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, this.id);

            return preparedStatement;
        };
    }
}
