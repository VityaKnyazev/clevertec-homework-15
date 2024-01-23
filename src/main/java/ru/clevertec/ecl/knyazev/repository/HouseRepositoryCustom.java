package ru.clevertec.ecl.knyazev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.repository.exception.RepositoryException;

/**
 * Represents custom JPA Repository for House data
 */
public interface HouseRepositoryCustom extends CustomRepository {
    /**
     *
     * Find all houses or find all houses
     * on given pageable data
     * and searching data
     *
     * @param pageable pageable param
     * @param searching searching param
     * @return page houses or page houses on given pageable data
     * and searching data or empty page
     * @throws RepositoryException when finding error
     */
    Page<House> findAll(Pageable pageable, Searching searching) throws RepositoryException;
}
