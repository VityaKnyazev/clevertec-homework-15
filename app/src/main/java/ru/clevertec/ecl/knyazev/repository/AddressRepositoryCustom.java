package ru.clevertec.ecl.knyazev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.repository.exception.RepositoryException;

/**
 * Represents custom JPA Repository for Address data
 */
public interface AddressRepositoryCustom extends CustomRepository {
    /**
     *
     * Find all addresses or find all addresses
     * on given pageable data
     * and searching data
     *
     * @param pageable pageable param
     * @param searching searching param
     * @return page addresses or page addresses on given pageable data
     * and searching data or empty page
     * @throws RepositoryException when finding error
     */
    Page<Address> findAll(Pageable pageable, Searching searching) throws RepositoryException;
}
