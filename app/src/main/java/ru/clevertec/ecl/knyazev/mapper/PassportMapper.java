package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.knyazev.data.http.passport.request.PostPutPassportRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.passport.response.GetPassportResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Passport;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    @Mapping(target = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Mapping(target = "updateDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    GetPassportResponseDTO toGetPassportResponseDTO(Passport passport);

    List<GetPassportResponseDTO> toGetPassportResponseDTOs(List<Passport> passports);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateDate", ignore = true)
    Passport toPassport(PostPutPassportRequestDTO postPutPassportRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    Passport toPassport(@MappingTarget Passport passport, PostPutPassportRequestDTO postPutPassportRequestDTO);
}
