package ru.clevertec.ecl.knyazev.service;

import org.hibernate.service.spi.ServiceException;
import ru.clevertec.ecl.knyazev.data.http.house.request.DeleteHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;

import java.util.List;
import java.util.UUID;

/**
 * Represents service for House data
 */
public interface HouseService {
    /**
     * Get house DTO by house uuid
     *
     * @param houseUUID house uuid
     * @return House data transfer object
     * @throws ServiceException when house not found
     */
    GetHouseResponseDTO get(UUID houseUUID) throws ServiceException;

    /**
     * Get all house data transfer objects, also using pagination
     *
     * @param paging query object
     * @return all house DTOs by given paging object or empty list
     */
    List<GetHouseResponseDTO> getAll(Paging paging);

    /**
     * Get all person DTOs that living in house, also using pagination
     *
     * @param houseUUID house uuid
     * @param paging    paging object data for pagination
     * @return all person DTOs that living in house, also with pagination
     * or empty list
     */
    List<GetPersonResponseDTO> getLivingPersons(UUID houseUUID, Paging paging);

    /**
     * Add house using house DTO and get house DTO as
     * result
     *
     * @param postPutHouseRequestDTO house DTO
     * @return added house DTO
     */
    GetHouseResponseDTO add(PostPutHouseRequestDTO postPutHouseRequestDTO);

    /**
     * Update existing house using house DTO and get house DTO as
     * result
     *
     * @param postPutHouseRequestDTO house DTO
     * @return updated house DTO
     */
    GetHouseResponseDTO update(PostPutHouseRequestDTO postPutHouseRequestDTO);

    /**
     * Remove existing house using UUID in house DTO
     *
     * @param deleteAddressRequestDTO house dto with uuid
     */
    void remove(DeleteHouseRequestDTO deleteAddressRequestDTO);
}
