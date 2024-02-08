package ru.clevertec.ecl.knyazev.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.repository.AddressRepositoryCustom;
import ru.clevertec.ecl.knyazev.repository.exception.RepositoryException;

@NoArgsConstructor
@AllArgsConstructor
public class AddressRepositoryCustomImpl implements AddressRepositoryCustom {

    private final static String[] SEARCHING_SORTING_FIELDS = {"area", "country", "city", "street"};

    private static final String FIND_ALL_QUERY = """
            SELECT DISTINCT id, uuid, area, country, city, street, number FROM address""";
    private static final String FIND_ALL_COUNT_QUERY = "SELECT DISTINCT COUNT(id) FROM address";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Address> findAll(Pageable pageable, Searching searching) throws RepositoryException {

        return findAllNativeQuery(entityManager,
                FIND_ALL_QUERY,
                searching,
                pageable,
                SEARCHING_SORTING_FIELDS,
                Address.class,
                () -> findAllCount(searching, pageable));

    }

    private Long findAllCount(Searching searching,
                             Pageable pageable) {

        return findAllCount(entityManager,
                FIND_ALL_COUNT_QUERY,
                searching,
                pageable,SEARCHING_SORTING_FIELDS);
    }
}
