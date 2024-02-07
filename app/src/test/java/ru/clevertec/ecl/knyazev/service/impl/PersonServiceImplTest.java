package ru.clevertec.ecl.knyazev.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Passport;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.mapper.HouseMapper;
import ru.clevertec.ecl.knyazev.mapper.HouseMapperImpl;
import ru.clevertec.ecl.knyazev.mapper.PersonMapper;
import ru.clevertec.ecl.knyazev.mapper.PersonMapperImpl;
import ru.clevertec.ecl.knyazev.repository.PersonRepository;
import ru.clevertec.ecl.knyazev.service.HouseService;
import ru.clevertec.ecl.knyazev.service.PassportService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;
import ru.clevertec.ecl.knyazev.util.HouseTestData;
import ru.clevertec.ecl.knyazev.util.PassportTestData;
import ru.clevertec.ecl.knyazev.util.PersonTestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepositoryMock;

    @Mock
    private PassportService passportServiceImplMock;
    @Mock
    private HouseService houseServiceImplMock;

    @Spy
    private PersonMapper personMapperImpl = new PersonMapperImpl();
    @Spy
    private HouseMapper houseMapperImpl = new HouseMapperImpl();

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @Test
    public void checkGetShouldReturnGetPersonResponseDTOByUUID() {
        Person expectedPerson = PersonTestData.expectedPerson();

        when(personRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedPerson));

        UUID inputUUID = expectedPerson.getUuid();
        GetPersonResponseDTO actualGetPersonResponseDTO = personServiceImpl.get(inputUUID);

        assertAll(
                () -> assertThat(actualGetPersonResponseDTO).isNotNull(),
                () -> assertThat(actualGetPersonResponseDTO.name())
                        .isEqualTo(expectedPerson.getName()),
                () -> assertThat(actualGetPersonResponseDTO.surname())
                        .isEqualTo(expectedPerson.getSurname()),
                () -> assertThat(actualGetPersonResponseDTO.sex())
                        .isEqualTo(expectedPerson.getSex().name()),
                () -> assertThat(actualGetPersonResponseDTO.passportSeries())
                        .isEqualTo(expectedPerson.getPassport().getPassportSeries()),
                () -> assertThat(actualGetPersonResponseDTO.passportNumber())
                        .isEqualTo(expectedPerson.getPassport().getPassportNumber()),
                () -> assertThat(actualGetPersonResponseDTO.createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkGetShouldThrowServiceExceptionWhenPersonNotFound() {
        Optional<Person> expectedWrapPerson = Optional.empty();

        when(personRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(expectedWrapPerson);

        UUID inputUUID = UUID.randomUUID();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> personServiceImpl.get(inputUUID));
    }

    @Test
    public void checkGetAllShouldReturnAllPersonsAlsoWithPaging() {
        Page<Person> expectedPagePersons = PersonTestData.expectedPagePersons();
        int expectedGetPassportResponseDTOsSize = 1;

        when(personRepositoryMock.findAll(any(Pageable.class)))
                .thenReturn(expectedPagePersons);

        Pageable pageableInput = PageRequest.of(PersonTestData.PAGE_NUMBER,
                PersonTestData.PAGE_SIZE);
        List<GetPersonResponseDTO> actualGetPersonResponseDTOs = personServiceImpl.getAll(pageableInput);

        assertAll(
                () -> assertThat(actualGetPersonResponseDTOs).isNotNull()
                        .hasSize(expectedGetPassportResponseDTOsSize),
                () -> assertThat(actualGetPersonResponseDTOs.stream().findFirst().get().name())
                        .isEqualTo(expectedPagePersons.stream().findFirst().get().getName()),
                () -> assertThat(actualGetPersonResponseDTOs.stream().findFirst().get().surname())
                        .isEqualTo(expectedPagePersons.stream().findFirst().get().getSurname()),
                () -> assertThat(actualGetPersonResponseDTOs.stream().findFirst().get().sex())
                        .isEqualTo(expectedPagePersons.stream().findFirst().get().getSex().name()),
                () -> assertThat(actualGetPersonResponseDTOs.stream().findFirst().get().passportSeries())
                        .isEqualTo(expectedPagePersons.stream().findFirst().get().getPassport().getPassportSeries()),
                () -> assertThat(actualGetPersonResponseDTOs.stream().findFirst().get().passportNumber())
                        .isEqualTo(expectedPagePersons.stream().findFirst().get().getPassport().getPassportNumber()),
                () -> assertThat(actualGetPersonResponseDTOs.stream().findFirst().get().createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkGetPossessingHousesShouldReturnHouseResponseDTOs() {
        Person expectedPerson = PersonTestData.expectedPerson();

        when(personRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedPerson));

        Pageable pageableInput = PageRequest.of(PersonTestData.PAGE_NUMBER,
                PersonTestData.PAGE_SIZE);
        UUID inputUUID = expectedPerson.getUuid();

        List<GetHouseResponseDTO> actualHouseResponseDTOs = personServiceImpl.getPossessingHouses(inputUUID,
                pageableInput);

        assertAll(
                () -> assertThat(actualHouseResponseDTOs.stream().findFirst().get().area())
                        .isEqualTo(expectedPerson.getPossessedHouses().stream().findFirst().get()
                                .getAddress().getArea()),
                () -> assertThat(actualHouseResponseDTOs.stream().findFirst().get().country())
                        .isEqualTo(expectedPerson.getPossessedHouses().stream().findFirst().get()
                                .getAddress().getCountry()),
                () -> assertThat(actualHouseResponseDTOs.stream().findFirst().get().city())
                        .isEqualTo(expectedPerson.getPossessedHouses().stream().findFirst().get()
                                .getAddress().getCity()),
                () -> assertThat(actualHouseResponseDTOs.stream().findFirst().get().street())
                        .isEqualTo(expectedPerson.getPossessedHouses().stream().findFirst().get()
                                .getAddress().getStreet()),
                () -> assertThat(actualHouseResponseDTOs.stream().findFirst().get().number())
                        .isEqualTo(expectedPerson.getPossessedHouses().stream().findFirst().get()
                                .getAddress().getNumber()),
                () -> assertThat(actualHouseResponseDTOs.stream().findFirst().get().createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkGetPossessingHousesShouldThrowServiceException() {

        when(personRepositoryMock.findByUuid(any(UUID.class)))
                .thenThrow(ServiceException.class);

        UUID inputUUID = UUID.randomUUID();
        Pageable pageable = Pageable.unpaged();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> personServiceImpl.getPossessingHouses(inputUUID, pageable));
    }

    @Test
    public void checkAddShouldReturnSavedGetPersonResponseDTO() {
        Passport expectedDbPassport = PassportTestData.expectedPassport();
        House expectedDbLivingHouse = HouseTestData.expectedHouse();
        House expectedDbPossessingHouse = HouseTestData.expectedHouse();

        when(passportServiceImplMock.getPassport(any(UUID.class)))
                .thenReturn(expectedDbPassport);
        when(houseServiceImplMock.getHouse(any(UUID.class)))
                .thenReturn(expectedDbLivingHouse)
                .thenReturn(expectedDbPossessingHouse);

        when(personRepositoryMock.save(Mockito.argThat(passport -> passport.getId() == null)))
                .thenAnswer(invocation -> {
                    Person expectedSavedPerson = invocation.getArgument(0);
                    expectedSavedPerson.setId(18L);
                    return expectedSavedPerson;
                });

        PostPutPersonRequestDTO inputPostPersonRequestDTO =
                PersonTestData.inputPostPersonRequestDTO();

        GetPersonResponseDTO actualGetPersonResponseDTO = personServiceImpl.add(inputPostPersonRequestDTO);

        assertAll(
                () -> assertThat(actualGetPersonResponseDTO).isNotNull()
                        .extracting(personDTO -> personDTO.uuid()).isNotNull(),
                () -> assertThat(actualGetPersonResponseDTO.name())
                        .isEqualTo(inputPostPersonRequestDTO.name()),
                () -> assertThat(actualGetPersonResponseDTO.surname())
                        .isEqualTo(inputPostPersonRequestDTO.surname()),
                () -> assertThat(actualGetPersonResponseDTO.sex())
                        .isEqualTo(inputPostPersonRequestDTO.sex()),
                () -> assertThat(actualGetPersonResponseDTO.passportSeries())
                        .isEqualTo(expectedDbPassport.getPassportSeries()),
                () -> assertThat(actualGetPersonResponseDTO.passportNumber())
                        .isEqualTo(expectedDbPassport.getPassportNumber()),
                () -> assertThat(actualGetPersonResponseDTO.createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkUpdateShouldReturnUpdatedGetPersonResponseDTO() {
        Person expectedUpdatingDbPerson = PersonTestData.expectedPerson();

        Passport replaceableDbPassport = PassportTestData.replaceablePassport();
        House replaceableDbLivingHouse = HouseTestData.replaceableHouse();
        House replaceableDbPossessingHouse = HouseTestData.replaceableHouse();

        when(personRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedUpdatingDbPerson));

        when(passportServiceImplMock.getPassport(any(UUID.class)))
                .thenReturn(replaceableDbPassport);
        when(houseServiceImplMock.getHouse(any(UUID.class)))
                .thenReturn(replaceableDbLivingHouse)
                .thenReturn(replaceableDbPossessingHouse);

        when(personRepositoryMock.save(any(Person.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PostPutPersonRequestDTO inputPutPersonRequestDTO =
                PersonTestData.inputPutPersonRequestDTO();

        GetPersonResponseDTO actualGetPersonResponseDTO = personServiceImpl.update(inputPutPersonRequestDTO);

        assertAll(
                () -> assertThat(actualGetPersonResponseDTO).isNotNull(),
                () -> assertThat(actualGetPersonResponseDTO.name())
                        .isEqualTo(inputPutPersonRequestDTO.name()),
                () -> assertThat(actualGetPersonResponseDTO.surname())
                        .isEqualTo(inputPutPersonRequestDTO.surname()),
                () -> assertThat(actualGetPersonResponseDTO.sex())
                        .isEqualTo(inputPutPersonRequestDTO.sex()),
                () -> assertThat(actualGetPersonResponseDTO.passportSeries())
                        .isEqualTo(replaceableDbPassport.getPassportSeries()),
                () -> assertThat(actualGetPersonResponseDTO.passportNumber())
                        .isEqualTo(replaceableDbPassport.getPassportNumber()),
                () -> assertThat(actualGetPersonResponseDTO.createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkUpdateShouldThrowServiceException() {
        Optional<Person> expectedUpdatingDbPerson = Optional.empty();

        when(personRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(expectedUpdatingDbPerson);

        PostPutPersonRequestDTO inputPutPersonRequestDTO =
                PersonTestData.inputPutPersonRequestDTO();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> personServiceImpl.update(inputPutPersonRequestDTO));
    }

    @Test
    public void checkRemoveShouldRemovePassportByUUID() {
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        UUID inputUUID = UUID.randomUUID();

        personServiceImpl.remove(inputUUID);

        verify(personRepositoryMock).deleteByUuid(uuidArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(inputUUID);
    }
}
