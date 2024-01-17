package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.knyazev.dao.PassportDAO;
import ru.clevertec.ecl.knyazev.data.http.passport.request.DeletePassportRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.passport.request.PostPutPassportRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.passport.response.GetPassportResponseDTO;
import ru.clevertec.ecl.knyazev.mapper.PassportMapper;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.service.PassportService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PassportServiceImpl implements PassportService {

    private final PassportDAO passportDAOImpl;

    private final PassportMapper passportMapperImpl;

    /**
     * {@inheritDoc}
     */
    @Override
    public GetPassportResponseDTO get(UUID passportUUID) throws ServiceException {
        return passportMapperImpl.toGetPassportResponseDTO(passportDAOImpl.findByUUID(passportUUID)
                .orElseThrow(ServiceException::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GetPassportResponseDTO> getAll(Paging paging) {
        return passportMapperImpl.toGetPassportResponseDTOs(
                paging.usePaging()
                        ? passportDAOImpl.findAll(paging)
                        : passportDAOImpl.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetPassportResponseDTO add(PostPutPassportRequestDTO postPutPassportRequestDTO) {
        return passportMapperImpl.toGetPassportResponseDTO(
                passportDAOImpl.save(
                        passportMapperImpl.toPassport(postPutPassportRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetPassportResponseDTO update(PostPutPassportRequestDTO postPutPassportRequestDTO) {
        return passportMapperImpl.toGetPassportResponseDTO(
                passportDAOImpl.update(
                        passportMapperImpl.toPassport(postPutPassportRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(DeletePassportRequestDTO deletePassportRequestDTO) {
        passportDAOImpl.delete(
                passportMapperImpl.toPassportUUID(deletePassportRequestDTO));
    }
}
