package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseMapper {
    @Mapping(target = "area", source = "address.area")
    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "number", source = "address.number")
    @Mapping(target = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    GetHouseResponseDTO toGetHouseResponseDTO(House house);

    List<GetHouseResponseDTO> toGetHouseResponseDTOs(List<House> houses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "livingPersons", ignore = true)
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    House toHouse(PostPutHouseRequestDTO postPutHouseRequestDTO, Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "address", source = "address")
    @Mapping(target = "livingPersons", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    House toHouse(@MappingTarget House house, Address address);
}
