package ru.clevertec.ecl.knyazev.dao;

import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.pagination.Paging;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * Represents data access object for House entity
 *
 */
public interface HouseDAO {
    /**
     *
     * Find House by uuid
     *
     * @param uuid house uuid for searching
     * @return optional house or optional empty if house not found
     */
    Optional<House> findByUUID(UUID uuid);

    /**
     *
     * Find all houses
     *
     * @return all houses or empty list
     */
    List<House> findAll();

    /**
     *
     * Find all houses on given paging data
     *
     * @param paging paging param
     * @return all houses on given paging query or empty list
     */
    List<House> findAll(Paging paging);

    /**
     *
     * save house
     *
     * @param house for saving
     * @return saved house
     * @throws DAOException when error saving
     */
    House save(House house) throws DAOException;

    /**
     *
     * update house
     *
     * @param house for updating
     * @return updated house
     * @throws DAOException when error updating
     */
    House update(House house) throws DAOException;

    /**
     *
     * delete house
     *
     * @param houseUUID house uuid
     * @throws DAOException when deleting constraint
     */
    void delete(UUID houseUUID) throws DAOException;
}
