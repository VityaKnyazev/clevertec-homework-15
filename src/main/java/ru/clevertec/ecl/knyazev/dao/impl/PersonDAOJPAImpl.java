package ru.clevertec.ecl.knyazev.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.knyazev.dao.PassportDAO;
import ru.clevertec.ecl.knyazev.dao.PersonDAO;
import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Passport;
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

    private static final String FIND_BY_UUID_QUERY = "SELECT p FROM Person p WHERE p.uuid = :uuid";
    private static final String FIND_ALL_QUERY = "SELECT p FROM Person p";

    @PersistenceContext
    private EntityManager entityManager;

    private PassportDAO passportDAOImpl;
    private HouseDAOJPAImpl houseDAOJPAImpl;

    @Autowired
    public PersonDAOJPAImpl(PassportDAO passportDAOImpl, HouseDAOJPAImpl houseDAOJPAImpl) {
        this.passportDAOImpl = passportDAOImpl;
        this.houseDAOJPAImpl = houseDAOJPAImpl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findByUUID(UUID uuid) {

        Person person = null;

        try {
            person = entityManager.createQuery(FIND_BY_UUID_QUERY, Person.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
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

        Passport passportDB = getPersonPassport(person);
        House livingHouseDB = getPersonLivingHouse(person);
        List<House> possessingHousesDB = getPersonPossessingHouses(person);

        person.setPassport(passportDB);
        person.setLivingHouse(livingHouseDB);
        person.setPossessedHouses(possessingHousesDB);

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

        Passport passportDB = getPersonPassport(person);
        House livingHouseDB = getPersonLivingHouse(person);
        List<House> possessingHousesDB = getPersonPossessingHouses(person);

        Person personDB = findByUUID(person.getUuid())
                .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                        DAOException.UPDATING_ERROR,
                        DAOException.ENTITY_NOT_FOUND,
                        person.getUuid())));

        personDB.setName(person.getName());
        personDB.setSurname(person.getSurname());
        personDB.setSex(person.getSex());

        personDB.setPassport(passportDB);
        personDB.setLivingHouse(livingHouseDB);
        personDB.setPossessedHouses(possessingHousesDB);

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

    /**
     *
     * Get person passport from db
     *
     * @param person person that contains passport uuid
     * @return person passport from db by its uuid
     * @throws DAOException when person passport not found
     */
    private Passport getPersonPassport(Person person) throws DAOException {
        UUID passportUuid = person.getPassport().getUuid();

        return passportDAOImpl.findByUUID(passportUuid)
                .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                        DAOException.SAVING_ERROR,
                        DAOException.ENTITY_NOT_FOUND,
                        passportUuid)));
    }

    /**
     *
     * Get person living house from db
     *
     * @param person person that contains living house uuid
     * @return person living house from db by its uuid
     * @throws DAOException when person living house not found
     */
    private House getPersonLivingHouse(Person person) throws DAOException {
        UUID livingHouseUuid = person.getLivingHouse().getUuid();

        return houseDAOJPAImpl.findByUUID(livingHouseUuid)
                .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                        DAOException.SAVING_ERROR,
                        DAOException.ENTITY_NOT_FOUND,
                        livingHouseUuid)));
    }

    /**
     *
     * Get person possessing houses from db
     *
     * @param person person that contains possessing houses UUIDs
     * @return person possessing houses from db by its uuid
     * @throws DAOException when person possessing houses not found
     */
    private List<House> getPersonPossessingHouses(Person person) throws DAOException {
        List<UUID> possessingHouseUUIDs = person.getPossessedHouses().stream()
                .map(House::getUuid).toList();

        return possessingHouseUUIDs.stream()
                .map(hUUID -> houseDAOJPAImpl.findByUUID(hUUID)
                        .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                                DAOException.SAVING_ERROR,
                                DAOException.ENTITY_NOT_FOUND,
                                hUUID))))
                .toList();
    }
}
