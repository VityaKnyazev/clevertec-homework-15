package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.knyazev.data.http.address.request.PatchAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    GetAddressResponseDTO toGetAddressResponseDTO(Address address);

    PatchAddressRequestDTO toPatchAddressRequestDto(Address address);

    List<GetAddressResponseDTO> toGetAddressResponseDTOs(List<Address> addresses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "house", ignore = true)
    Address toAddress(PostPutAddressRequestDTO postPutAddressRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "house", ignore = true)
    Address toAddress(@MappingTarget Address dbAddress, PostPutAddressRequestDTO postPutAddressRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "house", ignore = true)
    Address toAddress(@MappingTarget Address dbAddress, PatchAddressRequestDTO patchAddressRequestDTO);
}
