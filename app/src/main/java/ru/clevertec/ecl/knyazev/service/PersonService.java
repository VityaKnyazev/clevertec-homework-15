package ru.clevertec.ecl.knyazev.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Represents service for Person data
 */
public interface PersonService {
    /**
     * Get person DTO by person uuid
     *
     * @param personUUID person uuid
     * @return Person data transfer object
     * @throws ServiceException when person not found
     */
    GetPersonResponseDTO get(UUID personUUID) throws ServiceException;

    /**
     * Get all person data transfer objects, also using pagination
     * and sorting
     *
     * @param pageable query object
     * @return all person DTOs by given pageable object or empty list
     */
    List<GetPersonResponseDTO> getAll(Pageable pageable);

    /**
     * Get all house DTOs that person possessing, also using paging
     * and sorting
     *
     * @param personUUID person uuid
     * @param pageable     object data for pagination and sorting
     * @return all houses DTOs that person with given uuid possessing, also with pagination
     * and sorting or empty list
     */
    List<GetHouseResponseDTO> getPossessingHouses(UUID personUUID, Pageable pageable);

    /**
     * Add person using person DTO and get person DTO as
     * result
     *
     * @param postPutPersonRequestDTO person DTO
     * @return added person DTO
     */
    GetPersonResponseDTO add(PostPutPersonRequestDTO postPutPersonRequestDTO);

    /**
     * Update existing person using person DTO and get person DTO as
     * result
     *
     * @param postPutPersonRequestDTO person DTO
     * @return updated person DTO
     */
    GetPersonResponseDTO update(PostPutPersonRequestDTO postPutPersonRequestDTO);

    /**
     * Remove existing person using its UUID
     *
     * @param personUUID person uuid
     */
    void remove(UUID personUUID);
}
