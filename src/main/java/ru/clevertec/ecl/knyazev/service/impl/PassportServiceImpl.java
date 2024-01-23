package ru.clevertec.ecl.knyazev.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.knyazev.data.http.passport.request.PostPutPassportRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.passport.response.GetPassportResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Passport;
import ru.clevertec.ecl.knyazev.mapper.PassportMapper;
import ru.clevertec.ecl.knyazev.repository.PassportRepository;
import ru.clevertec.ecl.knyazev.service.PassportService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    private final PassportMapper passportMapperImpl;

    /**
     * {@inheritDoc}
     */
    @Override
    public Passport getPassport(UUID passportUUID) throws ServiceException {
        return passportRepository.findByUuid(passportUUID)
                .orElseThrow(ServiceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetPassportResponseDTO getPassportResponseDTO(UUID passportUUID) throws ServiceException {
        return passportMapperImpl.toGetPassportResponseDTO(passportRepository.findByUuid(passportUUID)
                .orElseThrow(ServiceException::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GetPassportResponseDTO> getAll(Pageable pageable) {
        return passportMapperImpl.toGetPassportResponseDTOs(
                passportRepository.findAll(pageable).getContent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetPassportResponseDTO add(PostPutPassportRequestDTO postPutPassportRequestDTO) {
        return passportMapperImpl.toGetPassportResponseDTO(
                passportRepository.save(
                        passportMapperImpl.toPassport(postPutPassportRequestDTO)));
    }

    /**
     * {@inheritDoc}
     * @throws ServiceException when passport not found
     */
    @Override
    public GetPassportResponseDTO update(PostPutPassportRequestDTO postPutPassportRequestDTO) throws ServiceException {
        Passport dbPassport = passportRepository.findByUuid(
                UUID.fromString(postPutPassportRequestDTO.uuid()))
                .orElseThrow(ServiceException::new);

        return passportMapperImpl.toGetPassportResponseDTO(
                passportRepository.save(
                        passportMapperImpl.toPassport(dbPassport, postPutPassportRequestDTO)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(UUID passportUUID) {
        passportRepository.deleteByUuid(passportUUID);
    }
}
