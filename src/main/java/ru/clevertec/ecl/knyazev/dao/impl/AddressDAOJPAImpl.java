package ru.clevertec.ecl.knyazev.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.knyazev.dao.AddressDAO;
import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.entity.Address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AddressDAOJPAImpl implements AddressDAO {

    private final static String[] SEARCHING_FIELDS = {"area", "country", "city", "street"};

    private static final String FIND_BY_UUID_QUERY = "SELECT a FROM Address a WHERE a.uuid = :uuid";
    private static final String FIND_ALL_QUERY = """
            SELECT DISTINCT id, uuid, area, country, city, street, number FROM address""";
    private static final String REMOVE_QUERY = "DELETE FROM Address a WHERE a.uuid = :addressUUID";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Address> findByUUID(UUID uuid) {

        Address address = null;

        try {
            address = entityManager.createQuery(FIND_BY_UUID_QUERY, Address.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            log.error(String.format("%s%s: %s",
                    DAOException.ENTITY_NOT_FOUND,
                    uuid,
                    e.getMessage()), e);
        }
        return address != null
                ? Optional.of(address)
                : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Address> findAll(Paging paging, Searching searching) throws DAOException {
        List<Address> addresses = new ArrayList<>();

        String queryRes = searching.useSearching()
                ? FIND_ALL_QUERY + searching.getSearchingQueryPart(Arrays.asList(SEARCHING_FIELDS))
                : FIND_ALL_QUERY;

        try {

            Query query = entityManager.createNativeQuery(queryRes,
                    Address.class);

            if (searching.useSearching()) {
                query = query.setParameter(1, searching.getSearchingValue())
                        .setParameter(2, searching.getSearchingValue())
                        .setParameter(3, searching.getSearchingValue())
                        .setParameter(4, searching.getSearchingValue())
                        .setParameter(5, searching.getSearchingValue())
                        .setParameter(6, searching.getSearchingValue())
                        .setParameter(7, searching.getSearchingValue())
                        .setParameter(8, searching.getSearchingValue())
                        .setParameter(9, searching.getSearchingValue())
                        .setParameter(10, searching.getSearchingValue())
                        .setParameter(11, searching.getSearchingValue())
                        .setParameter(12, searching.getSearchingValue());
            }

            if (paging.usePaging()) {
                query = query.setFirstResult(paging.getOffset())
                        .setMaxResults(paging.getLimit());
            }

            addresses = (List<Address>) query.getResultList();

        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DAOException(String.format("%s: %s",
                    DAOException.FIND_ALL_ERROR,
                    e.getMessage()), e);
        }
        return addresses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Address saveOrUpdate(Address address) throws DAOException {

        try {
            if (address.getId() == null) {
                entityManager.persist(address);
            } else {
                entityManager.merge(address);
            }
        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DAOException(String.format("%s: %s",
                    DAOException.SAVING_OR_UPDATING_ERROR,
                    e.getMessage()), e);
        }
        return address;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(UUID addressUUID) throws DAOException {

            try {
                entityManager.createQuery(REMOVE_QUERY)
                        .setParameter("addressUUID", addressUUID)
                        .executeUpdate();
            } catch (IllegalArgumentException | PersistenceException e) {
                throw new DAOException(String.format("%s: %s",
                        DAOException.DELETING_ERROR,
                        e.getMessage()), e);
            }
    }
}
