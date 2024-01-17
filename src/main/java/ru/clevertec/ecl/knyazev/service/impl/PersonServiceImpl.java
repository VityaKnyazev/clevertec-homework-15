package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.knyazev.dao.PersonDAO;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Pager;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.mapper.HouseMapper;
import ru.clevertec.ecl.knyazev.mapper.PersonMapper;
import ru.clevertec.ecl.knyazev.service.PersonService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDAO personDAOJPAImpl;

    private final PersonMapper personMapperImpl;
    private final HouseMapper houseMapperImpl;

    private final Pager pager;

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public GetPersonResponseDTO get(UUID personUUID) throws ServiceException {
        return personMapperImpl.toGetPersonResponseDto(personDAOJPAImpl.findByUUID(personUUID)
                .orElseThrow(ServiceException::new));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetPersonResponseDTO> getAll(Paging paging) {
        return personMapperImpl.toGetPersonResponseDTOs(
                personDAOJPAImpl.findAll(paging));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetHouseResponseDTO> getPossessingHouses(UUID personUUID, Paging paging) {
        List<House> possessingHouses = personDAOJPAImpl.findByUUID(personUUID)
                .orElseThrow(ServiceException::new)
                .getPossessedHouses();

        List<House> pagingPossessingHouses = (List<House>) pager.getPaginationResult(possessingHouses, paging);
        return houseMapperImpl.toGetHouseResponseDTOs(pagingPossessingHouses);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public GetPersonResponseDTO add(PostPutPersonRequestDTO postPutPersonRequestDTO) {
        return personMapperImpl.toGetPersonResponseDto(
                personDAOJPAImpl.save(
                        personMapperImpl.toPerson(postPutPersonRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public GetPersonResponseDTO update(PostPutPersonRequestDTO postPutPersonRequestDTO) {
        return personMapperImpl.toGetPersonResponseDto(
                personDAOJPAImpl.update(
                        personMapperImpl.toPerson(postPutPersonRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void remove(UUID personUUID) {
        personDAOJPAImpl.delete(personUUID);
    }
}
