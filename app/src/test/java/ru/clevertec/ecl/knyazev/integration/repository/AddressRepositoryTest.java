package ru.clevertec.ecl.knyazev.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.domain.searching.impl.SearchingImpl;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.integration.config.TestContainerInitializer;
import ru.clevertec.ecl.knyazev.repository.AddressRepository;

import java.util.UUID;
import java.util.stream.Stream;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
public class AddressRepositoryTest extends TestContainerInitializer {
    private static final String FIND_BY_UUID_QUERY = "SELECT a FROM Address a WHERE a.uuid = :uuid";

    private final AddressRepository addressRepository;

    private final TestEntityManager testEntityManager;

    @Test
    public void checkFindByUUIDShouldReturnAddress() {
        UUID addressUUID = UUID.fromString("b835d0cf-793d-4ef9-aa2d-59bdd82ff6f0");

        Address expectedAddress = testEntityManager.getEntityManager()
                .createQuery(FIND_BY_UUID_QUERY, Address.class)
                .setParameter("uuid", addressUUID)
                .getSingleResult();

        Address actualAddress = addressRepository.findByUuid(addressUUID).get();

        assertThat(actualAddress).isEqualTo(expectedAddress);
    }

    @Test
    public void checkDeleteByUuidShouldRemoveAddress() {
        UUID deletingAddressUUID = UUID.fromString("63efdd52-1e58-4066-bde3-b9507f5499b3");

        addressRepository.deleteByUuid(deletingAddressUUID);

        assertThatExceptionOfType(NoResultException.class)
                .isThrownBy(() -> testEntityManager.getEntityManager()
                        .createQuery(FIND_BY_UUID_QUERY, Address.class)
                        .setParameter("uuid", deletingAddressUUID)
                        .getSingleResult());
    }

    @Test
    public void checkFindAllShouldReturnAllData() {
        int expectedPageAddressesQuantity = 17;

        Pageable inputPageable = Pageable.unpaged();
        Searching inputSearching = new SearchingImpl(null);

        Page<Address> actualPageAddresses = addressRepository.findAll(inputPageable,
                inputSearching);

        assertThat(actualPageAddresses).hasSize(expectedPageAddressesQuantity);

    }

    @ParameterizedTest
    @MethodSource("getDataForFindAll")
    public void checkFindAllShouldReturnDataWithPageableAndSearching(Pageable pageable,
                                                                     Searching searching,
                                                                     Address firstCollectionElement,
                                                                     Integer totalSelectionElementQuantity) {
        Page<Address> actualPageAddresses = addressRepository.findAll(pageable,
                searching);

        assertAll(
                () -> assertThat(actualPageAddresses).hasSize(totalSelectionElementQuantity),
                () -> assertThat(actualPageAddresses.stream().findFirst().get())
                        .isEqualTo(firstCollectionElement)
        );
    }


    /**
     * Get paging, sorting, searching data
     * for testing findAll method
     * Returns stream of arguments that contains
     * paging, sorting, searching data in various variants
     * and
     * expected first address element in outcome selection
     * with
     * expected selected elements quantity
     *
     *
     * @return aging, sorting, searching data and
     * expected first collection element, collection elements size
     * for testing findAll method
     */
    private static Stream<Arguments> getDataForFindAll() {
        String sortingfield = "city";
        Sort.Direction sortDirection = Sort.Direction.ASC;
        String searchingParam = "Sout";
        int pageNumber = 0;
        int pageSize = 15;


        return Stream.of(
                Arguments.of(PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortingfield)),
                        new SearchingImpl(searchingParam),
                        Address.builder()
                                .id(12L)
                                .uuid(UUID.fromString("63efdd52-1e58-4066-bde3-b9507f5499b3"))
                                .area("South")
                                .country("Russia")
                                .city("Bratsk")
                                .street("Lenina")
                                .number(25)
                                .build(),
                        4),

                Arguments.of(PageRequest.of(pageNumber, pageSize),
                        new SearchingImpl(searchingParam),
                        Address.builder()
                                .id(3L)
                                .uuid(UUID.fromString("bfe2092d-1ea8-4420-bf23-568509be03f7"))
                                .area("South")
                                .country("Mexico")
                                .city("Mexico City")
                                .street("Juarez Avenue")
                                .number(789)
                                .build(),
                        4),

                Arguments.of(PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortingfield)),
                        new SearchingImpl(null),
                        Address.builder()
                                .id(13L)
                                .uuid(UUID.fromString("cc54be96-f612-4cae-ab16-9abc36a73746"))
                                .area("East")
                                .country("USA")
                                .city("Boston")
                                .street("Cherchelya")
                                .number(74)
                                .build(),
                        15),

                Arguments.of(Pageable.unpaged(Sort.by(sortDirection, sortingfield)),
                        new SearchingImpl(searchingParam),
                        Address.builder()
                                .id(12L)
                                .uuid(UUID.fromString("63efdd52-1e58-4066-bde3-b9507f5499b3"))
                                .area("South")
                                .country("Russia")
                                .city("Bratsk")
                                .street("Lenina")
                                .number(25)
                                .build(),
                        4),
                Arguments.of(PageRequest.of(pageNumber, pageSize),
                        new SearchingImpl(null),
                        Address.builder()
                                .id(1L)
                                .uuid(UUID.fromString("548f8b02-4f28-49af-864b-b50faa1c1438"))
                                .area("Central")
                                .country("USA")
                                .city("New York")
                                .street("Broadway")
                                .number(123)
                                .build(),
                        15),

                Arguments.of(Pageable.unpaged(Sort.by(sortDirection, sortingfield)),
                        new SearchingImpl(null),
                        Address.builder()
                                .id(13L)
                                .uuid(UUID.fromString("cc54be96-f612-4cae-ab16-9abc36a73746"))
                                .area("East")
                                .country("USA")
                                .city("Boston")
                                .street("Cherchelya")
                                .number(74)
                                .build(),
                        17),

                Arguments.of(Pageable.unpaged(),
                        new SearchingImpl(searchingParam),
                        Address.builder()
                                .id(3L)
                                .uuid(UUID.fromString("bfe2092d-1ea8-4420-bf23-568509be03f7"))
                                .area("South")
                                .country("Mexico")
                                .city("Mexico City")
                                .street("Juarez Avenue")
                                .number(789)
                                .build(),
                        4)
        );
    }
}
