package ru.clevertec.ecl.knyazev.dao;

import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.pagination.Paging;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * Represents data access object for Person entity
 *
 */
public interface PersonDAO {
    /**
     *
     * Find Person by uuid
     *
     * @param uuid person uuid for searching
     * @return optional person or optional empty if person not found
     */
    Optional<Person> findByUUID(UUID uuid);

    /**
     *
     * Find all persons
     *
     * @return all persons or empty list
     */
    List<Person> findAll();

    /**
     *
     * Find all persons on given paging data
     *
     * @param paging paging param
     * @return all persons on given paging query or empty list
     */
    List<Person> findAll(Paging paging);

    /**
     *
     * save person
     *
     * @param person for saving
     * @return saved person
     * @throws DAOException when error saving
     */
    Person save(Person person) throws DAOException;

    /**
     *
     * update person
     *
     * @param person for updating
     * @return updated person
     * @throws DAOException when error updating
     */
    Person update(Person person) throws DAOException;

    /**
     *
     * delete person
     *
     * @param personUUID person uuid
     * @throws DAOException when deleting constraint
     */
    void delete(UUID personUUID) throws DAOException;
}
