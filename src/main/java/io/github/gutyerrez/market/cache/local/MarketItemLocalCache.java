package io.github.gutyerrez.market.cache.local;

import com.google.common.collect.Maps;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.market.api.MarketItem;
import io.github.gutyerrez.market.api.category.MarketCategory;
import io.github.gutyerrez.market.api.category.MarketCategoryRegistry;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SrGutyerrez
 */
public class MarketItemLocalCache implements LocalCache {

    private final Map<Integer, MarketItem> CACHE = Maps.newHashMap();

    public LinkedList<MarketItem> get(MarketCategory marketCategory) {
        return this.CACHE.values()
                .stream()
                .filter(marketItem -> {
                    MarketCategory _marketCategory = MarketCategoryRegistry.getCategory(marketItem.getItem());

                    if (_marketCategory == null) {
                        return false;
                    }

                    return marketItem.getTarget() == null && _marketCategory.equals(marketCategory);
                })
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void remove(Integer id) {
        this.CACHE.remove(id);
    }

}
