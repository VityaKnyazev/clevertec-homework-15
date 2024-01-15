package ru.clevertec.ecl.knyazev.service;

import ru.clevertec.ecl.knyazev.data.http.address.request.DeleteAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Represents service for Address data
 */
public interface AddressService {
    /**
     * Get address DTO by address uuid
     *
     * @param addressUUID address uuid
     * @return Address data transfer object
     * @throws ServiceException when address not found
     */
    GetAddressResponseDTO get(UUID addressUUID) throws ServiceException;

    /**
     * Get all address data transfer objects, also using pagination
     *
     * @param paging query object
     * @return all address DTOs by given paging object or empty list
     */
    List<GetAddressResponseDTO> getAll(Paging paging);

    /**
     * Add address using address DTO and get address DTO as
     * result
     *
     * @param postPutAddressRequestDTO address DTO
     * @return added address DTO
     */
    GetAddressResponseDTO add(PostPutAddressRequestDTO postPutAddressRequestDTO);

    /**
     * Update existing address using address DTO and get address DTO as
     * result
     *
     * @param postPutAddressRequestDTO address DTO
     * @return updated address DTO
     */
    GetAddressResponseDTO update(PostPutAddressRequestDTO postPutAddressRequestDTO);

    /**
     * Remove existing address using UUID in address DTO
     *
     * @param deleteAddressRequestDTO address dto with uuid
     */
    void remove(DeleteAddressRequestDTO deleteAddressRequestDTO);
}
