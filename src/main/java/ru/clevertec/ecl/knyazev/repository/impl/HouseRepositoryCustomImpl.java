package ru.clevertec.ecl.knyazev.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.repository.AddressRepository;
import ru.clevertec.ecl.knyazev.repository.HouseRepositoryCustom;
import ru.clevertec.ecl.knyazev.repository.exception.RepositoryException;

@NoArgsConstructor
@AllArgsConstructor
public class HouseRepositoryCustomImpl implements HouseRepositoryCustom {

    private final static String[] SEARCHING_SORTING_FIELDS = {};

    private static final String FIND_ALL_QUERY = "SELECT id, uuid, address_id, create_date FROM house";
    private static final String FIND_ALL_COUNT_QUERY = "SELECT COUNT(id) FROM house";

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<House> findAll(Pageable pageable, Searching searching) throws RepositoryException {
        return findAllNativeQuery(entityManager,
                FIND_ALL_QUERY,
                searching,
                pageable,
                SEARCHING_SORTING_FIELDS,
                House.class,
                () -> findAllCount(searching, pageable));
    }


    private Long findAllCount(Searching searching, Pageable pageable) {
        return findAllCount(entityManager,
                FIND_ALL_COUNT_QUERY,
                searching,
                pageable, SEARCHING_SORTING_FIELDS);
    }
}
