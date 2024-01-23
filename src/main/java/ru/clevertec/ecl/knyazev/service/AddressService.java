package ru.clevertec.ecl.knyazev.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents service for Address data
 */
public interface AddressService {

    /**
     * Get address by address uuid
     *
     * @param addressUUID address uuid
     * @return address
     * @throws ServiceException when address not found
     */
    Address getAddress(UUID addressUUID);

    /**
     * Get address DTO by address uuid
     *
     * @param addressUUID address uuid
     * @return Address data transfer object
     * @throws ServiceException when address not found
     */
    GetAddressResponseDTO getAddressResponseDTO(UUID addressUUID) throws ServiceException;

    /**
     * Get all addresses, also using pagination, sorting
     * and searching
     *
     * @param pageable query object
     * @param searching searching object
     * @return all addresses by given paging and searching object
     * or empty list
     */
    List<Address> getAllAddresses(Pageable pageable, Searching searching);

    /**
     * Get all address data transfer objects, also using pagination, sorting
     * and searching
     *
     * @param pageable query object
     * @param searching searching object
     * @return all address DTOs by given paging and searching object
     * or empty list
     */
    List<GetAddressResponseDTO> getAllAddressResponseDTO(Pageable pageable, Searching searching);

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
     * Remove existing address using its UUID
     *
     * @param addressUUID address uuid
     */
    void remove(UUID addressUUID);
}
