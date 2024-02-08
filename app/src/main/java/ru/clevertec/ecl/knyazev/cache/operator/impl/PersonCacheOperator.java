package ru.clevertec.ecl.knyazev.cache.operator.impl;

import ru.clevertec.ecl.knyazev.cache.Cache;
import ru.clevertec.ecl.knyazev.cache.operator.AbstractCacheOperator;
import ru.clevertec.ecl.knyazev.entity.Person;

import java.util.UUID;

public class PersonCacheOperator extends AbstractCacheOperator<UUID, Person> {
    public PersonCacheOperator(Cache<UUID, Person> cache) {
        super(cache);
    }
}
