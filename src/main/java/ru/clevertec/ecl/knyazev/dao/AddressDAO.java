package ru.clevertec.ecl.knyazev.dao;

import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.pagination.Paging;

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
     * Find all addresses
     *
     * @return all addresses or empty list
     */
    List<Address> findAll();

    /**
     *
     * Find all addresses on given paging data
     *
     * @param paging paging param
     * @return all addresses on given paging query or empty list
     */
    List<Address> findAll(Paging paging);

    /**
     *
     * save address
     *
     * @param address for saving
     * @return saved address
     * @throws DAOException when error saving
     */
    Address save(Address address) throws DAOException;

    /**
     *
     * update address
     *
     * @param address for updating
     * @return updated address
     * @throws DAOException when error updating
     */
    Address update(Address address) throws DAOException;

    /**
     *
     * delete address
     *
     * @param addressUUID address uuid
     * @throws DAOException when deleting constraint
     */
    void delete(UUID addressUUID) throws DAOException;
}
