package ru.clevertec.ecl.knyazev.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.ecl.knyazev.entity.Passport;
import ru.clevertec.ecl.knyazev.integration.config.TestContainerInitializer;
import ru.clevertec.ecl.knyazev.repository.PassportRepository;

import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
public class PassportRepositoryTest extends TestContainerInitializer {
    private static final String FIND_BY_UUID_QUERY = "SELECT p FROM Passport p WHERE p.uuid = :uuid";
    private static final String FIND_ALL = "SELECT p FROM Passport p";

    private final PassportRepository passportRepository;

    private final TestEntityManager testEntityManager;

    @Test
    public void checkFindByUUIDShouldReturnPassport() {
        UUID passportUUID = UUID.fromString("bb7fa7ee-e31b-4f9d-b9c5-684bdec38954");

        Passport expectedPassport = testEntityManager.getEntityManager()
                .createQuery(FIND_BY_UUID_QUERY, Passport.class)
                .setParameter("uuid", passportUUID)
                .getSingleResult();

        Passport actualPassport = passportRepository.findByUuid(passportUUID).get();

        assertThat(actualPassport).isEqualTo(expectedPassport);
    }

    @Test
    public void checkFindAllShouldReturnPageablePassports() {
        int pageNumber = 0;
        int pageSize = 7;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Passport> expectedPassports = testEntityManager.getEntityManager()
                .createQuery(FIND_ALL, Passport.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Page<Passport> actualPagePassports = passportRepository.findAll(pageable);

        assertThat(actualPagePassports.toList())
                .isEqualTo(expectedPassports);
    }

    @Test
    public void checkDeleteByUuidShouldRemovePassport() {
        UUID deletingPassportUUID = UUID.fromString("33aa9109-cdd9-45a3-a374-66fcbfa082e1");

        passportRepository.deleteByUuid(deletingPassportUUID);

        assertThatExceptionOfType(NoResultException.class)
                .isThrownBy(() -> testEntityManager.getEntityManager()
                        .createQuery(FIND_BY_UUID_QUERY, Passport.class)
                        .setParameter("uuid", deletingPassportUUID)
                        .getSingleResult());
    }
}
