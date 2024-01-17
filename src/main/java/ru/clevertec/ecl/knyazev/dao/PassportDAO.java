package ru.clevertec.ecl.knyazev.dao;

import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.entity.Passport;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * Represents data access object for Passport entity
 *
 */
public interface PassportDAO {
    /**
     *
     * Find Passport by uuid
     *
     * @param uuid passport uuid for searching
     * @return optional passport or optional empty if passport not found
     */
    Optional<Passport> findByUUID(UUID uuid);

    /**
     *
     * Find all passports on given paging data
     *
     * @param paging paging param for pagination
     * @return all passports on given paging query or empty list
     * @throws DAOException when finding error
     */
    List<Passport> findAll(Paging paging) throws DAOException;

    /**
     *
     * save passport
     *
     * @param passport for saving
     * @return saved passport
     * @throws DAOException when error saving
     */
    Passport save(Passport passport) throws DAOException;

    /**
     *
     * update passport
     *
     * @param passport for updating
     * @return updated passport
     * @throws DAOException when error updating
     */
    Passport update(Passport passport) throws DAOException;

    /**
     *
     * delete passport
     *
     * @param passportUUID passport uuid
     * @throws DAOException when deleting constraint
     */
    void delete(UUID passportUUID) throws DAOException;
}
