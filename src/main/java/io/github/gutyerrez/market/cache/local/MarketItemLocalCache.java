package io.github.gutyerrez.market.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Maps;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.market.api.MarketItem;
import io.github.gutyerrez.market.api.category.MarketCategory;
import io.github.gutyerrez.market.api.category.MarketCategoryRegistry;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author SrGutyerrez
 */
public class MarketItemLocalCache implements LocalCache {

    private final Cache<Integer, MarketItem> CACHE = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.SECONDS)
            .build(id -> null);

    public LinkedList<MarketItem> get(MarketCategory marketCategory) {
        return this.CACHE.asMap().values()
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

    // remover isso aqui depois
    public void add(MarketItem marketItem) {
        this.CACHE.put(marketItem.getId(), marketItem);
    }

}
