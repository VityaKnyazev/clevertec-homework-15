package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.knyazev.dao.AddressDAO;
import ru.clevertec.ecl.knyazev.dao.exception.DAOException;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.mapper.AddressMapper;
import ru.clevertec.ecl.knyazev.service.AddressService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDAO addressDAOJPAImpl;

    private final AddressMapper addressMapperImpl;

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAddressResponseDTO get(UUID addressUUID) throws ServiceException {
        return addressMapperImpl.toGetAddressResponseDTO(addressDAOJPAImpl.findByUUID(addressUUID)
                .orElseThrow(ServiceException::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GetAddressResponseDTO> getAll(Paging paging, Searching searching) {
        return addressMapperImpl.toGetAddressResponseDTOs(
                addressDAOJPAImpl.findAll(paging, searching));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAddressResponseDTO add(PostPutAddressRequestDTO postPutAddressRequestDTO) {
        return addressMapperImpl.toGetAddressResponseDTO(
                addressDAOJPAImpl.saveOrUpdate(
                        addressMapperImpl.toAddress(postPutAddressRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAddressResponseDTO update(PostPutAddressRequestDTO postPutAddressRequestDTO) {

        UUID addressUUID = UUID.fromString(postPutAddressRequestDTO.uuid());
        Address addressDB = addressDAOJPAImpl.findByUUID(addressUUID)
                .orElseThrow(() -> new ServiceException(String.format("%s. %s",
                        DAOException.SAVING_OR_UPDATING_ERROR,
                        ServiceException.DEFAULT_ERROR_MESSAGE)));

        return addressMapperImpl.toGetAddressResponseDTO(
                addressDAOJPAImpl.saveOrUpdate(
                        addressMapperImpl.toAddress(addressDB, postPutAddressRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(UUID addressUUID) {
        addressDAOJPAImpl.delete(addressUUID);
    }
}
