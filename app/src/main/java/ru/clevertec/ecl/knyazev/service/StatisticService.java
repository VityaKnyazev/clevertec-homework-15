package ru.clevertec.ecl.knyazev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;

import java.util.UUID;

public interface StatisticService {

    /**
     *
     * Get all persons that ever live in house with given uuid
     *
     * @param houseUUID house uuid
     * @param pageable pageable object
     * @return page persons that ever live in house with given uuid
     */
    Page<GetPersonResponseDTO> getAllEverLivingPersonsInHouse(UUID houseUUID, Pageable pageable);

    /**
     *
     * Get all persons that ever possessed house with given uuid
     *
     * @param houseUUID house uuid
     * @param pageable pageable object
     * @return page persons that ever possessed house with given uuid
     */
    Page<GetPersonResponseDTO> getAllEverHousePossessingPersons(UUID houseUUID, Pageable pageable);

    /**
     *
     * Get all houses that person with uuid ever live
     *
     * @param personUUID person uuid
     * @param pageable pageable object
     * @return page houses that person with uuid ever live
     */
    Page<GetHouseResponseDTO> getAllEverPersonLivingHouses(UUID personUUID, Pageable pageable);

    /**
     *
     * Get all houses that person with uuid ever possess or empty list
     *
     * @param personUUID person uuid
     * @param pageable pageable object
     * @return page houses that person with uuid ever possess
     */
    Page<GetHouseResponseDTO> getAllEverPersonPossessedHouses(UUID personUUID, Pageable pageable);
}
