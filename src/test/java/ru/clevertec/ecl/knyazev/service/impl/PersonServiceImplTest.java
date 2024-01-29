package ru.clevertec.ecl.knyazev.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import ru.clevertec.ecl.knyazev.service.impl.PersonServiceImpl;
import ru.clevertec.ecl.knyazev.util.HouseServiceImplTestData;
import ru.clevertec.ecl.knyazev.util.PassportServiceImplTestData;
import ru.clevertec.ecl.knyazev.util.PersonServiceImplTestData;

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
        Person expectedPerson = PersonServiceImplTestData.expectedPerson();

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
        Page<Person> expectedPagePersons = PersonServiceImplTestData.expectedPagePersons();
        int expectedGetPassportResponseDTOsSize = 1;

        when(personRepositoryMock.findAll(any(Pageable.class)))
                .thenReturn(expectedPagePersons);

        Pageable pageableInput = PageRequest.of(PersonServiceImplTestData.PAGE_NUMBER,
                PersonServiceImplTestData.PAGE_SIZE);
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
    public void checkAddShouldReturnSavedGetPersonResponseDTO() {
        Passport expectedDbPassport = PassportServiceImplTestData.expectedPassport();
        House expectedDbLivingHouse = HouseServiceImplTestData.expectedHouse();
        House expectedDbPossessingHouse = HouseServiceImplTestData.expectedHouse();

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
                PersonServiceImplTestData.inputPostPersonRequestDTO();

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
        Person expectedUpdatingDbPerson = PersonServiceImplTestData.expectedPerson();

        Passport replaceableDbPassport = PassportServiceImplTestData.replaceablePassport();
        House replaceableDbLivingHouse = HouseServiceImplTestData.replaceableHouse();
        House replaceableDbPossessingHouse = HouseServiceImplTestData.replaceableHouse();

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
                PersonServiceImplTestData.inputPutPersonRequestDTO();

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
                PersonServiceImplTestData.inputPutPersonRequestDTO();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> personServiceImpl.update(inputPutPersonRequestDTO));
    }

    @Test
    public void checkRemoveShouldRemovePassportByUUID() {
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        doNothing().
                when(personRepositoryMock).deleteByUuid(uuidArgumentCaptor.capture());

        UUID inputUUID = UUID.randomUUID();

        personServiceImpl.remove(inputUUID);

        verify(personRepositoryMock).deleteByUuid(uuidArgumentCaptor.getValue());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(inputUUID);
    }
}
