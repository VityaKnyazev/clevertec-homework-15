package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.knyazev.data.http.person.request.DeletePersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "passportSeries", source = "passport.passportSeries")
    @Mapping(target = "passportNumber", source = "passport.passportNumber")
    @Mapping(target = "createDate", source = "passport.createDate")
    @Mapping(target = "updateDate", source = "passport.updateDate")
    GetPersonResponseDTO toGetPersonResponseDto(Person person);

    List<GetPersonResponseDTO> toGetPersonResponseDTOs(List<Person> persons);

    @Named("possessingHouseUUIDsInPerson")
    default List<House> possessingHouseUUIDsInPerson(List<String> possessingHouseUUIDs) {
        List<House> possessedHouses = new ArrayList<>();

        if (possessingHouseUUIDs != null && !possessedHouses.isEmpty()) {
            possessingHouseUUIDs.forEach(hUUID -> possessedHouses.add(House.builder()
                    .uuid(UUID.fromString(hUUID))
                    .build()));
        }
        return possessedHouses;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passport.uuid", source = "passportUUID")
    @Mapping(target = "livingHouse.uuid", source = "livingHouseUUID")
    @Mapping(target = "possessedHouses",
            source = "possessingHouseUUIDs",
            qualifiedByName = "possessingHouseUUIDsInPerson")
    Person toPerson(PostPutPersonRequestDTO postPutPersonRequestDTO);

    default UUID toPersonUUID(DeletePersonRequestDTO deletePersonRequestDTO) {
        return UUID.fromString(deletePersonRequestDTO.uuid());
    }
}
