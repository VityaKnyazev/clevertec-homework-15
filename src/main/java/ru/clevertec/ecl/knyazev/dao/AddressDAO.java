package ru.clevertec.ecl.knyazev.dao;

import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.entity.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * Represents data access object for Address entity
 *
 */
public interface AddressDAO {
    /**
     *
     * Find Address by uuid
     *
     * @param uuid address uuid for searching
     * @return optional address or optional empty if address not found
     */
    Optional<Address> findByUUID(UUID uuid);

    /**
     *
     * Find all addresses or find all addresses
     * on given paging data
     * and searching data
     *
     * @param paging paging param
     * @param searching searching param
     * @return all addresses or all addresses on given paging data
     * and searching data or empty list
     * @throws DAOException when finding error
     */
    List<Address> findAll(Paging paging, Searching searching) throws DAOException;

    /**
     *
     * Save or update address. if id is null using saving,
     * otherwise - updating
     *
     * @param address for saving or updating
     * @return saved or updates address
     * @throws DAOException when error saving or updating
     */
    Address saveOrUpdate(Address address) throws DAOException;

    /**
     *
     * delete address
     *
     * @param addressUUID address uuid
     * @throws DAOException when deleting constraint
     */
    void delete(UUID addressUUID) throws DAOException;
}
