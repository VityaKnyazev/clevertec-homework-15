package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.HouseHistory;
import ru.clevertec.ecl.knyazev.entity.type.PersonHouseType;
import ru.clevertec.ecl.knyazev.mapper.HouseMapper;
import ru.clevertec.ecl.knyazev.mapper.PersonMapper;
import ru.clevertec.ecl.knyazev.repository.impl.HouseHistoryRepository;
import ru.clevertec.ecl.knyazev.service.StatisticService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final HouseHistoryRepository houseHistoryRepository;

    private final PersonMapper personMapperImpl;
    private final HouseMapper houseMapperImpl;

    @Override
    public Page<GetPersonResponseDTO> getAllEverLivingPersonsInHouse(UUID houseUUID, Pageable pageable) {
        return getAllPersonsHistory(houseUUID,
                PersonHouseType.tenant,
                pageable);
    }

    @Override
    public Page<GetPersonResponseDTO> getAllEverHousePossessingPersons(UUID houseUUID, Pageable pageable) {
        return getAllPersonsHistory(houseUUID,
                PersonHouseType.owner,
                pageable);
    }

    @Override
    public Page<GetHouseResponseDTO> getAllEverPersonLivingHouses(UUID personUUID, Pageable pageable) {
        return getAllHousesHistory(personUUID,
                PersonHouseType.tenant,
                pageable);
    }

    @Override
    public Page<GetHouseResponseDTO> getAllEverPersonPossessedHouses(UUID personUUID, Pageable pageable) {
        return getAllHousesHistory(personUUID,
                PersonHouseType.owner,
                pageable);
    }

    /**
     *
     *
     * Get all history about persons that were related
     * with house by house uuid
     *
     * @param houseUUID house uuid
     * @param type person-house relation type
     * @param pageable pageable object
     * @return pageable person response DTOs that
     * were related with house by given house uuid
     */
    private Page<GetPersonResponseDTO> getAllPersonsHistory(UUID houseUUID,
                                                     PersonHouseType type,
                                                     Pageable pageable) {
        Page<HouseHistory> houseHistories = houseHistoryRepository.findAllByHouseUuidAndType(houseUUID,
                type,
                pageable);

        return new PageImpl<>(personMapperImpl.toGetPersonResponseDTOs(houseHistories.stream()
                .map(HouseHistory::getPerson)
                .toList()),
                houseHistories.getPageable(),
                houseHistories.getTotalElements());
    }

    /**
     *
     * Get all history about houses that were related
     * with person by given person uuid
     *
     * @param personUUID person uuid
     * @param type house-person relation type
     * @param pageable pageable object
     * @return pageable house response DTOs that
     * were related with person by given person uuid
     */
    private Page<GetHouseResponseDTO> getAllHousesHistory(UUID personUUID,
                                                          PersonHouseType type,
                                                          Pageable pageable) {
        Page<HouseHistory> houseHistories = houseHistoryRepository.findAllByPersonUuidAndType(personUUID,
                type,
                pageable);

        return new PageImpl<>(houseMapperImpl.toGetHouseResponseDTOs(houseHistories.stream()
                .map(HouseHistory::getHouse)
                .toList()),
                houseHistories.getPageable(),
                houseHistories.getTotalElements());
    }
}
