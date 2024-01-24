package ru.clevertec.ecl.knyazev.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ecl.knyazev.cache.AbstractCacheFactory;
import ru.clevertec.ecl.knyazev.cache.impl.DefaultCacheFactory;
import ru.clevertec.ecl.knyazev.config.properties.CacheProperties;

@ConditionalOnExpression("${cache.enabled:true}")
@Configuration
public class CacheConfig {

    @Bean
    AbstractCacheFactory defaultCacheFactory(CacheProperties cacheProperties) {
        return new DefaultCacheFactory(cacheProperties.algorithm(), cacheProperties.size());
    }


}
