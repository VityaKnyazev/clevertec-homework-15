package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.mapper.HouseMapper;
import ru.clevertec.ecl.knyazev.mapper.PersonMapper;
import ru.clevertec.ecl.knyazev.repository.HouseRepository;
import ru.clevertec.ecl.knyazev.service.AddressService;
import ru.clevertec.ecl.knyazev.service.HouseService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
@ConditionalOnProperty(prefix = "cache",
        name = "enabled",
        havingValue = "false")
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final AddressService addressServiceImpl;

    private final HouseMapper houseMapperImpl;
    private final PersonMapper personMapperImpl;

    @Transactional(readOnly = true)
    @Override
    public House getHouse(UUID houseUUID) throws ServiceException {
        return houseRepository.findByUuid(houseUUID)
                .orElseThrow(ServiceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public GetHouseResponseDTO getHouseResponseDTO(UUID houseUUID) throws ServiceException {
        return houseMapperImpl.toGetHouseResponseDTO(houseRepository.findByUuid(houseUUID)
                .orElseThrow(ServiceException::new));
    }

    /**
     * {@inheritDoc}
     * <p>
     * When using searching or sorting then using address fields for it.
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetHouseResponseDTO> getAll(Pageable pageable, Searching searching) {
        List<House> houses;

        if (searching.useSearching() || pageable.getSort().isSorted()) {
            houses = addressServiceImpl.getAllAddresses(pageable, searching).stream()
                    .map(Address::getHouse)
                    .toList();
        } else {
            houses = houseRepository.findAll(pageable).getContent();
        }

        return houseMapperImpl.toGetHouseResponseDTOs(houses);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetPersonResponseDTO> getLivingPersons(UUID houseUUID, Pageable pageable) {
        List<Person> livingPersons = houseRepository.findByUuid(houseUUID)
                .orElseThrow(ServiceException::new)
                .getLivingPersons();

        List<Person> pagingLivingPersons = new PageImpl<>(livingPersons, pageable, livingPersons.size())
                .getContent();
        return personMapperImpl.toGetPersonResponseDTOs(pagingLivingPersons);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public GetHouseResponseDTO add(PostPutHouseRequestDTO postPutHouseRequestDTO) {
        Address dbAddress = addressServiceImpl.getAddress(
                UUID.fromString(postPutHouseRequestDTO.addressUUID()));

        return houseMapperImpl.toGetHouseResponseDTO(
                houseRepository.save(
                        houseMapperImpl.toHouse(postPutHouseRequestDTO, dbAddress)));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ServiceException if house not found in datasource
     */
    @Transactional
    @Override
    public GetHouseResponseDTO update(PostPutHouseRequestDTO postPutHouseRequestDTO) throws ServiceException {
        House dbHouse = houseRepository.findByUuid(
                        UUID.fromString(postPutHouseRequestDTO.uuid()))
                .orElseThrow(ServiceException::new);

        Address dbAddress = addressServiceImpl.getAddress(
                UUID.fromString(postPutHouseRequestDTO.addressUUID()));

        return houseMapperImpl.toGetHouseResponseDTO(
                houseRepository.save(
                        houseMapperImpl.toHouse(dbHouse, dbAddress)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void remove(UUID houseUUID) {
        houseRepository.deleteByUuid(houseUUID);
    }
}
