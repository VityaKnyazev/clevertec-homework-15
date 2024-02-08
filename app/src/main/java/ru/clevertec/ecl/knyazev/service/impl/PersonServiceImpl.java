package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Passport;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.mapper.HouseMapper;
import ru.clevertec.ecl.knyazev.mapper.PersonMapper;
import ru.clevertec.ecl.knyazev.repository.PersonRepository;
import ru.clevertec.ecl.knyazev.service.HouseService;
import ru.clevertec.ecl.knyazev.service.PassportService;
import ru.clevertec.ecl.knyazev.service.PersonService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(prefix = "cache",
        name = "enabled",
        havingValue = "false")
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PassportService passportServiceImpl;
    private final HouseService houseServiceImpl;

    private final PersonMapper personMapperImpl;
    private final HouseMapper houseMapperImpl;

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public GetPersonResponseDTO get(UUID personUUID) throws ServiceException {
        return personMapperImpl.toGetPersonResponseDto(personRepository.findByUuid(personUUID)
                .orElseThrow(ServiceException::new));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetPersonResponseDTO> getAll(Pageable pageable) {
        return personMapperImpl.toGetPersonResponseDTOs(
                personRepository.findAll(pageable).getContent());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetHouseResponseDTO> getPossessingHouses(UUID personUUID, Pageable pageable) {
        List<House> possessingHouses = personRepository.findByUuid(personUUID)
                .orElseThrow(ServiceException::new)
                .getPossessedHouses();

        List<House> pagingPossessingHouses = new PageImpl<>(possessingHouses, pageable, possessingHouses.size())
                .getContent();
        return houseMapperImpl.toGetHouseResponseDTOs(pagingPossessingHouses);
    }

    /**
     * {@inheritDoc}
     *
     * @throws if finding passport, living house or
     *            possessing houses error
     */
    @Transactional
    @Override
    public GetPersonResponseDTO add(PostPutPersonRequestDTO postPutPersonRequestDTO) throws ServiceException {
        Passport dbPassport = getPersonPassport(postPutPersonRequestDTO);

        House dbLivingHouse = getPersonLivingHouse(postPutPersonRequestDTO);

        List<House> possessedHouses = getPersonPossessedHouses(postPutPersonRequestDTO);

        return personMapperImpl.toGetPersonResponseDto(
                personRepository.save(
                        personMapperImpl.toPerson(postPutPersonRequestDTO,
                                dbPassport,
                                dbLivingHouse,
                                possessedHouses)));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ServiceException if finding person, passport, living house or
     *                          possessing houses error
     */
    @Transactional
    @Override
    public GetPersonResponseDTO update(PostPutPersonRequestDTO postPutPersonRequestDTO) throws ServiceException {
        Person dbPerson = personRepository.findByUuid(
                        UUID.fromString(postPutPersonRequestDTO.uuid()))
                .orElseThrow(ServiceException::new);

        Passport dbPassport = getPersonPassport(postPutPersonRequestDTO);

        House dbLivingHouse = getPersonLivingHouse(postPutPersonRequestDTO);

        List<House> possessedHouses = getPersonPossessedHouses(postPutPersonRequestDTO);

        return personMapperImpl.toGetPersonResponseDto(
                personRepository.save(
                        personMapperImpl.toPerson(dbPerson,
                                postPutPersonRequestDTO,
                                dbPassport,
                                dbLivingHouse,
                                possessedHouses)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void remove(UUID personUUID) {
        personRepository.deleteByUuid(personUUID);
    }


    /**
     * Get passport from datasource
     *
     * @param postPutPersonRequestDTO request dto
     * @return passport from datasource
     * @throws ServiceException if not found
     */
    private Passport getPersonPassport(PostPutPersonRequestDTO postPutPersonRequestDTO) throws ServiceException {
        return passportServiceImpl.getPassport(
                UUID.fromString(postPutPersonRequestDTO.passportUUID()));
    }

    /**
     * Get person living house from datasource
     *
     * @param postPutPersonRequestDTO request dto
     * @return person living house
     * @throws ServiceException if not found
     */
    private House getPersonLivingHouse(PostPutPersonRequestDTO postPutPersonRequestDTO) throws ServiceException {
        return houseServiceImpl.getHouse(
                UUID.fromString(postPutPersonRequestDTO.livingHouseUUID()));
    }

    /**
     *
     * Get person possessed houses or empty list if no possessing
     *
     * @param postPutPersonRequestDTO request dto
     * @return person possessed houses
     */
    private List<House> getPersonPossessedHouses(PostPutPersonRequestDTO postPutPersonRequestDTO) {
        List<House> possesingHouses = new ArrayList<>();
        List<String> possessingHouseUUIDs = postPutPersonRequestDTO.possessingHouseUUIDs();

        if (possessingHouseUUIDs != null && !possessingHouseUUIDs.isEmpty()) {
            possesingHouses = postPutPersonRequestDTO.possessingHouseUUIDs().stream()
                    .map(houseUUID -> houseServiceImpl.getHouse(UUID.fromString(houseUUID)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return possesingHouses;
    }
}
