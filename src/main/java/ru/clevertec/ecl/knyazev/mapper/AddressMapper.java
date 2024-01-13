package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.knyazev.data.http.address.request.DeleteAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    GetAddressResponseDTO toGetAddressResponseDTO(Address address);

    List<GetAddressResponseDTO> toGetAddressResponseDTOs(List<Address> addresses);

    @Mapping(target = "id", ignore = true)
    Address toAddress(PostPutAddressRequestDTO postPutAddressRequestDTO);

    default UUID toAddressUUID(DeleteAddressRequestDTO deleteAddressRequestDTO) {
        return UUID.fromString(deleteAddressRequestDTO.uuid());
    }
}
