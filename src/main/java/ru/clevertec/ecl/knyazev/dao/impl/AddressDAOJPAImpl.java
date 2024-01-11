package ru.clevertec.ecl.knyazev.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.knyazev.dao.AddressDAO;
import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.pagination.Paging;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AddressDAOJPAImpl implements AddressDAO {

    private static final String FIND_ALL_QUERY = "SELECT a FROM Address a";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Address> findByUUID(UUID uuid) {

        Address address = null;

        try {
            address = entityManager.find(Address.class, uuid);
        } catch (IllegalArgumentException e) {
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
    @Override
    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();

        try {
            addresses = entityManager.createQuery(FIND_ALL_QUERY, Address.class)
                    .getResultList();
        } catch (IllegalArgumentException | PersistenceException e) {
            log.error(String.format("%s: %s",
                    DAOException.FIND_ALL_ERROR,
                    e.getMessage()), e);
        }
        return addresses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Address> findAll(Paging paging) {

        List<Address> addresses = new ArrayList<>();

        try {
            addresses = entityManager.createQuery(FIND_ALL_QUERY, Address.class)
                    .setFirstResult(paging.getOffset())
                    .setMaxResults(paging.getLimit())
                    .getResultList();
        } catch (IllegalArgumentException | PersistenceException e) {
            log.error(String.format("%s: %s",
                    DAOException.FIND_ALL_ERROR,
                    e.getMessage()), e);
        }
        return addresses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Address save(Address address) throws DAOException {

        try {
            entityManager.persist(address);
        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DAOException(String.format("%s: %s",
                    DAOException.SAVING_ERROR,
                    e.getMessage()), e);
        }
        return address;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Address update(Address address) throws DAOException {

        Address addressDB = findByUUID(address.getUuid())
                .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                        DAOException.UPDATING_ERROR,
                        DAOException.ENTITY_NOT_FOUND,
                        address.getUuid())));

        addressDB.setUuid(address.getUuid());
        addressDB.setArea(address.getArea());
        addressDB.setCountry(address.getCountry());
        addressDB.setCity(address.getCity());
        addressDB.setStreet(address.getStreet());
        addressDB.setNumber(address.getNumber());

        try {
            entityManager.merge(addressDB);
        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DAOException(String.format("%s: %s",
                    DAOException.UPDATING_ERROR,
                    e.getMessage()), e);
        }
        return addressDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(UUID addressUUID) throws DAOException {

        findByUUID(addressUUID).ifPresent(addressDB -> {
            try {
                entityManager.remove(addressDB);
            } catch (IllegalArgumentException | PersistenceException e) {
                throw new DAOException(String.format("%s: %s",
                        DAOException.DELETING_ERROR,
                        e.getMessage()), e);
            }
        });

    }
}
