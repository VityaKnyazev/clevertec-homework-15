package ru.clevertec.ecl.knyazev.repository.proxy.impl;

import org.springframework.dao.DataAccessException;
import ru.clevertec.ecl.knyazev.cache.operator.AbstractCacheOperator;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.repository.HouseRepository;
import ru.clevertec.ecl.knyazev.repository.proxy.RepositoryCacheProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

public class HouseRepositoryCacheProxy extends RepositoryCacheProxy<UUID, House> implements InvocationHandler {

    private final HouseRepository houseRepository;

    public HouseRepositoryCacheProxy(HouseRepository houseRepository,
                                     AbstractCacheOperator<UUID, House> cacheOperator) {
        super(cacheOperator);

        this.houseRepository = houseRepository;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.executeProxyMethod(houseRepository,
                method,
                args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Optional<House> whenFindByUuid(UUID uuid) {
        return cacheOperator.find(uuid)
                .or(() -> houseRepository.findByUuid(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected House whenSaveOrUpdate(House savingOrUpdatingHouse) throws DataAccessException {
        House savedOrUpdatedHouse = houseRepository.save(savingOrUpdatingHouse);

        cacheOperator.add(savedOrUpdatedHouse.getUuid(), savedOrUpdatedHouse);
        return savedOrUpdatedHouse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void whenDelete(UUID deletingHouseUUID) throws DataAccessException {
        houseRepository.deleteByUuid(deletingHouseUUID);
        cacheOperator.delete(deletingHouseUUID);
    }

}
