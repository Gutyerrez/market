package io.github.gutyerrez.market.cache.local;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.market.api.MarketItem;
import io.github.gutyerrez.market.api.category.MarketCategory;
import io.github.gutyerrez.market.api.category.MarketCategoryRegistry;

import java.util.LinkedList;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author SrGutyerrez
 */
public class MarketItemLocalCache implements LocalCache {

    private final Multimap<UUID, MarketItem> CACHE = HashMultimap.create();

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
                .map(targetItem -> targetItem)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    // remover isso aqui depois
    public void add(MarketItem marketItem) {
        this.CACHE.put(marketItem.getOwner(), marketItem);
    }

    public void updateCache(Multimap<UUID, MarketItem> cache) {
        this.CACHE.clear();
        this.CACHE.putAll(cache);
    }

}
