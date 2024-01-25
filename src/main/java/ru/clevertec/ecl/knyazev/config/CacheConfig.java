package ru.clevertec.ecl.knyazev.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ecl.knyazev.cache.factory.AbstractCacheFactory;
import ru.clevertec.ecl.knyazev.cache.factory.impl.ConcurrentCacheFactory;
import ru.clevertec.ecl.knyazev.cache.operator.AbstractCacheOperator;
import ru.clevertec.ecl.knyazev.cache.operator.impl.HouseCacheOperator;
import ru.clevertec.ecl.knyazev.cache.operator.impl.PersonCacheOperator;
import ru.clevertec.ecl.knyazev.config.properties.CacheProperties;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.mapper.HouseMapper;
import ru.clevertec.ecl.knyazev.mapper.PersonMapper;
import ru.clevertec.ecl.knyazev.repository.HouseRepository;
import ru.clevertec.ecl.knyazev.repository.PersonRepository;
import ru.clevertec.ecl.knyazev.repository.proxy.impl.HouseRepositoryCacheProxy;
import ru.clevertec.ecl.knyazev.repository.proxy.impl.PersonRepositoryCacheProxy;
import ru.clevertec.ecl.knyazev.service.AddressService;
import ru.clevertec.ecl.knyazev.service.HouseService;
import ru.clevertec.ecl.knyazev.service.PassportService;
import ru.clevertec.ecl.knyazev.service.PersonService;
import ru.clevertec.ecl.knyazev.service.impl.HouseServiceImpl;
import ru.clevertec.ecl.knyazev.service.impl.PersonServiceImpl;

import java.lang.reflect.Proxy;
import java.util.UUID;

@ConditionalOnProperty(prefix = "cache",
        name = "enabled",
        havingValue = "true")
@Configuration
public class CacheConfig {

    @Bean
    AbstractCacheFactory concurrentCacheFactory(CacheProperties cacheProperties) {
        return new ConcurrentCacheFactory(cacheProperties.algorithm(), cacheProperties.size());
    }

    @Bean
    AbstractCacheOperator<UUID, Person> personCacheOperator(AbstractCacheFactory concurrentCacheFactory) {
        return new PersonCacheOperator(concurrentCacheFactory.initCache());
    }

    @Bean
    AbstractCacheOperator<UUID, House> houseCacheOperator(AbstractCacheFactory concurrentCacheFactory) {
        return new HouseCacheOperator(concurrentCacheFactory.initCache());
    }

    @Bean
    PersonRepositoryCacheProxy personRepositoryCacheProxy(PersonRepository personRepository,
                                                          AbstractCacheOperator<UUID, Person> personCacheOperator) {
        return new PersonRepositoryCacheProxy(personRepository,
                personCacheOperator);
    }

    @Bean
    HouseRepositoryCacheProxy houseRepositoryCacheProxy(HouseRepository houseRepository,
                                                        AbstractCacheOperator<UUID, House> houseCacheOperator) {
        return new HouseRepositoryCacheProxy(houseRepository,
                houseCacheOperator);
    }

    @Bean
    PersonRepository personRepositoryProxy(PersonRepositoryCacheProxy personRepositoryCacheProxy) {
        return (PersonRepository) Proxy.newProxyInstance(PersonRepository.class.getClassLoader(),
                new Class[]{PersonRepository.class}, personRepositoryCacheProxy);
    }

    @Bean
    HouseRepository houseRepositoryProxy(HouseRepositoryCacheProxy houseRepositoryCacheProxy) {
        return (HouseRepository) Proxy.newProxyInstance(HouseRepository.class.getClassLoader(),
                new Class[]{HouseRepository.class}, houseRepositoryCacheProxy);
    }

    @Bean
    PersonService personServiceImpl(PersonRepository personRepositoryProxy,
                                    PassportService passportServiceImpl,
                                    HouseService houseServiceImpl,
                                    PersonMapper personMapperImpl,
                                    HouseMapper houseMapperImpl) {
        return new PersonServiceImpl(personRepositoryProxy,
                passportServiceImpl,
                houseServiceImpl,
                personMapperImpl,
                houseMapperImpl);
    }

    @Bean
    HouseService houseServiceImpl(HouseRepository houseRepositoryProxy,
                                  AddressService addressServiceImpl,
                                  HouseMapper houseMapperImpl,
                                  PersonMapper personMapperImpl) {
        return new HouseServiceImpl(houseRepositoryProxy,
                addressServiceImpl,
                houseMapperImpl,
                personMapperImpl);
    }
}
