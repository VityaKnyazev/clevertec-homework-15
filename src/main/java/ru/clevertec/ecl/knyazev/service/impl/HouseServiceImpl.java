package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.knyazev.dao.HouseDAO;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Pager;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.mapper.HouseMapper;
import ru.clevertec.ecl.knyazev.mapper.PersonMapper;
import ru.clevertec.ecl.knyazev.service.HouseService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseDAO houseDAOJPAImpl;

    private final HouseMapper houseMapperImpl;
    private final PersonMapper personMapperImpl;

    private final Pager pager;

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public GetHouseResponseDTO get(UUID houseUUID) throws ServiceException {
        return houseMapperImpl.toGetHouseResponseDTO(houseDAOJPAImpl.findByUUID(houseUUID)
                .orElseThrow(ServiceException::new));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetHouseResponseDTO> getAll(Paging paging, Searching searching) {
        return houseMapperImpl.toGetHouseResponseDTOs(
                houseDAOJPAImpl.findAll(paging, searching));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetPersonResponseDTO> getLivingPersons(UUID houseUUID, Paging paging) {

        List<Person> livingPersons = houseDAOJPAImpl.findByUUID(houseUUID)
                .orElseThrow(ServiceException::new)
                .getLivingPersons();

        List<Person> pagingLivingPersons = (List<Person>) pager.getPaginationResult(livingPersons, paging);
        return personMapperImpl.toGetPersonResponseDTOs(pagingLivingPersons);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public GetHouseResponseDTO add(PostPutHouseRequestDTO postPutHouseRequestDTO) {
        return houseMapperImpl.toGetHouseResponseDTO(
                houseDAOJPAImpl.save(
                        houseMapperImpl.toHouse(postPutHouseRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public GetHouseResponseDTO update(PostPutHouseRequestDTO postPutHouseRequestDTO) {
        return houseMapperImpl.toGetHouseResponseDTO(
                houseDAOJPAImpl.update(
                        houseMapperImpl.toHouse(postPutHouseRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void remove(UUID houseUUID) {
        houseDAOJPAImpl.delete(houseUUID);
    }
}
