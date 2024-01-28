package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.mapper.AddressMapper;
import ru.clevertec.ecl.knyazev.repository.AddressRepository;
import ru.clevertec.ecl.knyazev.repository.exception.RepositoryException;
import ru.clevertec.ecl.knyazev.service.AddressService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapperImpl;

    /**
     * {@inheritDoc}
     */
    @Override
    public Address getAddress(UUID addressUUID) throws ServiceException {
        return addressRepository.findByUuid(addressUUID)
                .orElseThrow(ServiceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAddressResponseDTO getAddressResponseDTO(UUID addressUUID) throws ServiceException {
        return addressMapperImpl.toGetAddressResponseDTO(addressRepository.findByUuid(addressUUID)
                .orElseThrow(ServiceException::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Address> getAllAddresses(Pageable pageable, Searching searching) {
        return addressRepository.findAll(pageable, searching).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GetAddressResponseDTO> getAllAddressResponseDTO(Pageable pageable, Searching searching) {
        return addressMapperImpl.toGetAddressResponseDTOs(
                addressRepository.findAll(pageable, searching).toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAddressResponseDTO add(PostPutAddressRequestDTO postPutAddressRequestDTO) {
        return addressMapperImpl.toGetAddressResponseDTO(
                addressRepository.save(
                        addressMapperImpl.toAddress(postPutAddressRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAddressResponseDTO update(PostPutAddressRequestDTO postPutAddressRequestDTO) {

        UUID addressUUID = UUID.fromString(postPutAddressRequestDTO.uuid());
        Address addressDB = addressRepository.findByUuid(addressUUID)
                .orElseThrow(() -> new ServiceException(String.format("%s. %s",
                        RepositoryException.SAVING_OR_UPDATING_ERROR,
                        ServiceException.DEFAULT_ERROR_MESSAGE)));

        return addressMapperImpl.toGetAddressResponseDTO(
                addressRepository.save(
                        addressMapperImpl.toAddress(addressDB, postPutAddressRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(UUID addressUUID) {
        addressRepository.deleteByUuid(addressUUID);
    }
}
