package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    GetAddressResponseDTO toGetAddressResponseDTO(Address address);

    List<GetAddressResponseDTO> toGetAddressResponseDTOs(List<Address> addresses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    Address toAddress(PostPutAddressRequestDTO postPutAddressRequestDTO);

    @Mapping(target = "id", ignore = true)
    Address toAddress(@MappingTarget Address dbAddress, PostPutAddressRequestDTO postPutAddressRequestDTO);
}
