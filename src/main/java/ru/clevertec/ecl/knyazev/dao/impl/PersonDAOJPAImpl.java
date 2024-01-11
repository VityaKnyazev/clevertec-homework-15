package ru.clevertec.ecl.knyazev.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.knyazev.dao.PersonDAO;
import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.pagination.Paging;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PersonDAOJPAImpl implements PersonDAO {

    private static final String FIND_ALL_QUERY = "SELECT p FROM Person p";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findByUUID(UUID uuid) {

        Person person = null;

        try {
            person = entityManager.find(Person.class, uuid);
        } catch (IllegalArgumentException e) {
            log.error(String.format("%s%s: %s",
                    DAOException.ENTITY_NOT_FOUND,
                    uuid,
                    e.getMessage()), e);
        }
        return person != null
                ? Optional.of(person)
                : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();

        try {
            persons = entityManager.createQuery(FIND_ALL_QUERY, Person.class)
                    .getResultList();
        } catch (IllegalArgumentException | PersistenceException e) {
            log.error(String.format("%s: %s",
                    DAOException.FIND_ALL_ERROR,
                    e.getMessage()), e);
        }
        return persons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> findAll(Paging paging) {

        List<Person> persons = new ArrayList<>();

        try {
            persons = entityManager.createQuery(FIND_ALL_QUERY, Person.class)
                    .setFirstResult(paging.getOffset())
                    .setMaxResults(paging.getLimit())
                    .getResultList();
        } catch (IllegalArgumentException | PersistenceException e) {
            log.error(String.format("%s: %s",
                    DAOException.FIND_ALL_ERROR,
                    e.getMessage()), e);
        }
        return persons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person save(Person person) throws DAOException {

        try {
            entityManager.persist(person);
        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DAOException(String.format("%s: %s",
                    DAOException.SAVING_ERROR,
                    e.getMessage()), e);
        }
        return person;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person update(Person person) throws DAOException {

        Person personDB = findByUUID(person.getUuid())
                .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                        DAOException.UPDATING_ERROR,
                        DAOException.ENTITY_NOT_FOUND,
                        person.getUuid())));

        personDB.setUuid(person.getUuid());
        personDB.setName(person.getName());
        personDB.setSurname(person.getSurname());
        personDB.setSex(person.getSex());
        personDB.setPassport(person.getPassport());
        personDB.setLivingHouse(person.getLivingHouse());
        personDB.setPossessedHouses(person.getPossessedHouses());
        personDB.setUpdateDate(person.getUpdateDate());


        try {
            entityManager.merge(personDB);
        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DAOException(String.format("%s: %s",
                    DAOException.UPDATING_ERROR,
                    e.getMessage()), e);
        }
        return personDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(UUID personUUID) throws DAOException {

        findByUUID(personUUID).ifPresent(personDB -> {
            try {
                entityManager.remove(personDB);
            } catch (IllegalArgumentException | PersistenceException e) {
                throw new DAOException(String.format("%s: %s",
                        DAOException.DELETING_ERROR,
                        e.getMessage()), e);
            }
        });

    }
}
