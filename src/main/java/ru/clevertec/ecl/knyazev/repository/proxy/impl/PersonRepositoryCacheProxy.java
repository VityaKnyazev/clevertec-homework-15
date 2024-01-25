package ru.clevertec.ecl.knyazev.repository.proxy.impl;

import org.springframework.dao.DataAccessException;
import ru.clevertec.ecl.knyazev.cache.operator.AbstractCacheOperator;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.repository.PersonRepository;
import ru.clevertec.ecl.knyazev.repository.proxy.RepositoryCacheProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

public class PersonRepositoryCacheProxy extends RepositoryCacheProxy<UUID, Person> implements InvocationHandler {

    private final PersonRepository personRepository;

    public PersonRepositoryCacheProxy(PersonRepository personRepository,
                                      AbstractCacheOperator<UUID, Person> cacheOperator) {
        super(cacheOperator);

        this.personRepository = personRepository;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.executeProxyMethod(personRepository,
                method,
                args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Optional<Person> whenFindByUuid(UUID uuid) {
        return cacheOperator.find(uuid)
                .or(() -> {
                    Optional<Person> dbPerson = personRepository.findByUuid(uuid);
                    dbPerson.ifPresent(personDB -> cacheOperator.add(personDB.getUuid(), personDB));
                    return dbPerson;
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Person whenSaveOrUpdate(Person savingOrUpdatingPerson) throws DataAccessException {

        Person savedOrUpdatedPerson = personRepository.save(savingOrUpdatingPerson);

        cacheOperator.add(savedOrUpdatedPerson.getUuid(), savedOrUpdatedPerson);
        return savedOrUpdatedPerson;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void whenDelete(UUID deletingPersonUUID) throws DataAccessException {
        personRepository.deleteByUuid(deletingPersonUUID);
        cacheOperator.delete(deletingPersonUUID);
    }

}
