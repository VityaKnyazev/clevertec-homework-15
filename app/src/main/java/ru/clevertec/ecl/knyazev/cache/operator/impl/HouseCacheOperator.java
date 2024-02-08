package ru.clevertec.ecl.knyazev.cache.operator.impl;

import ru.clevertec.ecl.knyazev.cache.Cache;
import ru.clevertec.ecl.knyazev.cache.operator.AbstractCacheOperator;
import ru.clevertec.ecl.knyazev.entity.House;

import java.util.UUID;

public class HouseCacheOperator extends AbstractCacheOperator<UUID, House> {
    public HouseCacheOperator(Cache<UUID, House> cache) {
        super(cache);
    }
}
