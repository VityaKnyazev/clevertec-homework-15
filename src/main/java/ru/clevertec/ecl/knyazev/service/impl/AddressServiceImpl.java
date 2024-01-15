package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.knyazev.dao.AddressDAO;
import ru.clevertec.ecl.knyazev.data.http.address.request.DeleteAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.mapper.AddressMapper;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.service.AddressService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
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
    public List<GetAddressResponseDTO> getAll(Paging paging) {
        return addressMapperImpl.toGetAddressResponseDTOs(
                paging.usePaging()
                        ? addressDAOJPAImpl.findAll(paging)
                        : addressDAOJPAImpl.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAddressResponseDTO add(PostPutAddressRequestDTO postPutAddressRequestDTO) {
        return addressMapperImpl.toGetAddressResponseDTO(
                addressDAOJPAImpl.save(
                        addressMapperImpl.toAddress(postPutAddressRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetAddressResponseDTO update(PostPutAddressRequestDTO postPutAddressRequestDTO) {
        return addressMapperImpl.toGetAddressResponseDTO(
                addressDAOJPAImpl.update(
                        addressMapperImpl.toAddress(postPutAddressRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(DeleteAddressRequestDTO deleteAddressRequestDTO) {
        addressDAOJPAImpl.delete(
                addressMapperImpl.toAddressUUID(deleteAddressRequestDTO));
    }
}
