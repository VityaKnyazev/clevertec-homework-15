package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Passport;
import ru.clevertec.ecl.knyazev.entity.Person;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "passportSeries", source = "passport.passportSeries")
    @Mapping(target = "passportNumber", source = "passport.passportNumber")
    @Mapping(target = "createDate", source = "passport.createDate")
    @Mapping(target = "updateDate", source = "passport.updateDate")
    GetPersonResponseDTO toGetPersonResponseDto(Person person);

    List<GetPersonResponseDTO> toGetPersonResponseDTOs(List<Person> persons);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "name", source = "postPutPersonRequestDTO.name")
    @Mapping(target = "surname", source = "postPutPersonRequestDTO.surname")
    @Mapping(target = "sex", source = "postPutPersonRequestDTO.sex")
    @Mapping(target = "passport", source = "passport")
    @Mapping(target = "livingHouse", source = "livingHouse")
    @Mapping(target = "possessedHouses", source = "possessedHouses")
    Person toPerson(PostPutPersonRequestDTO postPutPersonRequestDTO,
                    Passport passport,
                    House livingHouse,
                    List<House> possessedHouses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "name", source = "postPutPersonRequestDTO.name")
    @Mapping(target = "surname", source = "postPutPersonRequestDTO.surname")
    @Mapping(target = "sex", source = "postPutPersonRequestDTO.sex")
    @Mapping(target = "passport", source = "passport")
    @Mapping(target = "livingHouse", source = "livingHouse")
    @Mapping(target = "possessedHouses", source = "possessedHouses")
    Person toPerson(@MappingTarget Person person,
                    PostPutPersonRequestDTO postPutPersonRequestDTO,
                    Passport passport,
                    House livingHouse,
                    List<House> possessedHouses);
}
