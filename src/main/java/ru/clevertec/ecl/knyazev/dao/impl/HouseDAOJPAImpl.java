package ru.clevertec.ecl.knyazev.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.knyazev.dao.AddressDAO;
import ru.clevertec.ecl.knyazev.dao.HouseDAO;
import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class HouseDAOJPAImpl implements HouseDAO {

    private static final String FIND_BY_UUID_QUERY = "SELECT h FROM House h WHERE h.uuid = :uuid";
    private static final String FIND_ALL_QUERY = "SELECT h FROM House h";

    private static final String FIND_ALL_WHERE_ADDRESS_SEARCHING_PART_QUERY =
            " WHERE h.address.uuid IN (:searchingAddressUUIDs)";

    @PersistenceContext
    private EntityManager entityManager;

    private AddressDAO addressDAOJPAImpl;

    @Autowired
    private HouseDAOJPAImpl(AddressDAO addressDAOJPAImpl) {
        this.addressDAOJPAImpl = addressDAOJPAImpl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<House> findByUUID(UUID uuid) {

        House house = null;

        try {
            house = entityManager.createQuery(FIND_BY_UUID_QUERY, House.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            log.error(String.format("%s%s: %s",
                    DAOException.ENTITY_NOT_FOUND,
                    uuid,
                    e.getMessage()), e);
        }
        return house != null
                ? Optional.of(house)
                : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<House> findAll() {
        List<House> houses = new ArrayList<>();

        try {
            houses = entityManager.createQuery(FIND_ALL_QUERY, House.class)
                    .getResultList();
        } catch (IllegalArgumentException | PersistenceException e) {
            log.error(String.format("%s: %s",
                    DAOException.FIND_ALL_ERROR,
                    e.getMessage()), e);
        }
        return houses;
    }


    /**
     * Find all houses, also using paging and searching.
     * <p>
     * When using searching also searching address text fields
     *
     * @param paging    paging param
     * @param searching searching param
     * @return
     */
    @Override
    public List<House> findAll(Paging paging, Searching searching) {

        List<House> houses = new ArrayList<>();

        String query = searching.useSearching()
                ? FIND_ALL_QUERY + FIND_ALL_WHERE_ADDRESS_SEARCHING_PART_QUERY
                : FIND_ALL_QUERY;

        try {
            TypedQuery<House> houseTypedQuery = entityManager.createQuery(query, House.class);

            if (searching.useSearching()) {
                //Use full text searching on Address fields and get its UUIDs
                List<UUID> searchingAddressUUIDs = addressDAOJPAImpl.findAll(
                                Paging.noPaging(), searching).stream()
                        .map(address -> address.getUuid())
                        .toList();

                houseTypedQuery = houseTypedQuery.setParameter("searchingAddressUUIDs",
                        searchingAddressUUIDs);
            }

            if (paging.usePaging()) {
                houseTypedQuery = houseTypedQuery.setFirstResult(paging.getOffset())
                        .setMaxResults(paging.getLimit());
            }

            houses = houseTypedQuery.getResultList();

        } catch (IllegalArgumentException | PersistenceException e) {
            log.error(String.format("%s: %s",
                    DAOException.FIND_ALL_ERROR,
                    e.getMessage()), e);
        }

        return houses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public House save(House house) throws DAOException {

        UUID addressUUID = house.getAddress().getUuid();
        Address houseAddress = addressDAOJPAImpl.findByUUID(addressUUID)
                .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                        DAOException.SAVING_ERROR,
                        DAOException.ENTITY_NOT_FOUND,
                        addressUUID)));

        house.setUuid(UUID.randomUUID());
        house.setAddress(houseAddress);
        house.setCreateDate(LocalDateTime.now());

        try {
            entityManager.persist(house);
        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DAOException(String.format("%s: %s",
                    DAOException.SAVING_ERROR,
                    e.getMessage()), e);
        }
        return house;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public House update(House house) throws DAOException {

        House houseDB = findByUUID(house.getUuid())
                .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                        DAOException.UPDATING_ERROR,
                        DAOException.ENTITY_NOT_FOUND,
                        house.getUuid())));

        UUID addressUUID = house.getAddress().getUuid();
        Address houseAddress = addressDAOJPAImpl.findByUUID(addressUUID)
                .orElseThrow(() -> new DAOException(String.format("%s: %s%s",
                        DAOException.UPDATING_ERROR,
                        DAOException.ENTITY_NOT_FOUND,
                        addressUUID)));

        houseDB.setAddress(houseAddress);


        try {
            entityManager.merge(houseDB);
        } catch (IllegalArgumentException | PersistenceException e) {
            throw new DAOException(String.format("%s: %s",
                    DAOException.UPDATING_ERROR,
                    e.getMessage()), e);
        }
        return houseDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(UUID houseUUID) throws DAOException {

        findByUUID(houseUUID).ifPresent(houseDB -> {
            try {
                entityManager.remove(houseDB);
            } catch (IllegalArgumentException | PersistenceException e) {
                throw new DAOException(String.format("%s: %s",
                        DAOException.DELETING_ERROR,
                        e.getMessage()), e);
            }
        });

    }
}
