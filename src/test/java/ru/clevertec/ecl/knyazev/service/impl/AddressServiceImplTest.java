package ru.clevertec.ecl.knyazev.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
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
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.domain.searching.impl.SearchingImpl;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.mapper.AddressMapper;
import ru.clevertec.ecl.knyazev.mapper.AddressMapperImpl;
import ru.clevertec.ecl.knyazev.repository.AddressRepository;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;
import ru.clevertec.ecl.knyazev.service.impl.AddressServiceImpl;
import ru.clevertec.ecl.knyazev.util.AddressServiceImplTestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {
    @Mock
    private AddressRepository addressRepositoryMock;

    @Spy
    private AddressMapper addressMapperImpl = new AddressMapperImpl();

    @InjectMocks
    private AddressServiceImpl addressServiceImpl;

    @Test
    public void checkGetAddressShouldReturnAddressByUUID() {
        Address expectedAddress = AddressServiceImplTestData.expectedAddress();

        when(addressRepositoryMock.findByUuid(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(expectedAddress));

        UUID inputUUID = expectedAddress.getUuid();
        Address actualAddress = addressServiceImpl.getAddress(inputUUID);

        assertThat(actualAddress).isNotNull()
                .isEqualTo(expectedAddress);
    }

    @Test
    public void checkGetAddressShouldThrowServiceExceptionWhenAddressNotFound() {
        Optional<Address> expectedWrapAddress = Optional.empty();

        when(addressRepositoryMock.findByUuid(Mockito.any(UUID.class)))
                .thenReturn(expectedWrapAddress);

        UUID inputUUID = UUID.randomUUID();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> addressServiceImpl.getAddress(inputUUID));
    }

    @Test
    public void checkGetAddressResponseDTOShouldReturnAddressResponseDTO() {
        Address expectedAddress = AddressServiceImplTestData.expectedAddress();

        when(addressRepositoryMock.findByUuid(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(expectedAddress));

        UUID inputUUID = expectedAddress.getUuid();
        GetAddressResponseDTO actualGetAddressResponseDTO = addressServiceImpl.getAddressResponseDTO(inputUUID);

        assertAll(
                () -> assertThat(actualGetAddressResponseDTO.area())
                        .isEqualTo(expectedAddress.getArea()),
                () -> assertThat(actualGetAddressResponseDTO.country())
                        .isEqualTo(expectedAddress.getCountry()),
                () -> assertThat(actualGetAddressResponseDTO.city())
                        .isEqualTo(expectedAddress.getCity()),
                () -> assertThat(actualGetAddressResponseDTO.street())
                        .isEqualTo(expectedAddress.getStreet()),
                () -> assertThat(actualGetAddressResponseDTO.number())
                        .isEqualTo(expectedAddress.getNumber().toString())
        );
    }

    @Test
    public void checkGetAddressResponseDTOShouldThrowServiceExceptionWhenAddressNotFound() {
        Optional<Address> expectedWrapAddress = Optional.empty();

        when(addressRepositoryMock.findByUuid(Mockito.any(UUID.class)))
                .thenReturn(expectedWrapAddress);

        UUID inputUUID = UUID.randomUUID();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> addressServiceImpl.getAddressResponseDTO(inputUUID));
    }

    @Test
    public void checkGetAllAddressesShouldReturnAllAddressesAlsoWithPagingAndSearching() {
        Page<Address> expectedAddresses = AddressServiceImplTestData.expectedPageAddresses();

        when(addressRepositoryMock.findAll(Mockito.any(Pageable.class), Mockito.any(Searching.class)))
                .thenReturn(expectedAddresses);

        Searching searchingInput = new SearchingImpl(AddressServiceImplTestData.SEARCHING_PARAM);
        Pageable pageableInput = PageRequest.of(AddressServiceImplTestData.PAGE_NUMBER,
                AddressServiceImplTestData.PAGE_SIZE);
        List<Address> actualAddresses = addressServiceImpl.getAllAddresses(pageableInput, searchingInput);

        assertThat(actualAddresses).isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedAddresses.getContent());
    }

    @Test
    public void checkGetAllAddressResponseDTOShouldReturnAllAddressResponseDTOsAlsoWithPagingAndSorting() {
        Page<Address> expectedAddresses = AddressServiceImplTestData.expectedPageAddresses();
        int expectedGetAddressResponseDTOSize = 1;

        when(addressRepositoryMock.findAll(Mockito.any(Pageable.class), Mockito.any(Searching.class)))
                .thenReturn(expectedAddresses);

        Searching searchingInput = new SearchingImpl(AddressServiceImplTestData.SEARCHING_PARAM);
        Pageable pageableInput = PageRequest.of(AddressServiceImplTestData.PAGE_NUMBER,
                AddressServiceImplTestData.PAGE_SIZE);
        List<GetAddressResponseDTO> actualGetAddressResponseDTOs =
                addressServiceImpl.getAllAddressResponseDTO(pageableInput, searchingInput);

        assertAll(
                () -> assertThat(actualGetAddressResponseDTOs).isNotNull()
                        .hasSize(expectedGetAddressResponseDTOSize),
                () -> assertThat(actualGetAddressResponseDTOs.stream().findFirst().get().area())
                        .isEqualTo(expectedAddresses.getContent().stream().findFirst().get().getArea()),
                () -> assertThat(actualGetAddressResponseDTOs.stream().findFirst().get().country())
                        .isEqualTo(expectedAddresses.getContent().stream().findFirst().get().getCountry()),
                () -> assertThat(actualGetAddressResponseDTOs.stream().findFirst().get().city())
                        .isEqualTo(expectedAddresses.getContent().stream().findFirst().get().getCity()),
                () -> assertThat(actualGetAddressResponseDTOs.stream().findFirst().get().street())
                        .isEqualTo(expectedAddresses.getContent().stream().findFirst().get().getStreet()),
                () -> assertThat(actualGetAddressResponseDTOs.stream().findFirst().get().number())
                        .isEqualTo(expectedAddresses.getContent().stream().findFirst().get().getNumber().toString())
        );

    }

    @Test
    public void checkAddShouldReturnSavedGetAddressResponseDTO() {

        when(addressRepositoryMock.save(Mockito.argThat(address -> address.getId() == null)))
                .thenAnswer(invocation -> {
                    Address expectedSavedAddress = invocation.getArgument(0);
                    expectedSavedAddress.setId(10L);
                    return expectedSavedAddress;
                });

        PostPutAddressRequestDTO inputPostPutAddressRequestDTO =
                AddressServiceImplTestData.inputPostAddressRequestDTO();

        GetAddressResponseDTO actualGetAddressResponseDTO = addressServiceImpl.add(inputPostPutAddressRequestDTO);

        assertAll(
                () -> assertThat(actualGetAddressResponseDTO).isNotNull()
                        .extracting(addressDTO -> addressDTO.uuid()).isNotNull(),
                () -> assertThat(actualGetAddressResponseDTO.area())
                        .isEqualTo(inputPostPutAddressRequestDTO.area()),
                () -> assertThat(actualGetAddressResponseDTO.country())
                        .isEqualTo(inputPostPutAddressRequestDTO.country()),
                () -> assertThat(actualGetAddressResponseDTO.city())
                        .isEqualTo(inputPostPutAddressRequestDTO.city()),
                () -> assertThat(actualGetAddressResponseDTO.street())
                        .isEqualTo(inputPostPutAddressRequestDTO.street()),
                () -> assertThat(actualGetAddressResponseDTO.number())
                        .isEqualTo(inputPostPutAddressRequestDTO.number())
        );
    }

    @Test
    public void checkUpdateShouldReturnUpdatedGetAddressResponseDTO() {
        Address expectedUpdatingDbAddress = AddressServiceImplTestData.expectedAddress();

        when(addressRepositoryMock.findByUuid(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(expectedUpdatingDbAddress));

        when(addressRepositoryMock.save(Mockito.any(Address.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PostPutAddressRequestDTO inputPostPutAddressRequestDTO =
                AddressServiceImplTestData.inputPutAddressRequestDTO();

        GetAddressResponseDTO actualGetAddressResponseDTO = addressServiceImpl.update(inputPostPutAddressRequestDTO);

        assertAll(
                () -> assertThat(actualGetAddressResponseDTO.area())
                        .isEqualTo(inputPostPutAddressRequestDTO.area()),
                () -> assertThat(actualGetAddressResponseDTO.country())
                        .isEqualTo(inputPostPutAddressRequestDTO.country()),
                () -> assertThat(actualGetAddressResponseDTO.city())
                        .isEqualTo(inputPostPutAddressRequestDTO.city()),
                () -> assertThat(actualGetAddressResponseDTO.street())
                        .isEqualTo(inputPostPutAddressRequestDTO.street()),
                () -> assertThat(actualGetAddressResponseDTO.number())
                        .isEqualTo(inputPostPutAddressRequestDTO.number())
        );
    }

    @Test
    public void checkUpdateShouldThrowServiceException() {
        Optional<Address> expectedUpdatingDbAddress = Optional.empty();

        when(addressRepositoryMock.findByUuid(Mockito.any(UUID.class)))
                .thenReturn(expectedUpdatingDbAddress);

        PostPutAddressRequestDTO inputPostPutAddressRequestDTO =
                AddressServiceImplTestData.inputPutAddressRequestDTO();

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> addressServiceImpl.update(inputPostPutAddressRequestDTO));
    }

    @Test
    public void checkRemoveShouldRemoveAddressByUUID() {
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        
        doNothing().
                when(addressRepositoryMock).deleteByUuid(uuidArgumentCaptor.capture());

        UUID inputUUID = UUID.randomUUID();

        addressServiceImpl.remove(inputUUID);

        verify(addressRepositoryMock).deleteByUuid(uuidArgumentCaptor.getValue());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(inputUUID);
    }
}
