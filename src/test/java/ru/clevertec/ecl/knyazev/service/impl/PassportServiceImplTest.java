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
import ru.clevertec.ecl.knyazev.data.http.passport.request.PostPutPassportRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.passport.response.GetPassportResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Passport;
import ru.clevertec.ecl.knyazev.mapper.PassportMapper;
import ru.clevertec.ecl.knyazev.mapper.PassportMapperImpl;
import ru.clevertec.ecl.knyazev.repository.PassportRepository;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;
import ru.clevertec.ecl.knyazev.service.impl.PassportServiceImpl;
import ru.clevertec.ecl.knyazev.util.HouseServiceImplTestData;
import ru.clevertec.ecl.knyazev.util.PassportServiceImplTestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PassportServiceImplTest {
    @Mock
    private PassportRepository passportRepositoryMock;

    @Spy
    private PassportMapper passportMapperImpl = new PassportMapperImpl();

    @InjectMocks
    private PassportServiceImpl passportServiceImpl;

    @Test
    public void checkGetPassportShouldReturnPassportByUUID() {
        Passport expectedPassport = PassportServiceImplTestData.expectedPassport();

        when(passportRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedPassport));

        UUID inputUUID = expectedPassport.getUuid();
        Passport actualPassport = passportServiceImpl.getPassport(inputUUID);

        assertThat(actualPassport).isNotNull()
                .isEqualTo(expectedPassport);
    }

    @Test
    public void checkGetPassportShouldThrowServiceExceptionWhenPassportNotFound() {
        Optional<Passport> expectedWrapPassport = Optional.empty();

        when(passportRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(expectedWrapPassport);

        UUID inputUUID = UUID.randomUUID();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> passportServiceImpl.getPassport(inputUUID));
    }

    @Test
    public void checkGetPassportResponseDTOShouldReturnPassportResponseDTO() {
        Passport expectedPassport = PassportServiceImplTestData.expectedPassport();

        when(passportRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedPassport));

        UUID inputUUID = expectedPassport.getUuid();
        GetPassportResponseDTO actualGetPassportResponseDTO = passportServiceImpl.getPassportResponseDTO(inputUUID);

        assertAll(
                () -> assertThat(actualGetPassportResponseDTO.passportSeries())
                        .isEqualTo(expectedPassport.getPassportSeries()),
                () -> assertThat(actualGetPassportResponseDTO.passportNumber())
                        .isEqualTo(expectedPassport.getPassportNumber()),
                () -> assertThat(actualGetPassportResponseDTO.createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkGetPassportResponseDTOShouldThrowServiceExceptionWhenPassportNotFound() {
        Optional<Passport> expectedWrapPassport = Optional.empty();

        when(passportRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(expectedWrapPassport);

        UUID inputUUID = UUID.randomUUID();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> passportServiceImpl.getPassportResponseDTO(inputUUID));
    }

    @Test
    public void checkGetAllShouldReturnAllPassportsAlsoWithPaging() {
        Page<Passport> expectedPagePassports = PassportServiceImplTestData.expectedPagePassports();
        int expectedGetPassportResponseDTOsSize = 1;

        when(passportRepositoryMock.findAll(any(Pageable.class)))
                .thenReturn(expectedPagePassports);

        Pageable pageableInput = PageRequest.of(HouseServiceImplTestData.PAGE_NUMBER,
                HouseServiceImplTestData.PAGE_SIZE);
        List<GetPassportResponseDTO> actualGetPassportResponseDTOs = passportServiceImpl.getAll(pageableInput);

        assertAll(
                () -> assertThat(actualGetPassportResponseDTOs).isNotNull()
                        .hasSize(expectedGetPassportResponseDTOsSize),
                () -> assertThat(actualGetPassportResponseDTOs.stream().findFirst().get().passportSeries())
                        .isEqualTo(expectedPagePassports.stream().findFirst().get().getPassportSeries()),
                () -> assertThat(actualGetPassportResponseDTOs.stream().findFirst().get().passportNumber())
                        .isEqualTo(expectedPagePassports.stream().findFirst().get().getPassportNumber()),
                () -> assertThat(actualGetPassportResponseDTOs.stream().findFirst().get().createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkAddShouldReturnSavedGetPassportResponseDTO() {

        when(passportRepositoryMock.save(Mockito.argThat(passport -> passport.getId() == null)))
                .thenAnswer(invocation -> {
                    Passport expectedSavedPassport = invocation.getArgument(0);
                    expectedSavedPassport.setId(10L);
                    return expectedSavedPassport;
                });

        PostPutPassportRequestDTO inputPostPassportRequestDTO =
                PassportServiceImplTestData.inputPostPassportRequestDTO();

        GetPassportResponseDTO actualGetPassportResponseDTO = passportServiceImpl.add(inputPostPassportRequestDTO);

        assertAll(
                () -> assertThat(actualGetPassportResponseDTO).isNotNull()
                        .extracting(passportDTO -> passportDTO.uuid()).isNotNull(),
                () -> assertThat(actualGetPassportResponseDTO.passportSeries())
                        .isEqualTo(inputPostPassportRequestDTO.passportSeries()),
                () -> assertThat(actualGetPassportResponseDTO.passportNumber())
                        .isEqualTo(inputPostPassportRequestDTO.passportNumber()),
                () -> assertThat(actualGetPassportResponseDTO.createDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkUpdateShouldReturnUpdatedGetPassportResponseDTO() {
        Passport expectedUpdatingDbPassport = PassportServiceImplTestData.expectedPassport();

        when(passportRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(expectedUpdatingDbPassport));

        when(passportRepositoryMock.save(any(Passport.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PostPutPassportRequestDTO inputPutPassportRequestDTO =
                PassportServiceImplTestData.inputPutPassportRequestDTO();

        GetPassportResponseDTO actualGetPassportResponseDTO = passportServiceImpl.update(inputPutPassportRequestDTO);

        assertAll(
                () -> assertThat(actualGetPassportResponseDTO).isNotNull(),
                () -> assertThat(actualGetPassportResponseDTO.passportSeries())
                        .isEqualTo(inputPutPassportRequestDTO.passportSeries()),
                () -> assertThat(actualGetPassportResponseDTO.passportNumber())
                        .isEqualTo(inputPutPassportRequestDTO.passportNumber()),
                () -> assertThat(actualGetPassportResponseDTO.createDate())
                        .isNotNull(),
                () -> assertThat(actualGetPassportResponseDTO.updateDate())
                        .isNotNull()
        );
    }

    @Test
    public void checkUpdateShouldThrowServiceException() {
        Optional<Passport> expectedUpdatingDbPassport = Optional.empty();

        when(passportRepositoryMock.findByUuid(any(UUID.class)))
                .thenReturn(expectedUpdatingDbPassport);

        PostPutPassportRequestDTO inputPutPassportRequestDTO =
                PassportServiceImplTestData.inputPutPassportRequestDTO();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> passportServiceImpl.update(inputPutPassportRequestDTO));
    }

    @Test
    public void checkRemoveShouldRemovePassportByUUID() {
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        doNothing().
                when(passportRepositoryMock).deleteByUuid(uuidArgumentCaptor.capture());

        UUID inputUUID = UUID.randomUUID();

        passportServiceImpl.remove(inputUUID);

        verify(passportRepositoryMock).deleteByUuid(uuidArgumentCaptor.getValue());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(inputUUID);
    }
}
