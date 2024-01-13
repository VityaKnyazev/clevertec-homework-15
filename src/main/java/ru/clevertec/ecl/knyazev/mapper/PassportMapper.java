package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.knyazev.data.http.passport.request.DeletePassportRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.passport.request.PostPutPassportRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.passport.response.GetPassportResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Passport;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    @Mapping(target = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Mapping(target = "updateDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    GetPassportResponseDTO toGetPassportResponseDTO(Passport passport);

    List<GetPassportResponseDTO> toGetPassportResponseDTOs(List<Passport> passports);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Passport toPassport(PostPutPassportRequestDTO postPutPassportRequestDTO);

    default UUID toPassportUUID(DeletePassportRequestDTO deletePassportRequestDTO) {
        return UUID.fromString(deletePassportRequestDTO.uuid());
    }
}
