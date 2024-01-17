package ru.clevertec.ecl.knyazev.dao;

import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.entity.House;

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
     * Find all houses or find all houses
     * on given paging data
     * and searching data
     *
     * @param paging paging param
     * @param searching searching param
     * @return all houses or all houses on given paging data
     * and searching data or empty list
     */
    List<House> findAll(Paging paging, Searching searching);

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
