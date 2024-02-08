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
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.integration.config.TestContainerInitializer;
import ru.clevertec.ecl.knyazev.repository.PersonRepository;

import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
public class PersonRepositoryTest extends TestContainerInitializer {
    private static final String FIND_BY_UUID_QUERY = "SELECT p FROM Person p WHERE p.uuid = :uuid";
    private static final String FIND_ALL = "SELECT p FROM Person p";

    private final PersonRepository personRepository;

    private final TestEntityManager testEntityManager;

    @Test
    public void checkFindByUUIDShouldReturnPerson() {
        UUID personUUID = UUID.fromString("285b3607-22be-47b0-8bbc-f1f20ee0c17b");

        Person expectedPerson = testEntityManager.getEntityManager()
                .createQuery(FIND_BY_UUID_QUERY, Person.class)
                .setParameter("uuid", personUUID)
                .getSingleResult();

        Person actualPerson = personRepository.findByUuid(personUUID).get();

        assertThat(actualPerson).isEqualTo(expectedPerson);
    }

    @Test
    public void checkFindAllShouldReturnPageablePersons() {
        int pageNumber = 0;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Person> expectedPersons = testEntityManager.getEntityManager()
                .createQuery(FIND_ALL, Person.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Page<Person> actualPagePersons = personRepository.findAll(pageable);

        assertThat(actualPagePersons.toList())
                .isEqualTo(expectedPersons);
    }

    @Test
    public void checkDeleteByUuidShouldRemovePerson() {
        UUID deletingPersonUUID = UUID.fromString("c31a5eea-41f0-4aed-98be-b49cd8440785");

        personRepository.deleteByUuid(deletingPersonUUID);

        assertThatExceptionOfType(NoResultException.class)
                .isThrownBy(() -> testEntityManager.getEntityManager()
                        .createQuery(FIND_BY_UUID_QUERY, Person.class)
                        .setParameter("uuid", deletingPersonUUID)
                        .getSingleResult());
    }
}
