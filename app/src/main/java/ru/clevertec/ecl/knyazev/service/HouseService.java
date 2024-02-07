package ru.clevertec.ecl.knyazev.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Represents service for House data
 */
public interface HouseService {
    /**
     * Get house by house uuid
     *
     * @param houseUUID house uuid
     * @return house
     * @throws ServiceException when house not found
     */
    House getHouse(UUID houseUUID) throws ServiceException;

    /**
     * Get house DTO by house uuid
     *
     * @param houseUUID house uuid
     * @return House data transfer object
     * @throws ServiceException when house not found
     */
    GetHouseResponseDTO getHouseResponseDTO(UUID houseUUID) throws ServiceException;

    /**
     * Get all house data transfer objects, also using pagination, sorting
     * and searching
     *
     * @param pageable  query object
     * @param searching query object
     * @return all house DTOs by given paging, sorting and
     * searching object or empty list
     */
    List<GetHouseResponseDTO> getAll(Pageable pageable, Searching searching);

    /**
     * Get all person DTOs that living in house, also using pagination
     * and sorting
     *
     * @param houseUUID house uuid
     * @param pageable    pageable object data for pagination and sorting
     * @return all person DTOs that living in house, also with pagination
     * or empty list
     */
    List<GetPersonResponseDTO> getLivingPersons(UUID houseUUID, Pageable pageable);

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
     * Remove existing house using its UUID
     *
     * @param houseUUID house uuid
     */
    void remove(UUID houseUUID);
}
