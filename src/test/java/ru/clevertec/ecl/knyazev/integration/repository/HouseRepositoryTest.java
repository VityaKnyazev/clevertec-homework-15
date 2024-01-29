package ru.clevertec.ecl.knyazev.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.integration.config.TestContainerInitializer;
import ru.clevertec.ecl.knyazev.repository.HouseRepository;

import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HouseRepositoryTest extends TestContainerInitializer {
    private static final String FIND_BY_UUID_QUERY = "SELECT h FROM House h WHERE h.uuid = :uuid";
    private static final String FIND_ALL = "SELECT h FROM House h";

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void checkFindByUUIDShouldReturnHouse() {
        UUID houseUUID = UUID.fromString("46d2e812-80cd-4485-9fec-b20496c24963");

        House expectedHouse = testEntityManager.getEntityManager()
                .createQuery(FIND_BY_UUID_QUERY, House.class)
                .setParameter("uuid", houseUUID)
                .getSingleResult();

        House actualHouse = houseRepository.findByUuid(houseUUID).get();

        assertThat(actualHouse).isEqualTo(expectedHouse);
    }

    @Test
    public void checkFindAllShouldReturnPageableHouses() {
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<House> expectedHouses = testEntityManager.getEntityManager()
                .createQuery(FIND_ALL, House.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Page<House> actualPageHouses = houseRepository.findAll(pageable);

        assertThat(actualPageHouses.toList())
                .isEqualTo(expectedHouses);
    }

    @Test
    public void checkDeleteByUuidShouldDeleteHouse() {
        UUID deletingHouseUUID = UUID.fromString("4b736895-8c28-438c-b577-21b4479474ed");

        houseRepository.deleteByUuid(deletingHouseUUID);

        assertThatExceptionOfType(NoResultException.class)
                .isThrownBy(() -> testEntityManager.getEntityManager()
                        .createQuery(FIND_BY_UUID_QUERY, House.class)
                        .setParameter("uuid", deletingHouseUUID)
                        .getSingleResult());
    }
}
