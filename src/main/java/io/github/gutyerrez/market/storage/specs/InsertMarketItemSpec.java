package io.github.gutyerrez.market.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.InsertSqlSpec;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.spigot.misc.utils.InventoryUtils;
import io.github.gutyerrez.market.MarketConstants;
import io.github.gutyerrez.market.api.MarketItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.UUID;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class InsertMarketItemSpec extends InsertSqlSpec<MarketItem> {
    private final String ownerUUID;
    private final String targetUUID;
    private final ItemStack itemStack;
    private final Double price;
    private final Date createdAt;

    @Override
    public MarketItem parser(int affectedRows, ResultSet keyHolder) throws SQLException {
        if (affectedRows != 1) {
            return null;
        }

        return new MarketItem(
                keyHolder.getInt("id"),
                UUID.fromString(this.ownerUUID),
                this.targetUUID == null ? null : UUID.fromString(this.targetUUID),
                this.itemStack,
                this.price,
                this.createdAt
        );
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "INSERT INTO `%s` " +
                            "(" +
                            "`owner`," +
                            "`target`," +
                            "`item`," +
                            "`price`," +
                            "`created_at`" +
                            ") VALUES (?,?,?,?,?);",
                    MarketConstants.Databases.Mysql.Tables.MARKET_ITEM_TABLE_NAME
            );

            PreparedStatement preparedStatement = connection.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, this.ownerUUID);
            preparedStatement.setString(2, this.targetUUID);
            preparedStatement.setString(3, InventoryUtils.serializeContents(
                    new ItemStack[] {
                            this.itemStack
                    }
            ));
            preparedStatement.setDouble(4, this.price);
            preparedStatement.setTimestamp(5, new Timestamp(
                    this.createdAt.getTime()
            ));

            return preparedStatement;
        };
    }
}
