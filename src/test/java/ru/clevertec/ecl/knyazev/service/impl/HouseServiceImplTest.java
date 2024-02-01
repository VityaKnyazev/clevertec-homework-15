package ru.clevertec.ecl.knyazev.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.domain.searching.impl.SearchingImpl;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.mapper.HouseMapper;
import ru.clevertec.ecl.knyazev.mapper.HouseMapperImpl;
import ru.clevertec.ecl.knyazev.mapper.PersonMapper;
import ru.clevertec.ecl.knyazev.mapper.PersonMapperImpl;
import ru.clevertec.ecl.knyazev.repository.HouseRepository;
import ru.clevertec.ecl.knyazev.service.AddressService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;
import ru.clevertec.ecl.knyazev.util.AddressTestData;
import ru.clevertec.ecl.knyazev.util.HouseTestData;
import ru.clevertec.ecl.knyazev.util.PersonTestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class HouseServiceImplTest {
    @Mock
    private HouseRepository houseRepositoryMock;

    @Mock
    AddressService addressServiceMock;

    @Spy
    private HouseMapper houseMapperImpl = new HouseMapperImpl();
    @Spy
    private PersonMapper personMapperImpl = new PersonMapperImpl();

    @InjectMocks
    private HouseServiceImpl houseServiceImpl;

    @Test
    public void checkGetHouseShouldReturnHouseByUUID() {
        House expectedHouse = HouseTestData.expectedHouse();


        when(houseRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedHouse));

        UUID inputUUID = expectedHouse.getUuid();
        House actualHouse = houseServiceImpl.getHouse(inputUUID);

        assertThat(actualHouse).isNotNull()
                .isEqualTo(expectedHouse);
    }

    @Test
    public void checkGetHouseShouldThrowServiceExceptionWhenHouseNotFound() {
        Optional<House> expectedWrapHouse = Optional.empty();

        when(houseRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(expectedWrapHouse);

        UUID inputUUID = UUID.randomUUID();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> houseServiceImpl.getHouse(inputUUID));
    }

    @Test
    public void checkGetHouseResponseDTOShouldReturnHouseResponseDTO() {
        House expectedHouse = HouseTestData.expectedHouse();

        when(houseRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedHouse));

        UUID inputUUID = expectedHouse.getUuid();
        GetHouseResponseDTO actualGetHouseResponseDTO = houseServiceImpl.getHouseResponseDTO(inputUUID);

        assertAll(
                () -> assertThat(actualGetHouseResponseDTO.area())
                        .isEqualTo(expectedHouse.getAddress().getArea()),
                () -> assertThat(actualGetHouseResponseDTO.country())
                        .isEqualTo(expectedHouse.getAddress().getCountry()),
                () -> assertThat(actualGetHouseResponseDTO.city())
                        .isEqualTo(expectedHouse.getAddress().getCity()),
                () -> assertThat(actualGetHouseResponseDTO.street())
                        .isEqualTo(expectedHouse.getAddress().getStreet()),
                () -> assertThat(actualGetHouseResponseDTO.number())
                        .isEqualTo(expectedHouse.getAddress().getNumber()),
                () -> assertThat(actualGetHouseResponseDTO.createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkGetHouseResponseDTOShouldThrowServiceExceptionWhenHouseNotFound() {
        Optional<House> expectedWrapHouse = Optional.empty();

        when(houseRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(expectedWrapHouse);

        UUID inputUUID = UUID.randomUUID();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> houseServiceImpl.getHouseResponseDTO(inputUUID));
    }

    @ParameterizedTest
    @MethodSource("getDataForGetAllWithSortingAndSearching")
    public void checkGetAllShouldReturnAllHousesWithSortingAndSearching(Pageable pageable,
                                                                        Searching searching) {
        List<Address> expectedAddresses = AddressTestData.expectedPageAddresses().toList();
        int expectedGetHouseResponseDTOsSize = 1;

        when(addressServiceMock.getAllAddresses(any(Pageable.class),
                any(Searching.class)))
                .thenReturn(expectedAddresses);

        List<GetHouseResponseDTO> actualGetHouseResponseDTOS = houseServiceImpl.getAll(pageable, searching);

        assertThat(actualGetHouseResponseDTOS).isNotNull()
                .hasSize(expectedGetHouseResponseDTOsSize);
    }

    @ParameterizedTest
    @MethodSource("getDataForGetAllWithPaging")
    public void checkGetAllShouldReturnAllHousesWithPaging(Pageable pageable,
                                                           Searching searching) {
        Page<House> expectedPageHouses = HouseTestData.expectedPageHouses();
        int expectedGetHouseResponseDTOsSize = 1;

        when(houseRepositoryMock.findAll(any(Pageable.class)))
                .thenReturn(expectedPageHouses);

        List<GetHouseResponseDTO> actualGetHouseResponseDTOs = houseServiceImpl.getAll(pageable, searching);

        assertThat(actualGetHouseResponseDTOs).isNotNull()
                .hasSize(expectedGetHouseResponseDTOsSize);
    }

    @Test
    public void checkGetLivingPersonsShouldReturnPersonResponseDTOs() {
        House expectedHouse = HouseTestData.expectedHouse();
        expectedHouse.setLivingPersons(List.of(PersonTestData.expectedPerson()));

        int expectedPersonResponseDTOsSize = 1;

        when(houseRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedHouse));

        Pageable pageable = PageRequest.of(HouseTestData.PAGE_NUMBER, HouseTestData.PAGE_SIZE);
        UUID inputHouseIUUID = expectedHouse.getUuid();

        List<GetPersonResponseDTO> personResponseDTOs = houseServiceImpl
                .getLivingPersons(inputHouseIUUID, pageable);

        assertThat(personResponseDTOs).isNotNull()
                .hasSize(expectedPersonResponseDTOsSize);
    }

    @Test
    public void checkGetLivingPersonsShouldThrowServiceException() {

        when(houseRepositoryMock.findByUuid(any(UUID.class)))
                .thenThrow(ServiceException.class);

        UUID inputHouseUUID = UUID.randomUUID();
        Pageable pageable = Pageable.unpaged();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> houseServiceImpl.getLivingPersons(inputHouseUUID, pageable));
    }

    @Test
    public void checkAddShouldReturnSavedGetHouseResponseDTO() {
        Address expectedDbAddress = AddressTestData.expectedAddress();

        when(addressServiceMock.getAddress(any(UUID.class)))
                .thenReturn(expectedDbAddress);

        when(houseRepositoryMock.save(Mockito.argThat(house -> house.getId() == null)))
                .thenAnswer(invocation -> {
                    House expectedSavedHouse = invocation.getArgument(0);
                    expectedSavedHouse.setId(12L);
                    return expectedSavedHouse;
                });

        PostPutHouseRequestDTO inputPostPutHouseRequestDTO = PostPutHouseRequestDTO.builder()
                .addressUUID(expectedDbAddress.getUuid().toString())
                .build();

        GetHouseResponseDTO actualGetHouseResponseDTO = houseServiceImpl.add(inputPostPutHouseRequestDTO);

        assertAll(
                () -> assertThat(actualGetHouseResponseDTO).isNotNull()
                        .extracting(houseDTO -> houseDTO.uuid()).isNotNull(),
                () -> assertThat(actualGetHouseResponseDTO.area())
                        .isEqualTo(expectedDbAddress.getArea()),
                () -> assertThat(actualGetHouseResponseDTO.country())
                        .isEqualTo(expectedDbAddress.getCountry()),
                () -> assertThat(actualGetHouseResponseDTO.city())
                        .isEqualTo(expectedDbAddress.getCity()),
                () -> assertThat(actualGetHouseResponseDTO.street())
                        .isEqualTo(expectedDbAddress.getStreet()),
                () -> assertThat(actualGetHouseResponseDTO.number())
                        .isEqualTo(expectedDbAddress.getNumber()),
                () -> assertThat(actualGetHouseResponseDTO.createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkUpdateShouldReturnUpdatedGetHouseResponseDTO() {

        House expectedDbHouse = HouseTestData.expectedHouse();

        Address replaceableAddress = AddressTestData.replaceableAddress();

        when(houseRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedDbHouse));

        when(addressServiceMock.getAddress(any(UUID.class)))
                .thenReturn(replaceableAddress);

        when(houseRepositoryMock.save(any(House.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PostPutHouseRequestDTO inputPostPutHouseRequestDTO = PostPutHouseRequestDTO.builder()
                .uuid(expectedDbHouse.getUuid().toString())
                .addressUUID(replaceableAddress.getUuid().toString())
                .build();

        GetHouseResponseDTO actualGetHouseResponseDTO = houseServiceImpl.update(inputPostPutHouseRequestDTO);

        assertAll(
                () -> assertThat(actualGetHouseResponseDTO.area())
                        .isEqualTo(replaceableAddress.getArea()),
                () -> assertThat(actualGetHouseResponseDTO.country())
                        .isEqualTo(replaceableAddress.getCountry()),
                () -> assertThat(actualGetHouseResponseDTO.city())
                        .isEqualTo(replaceableAddress.getCity()),
                () -> assertThat(actualGetHouseResponseDTO.street())
                        .isEqualTo(replaceableAddress.getStreet()),
                () -> assertThat(actualGetHouseResponseDTO.number())
                        .isEqualTo(replaceableAddress.getNumber()),
                () -> assertThat(actualGetHouseResponseDTO.createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkUpdateShouldThrowServiceExceptionWhenHouseNotFound() {
        Optional<House> expectedDbHouseWrap = Optional.empty();

        when(houseRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(expectedDbHouseWrap);

        PostPutHouseRequestDTO inputPostPutHouseRequestDTO = PostPutHouseRequestDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .addressUUID(UUID.randomUUID().toString())
                .build();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> houseServiceImpl.update(inputPostPutHouseRequestDTO));
    }

    @Test
    public void checkRemoveShouldRemoveHouseByUUID() {
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        UUID inputUUID = UUID.randomUUID();

        houseServiceImpl.remove(inputUUID);

        verify(houseRepositoryMock).deleteByUuid(uuidArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(inputUUID);
    }

    private static Stream<Arguments> getDataForGetAllWithSortingAndSearching() {
        return Stream.of(
                Arguments.of(Pageable.unpaged(Sort.by(new ArrayList<>() {{
                            add(new Sort.Order(Sort.Direction.ASC, "city"));
                        }})),
                        new SearchingImpl("West")),
                Arguments.of(Pageable.unpaged(), new SearchingImpl("West")),
                Arguments.of(Pageable.unpaged(Sort.by(new ArrayList<>() {{
                            add(new Sort.Order(Sort.Direction.ASC, "city"));
                        }})),
                        new SearchingImpl(null))
        );
    }

    private static Stream<Arguments> getDataForGetAllWithPaging() {
        return Stream.of(
                Arguments.of(Pageable.unpaged(), new SearchingImpl(null)),
                Arguments.of(PageRequest.of(HouseTestData.PAGE_NUMBER, HouseTestData.PAGE_SIZE, Sort.unsorted()),
                        new SearchingImpl(null))
        );
    }
}
