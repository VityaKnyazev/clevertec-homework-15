package ru.clevertec.ecl.knyazev.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class HouseTestData {
    private static final Long EXPECTED_HOUSE_ID = 1L;

    private static final String EXPECTED_AND_INPUT_OUTPUT_HOUSE_UUID = "ab726b0c-6b70-4d49-8127-c1c0e6b0a86c";

    private static final Long REPLACEABLE_HOUSE_ID = 14L;
    public static final String REPLACEABLE_HOUSE_UUID = "5661ec2b-fa49-4390-933f-39b489c3d45b";

    private static final Long EXPECTED_PAGE_HOUSE_ID = 5L;
    private static final String EXPECTED_PAGE_HOUSE_UUID = "2bd1d608-a634-472d-8e6e-6a1db3921784";

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 1;
    public static final long TOTAL_ELEMENTS = 1;


    public static House expectedHouse() {
        Address expectedAddress = AddressTestData.expectedAddress();

        return House.builder()
                .id(EXPECTED_HOUSE_ID)
                .uuid(UUID.fromString(EXPECTED_AND_INPUT_OUTPUT_HOUSE_UUID))
                .address(expectedAddress)
                .createDate(LocalDateTime.now())
                .build();
    }

    public static GetHouseResponseDTO expectedHouseResponseDTO() {
        GetAddressResponseDTO getAddressResponseDTO = AddressTestData.expectedAddressResponseDTO();

        return GetHouseResponseDTO.builder()
                .uuid(EXPECTED_AND_INPUT_OUTPUT_HOUSE_UUID)
                .area(getAddressResponseDTO.area())
                .country(getAddressResponseDTO.country())
                .city(getAddressResponseDTO.city())
                .street(getAddressResponseDTO.street())
                .number(Integer.valueOf(getAddressResponseDTO.number()))
                .createDate(LocalDateTime.now().toString())
                .build();
    }

    public static List<GetHouseResponseDTO> expectedHouseResponseDTOs() {
        return List.of(
                expectedHouseResponseDTO()
        );
    }

    /**
     * House on which previous house
     * will be replaced after updating
     *
     * @return replaceable house
     */
    public static House replaceableHouse() {
        Address replaceableAddress = AddressTestData.replaceableAddress();

        return House.builder()
                .id(REPLACEABLE_HOUSE_ID)
                .uuid(UUID.fromString(REPLACEABLE_HOUSE_UUID))
                .address(replaceableAddress)
                .createDate(LocalDateTime.now())
                .build();
    }

    public static Page<House> expectedPageHouses() {
        Address expectedAddress = AddressTestData.expectedAddress();

        return new PageImpl<>(List.of(
                House.builder()
                        .id(EXPECTED_PAGE_HOUSE_ID)
                        .uuid(UUID.fromString(EXPECTED_PAGE_HOUSE_UUID))
                        .address(expectedAddress)
                        .createDate(LocalDateTime.now())
                        .build()
        ), PageRequest.of(PAGE_NUMBER, PAGE_SIZE), TOTAL_ELEMENTS);
    }

    public static Page<GetHouseResponseDTO> expectedPageHouseResponseDTOs() {
        Address expectedAddress = AddressTestData.expectedAddress();

        return new PageImpl<>(List.of(
                GetHouseResponseDTO.builder()
                        .uuid(EXPECTED_PAGE_HOUSE_UUID)
                        .area(expectedAddress.getArea())
                        .country(expectedAddress.getCountry())
                        .city(expectedAddress.getCity())
                        .street(expectedAddress.getStreet())
                        .number(expectedAddress.getNumber())
                        .createDate(LocalDateTime.now().toString())
                        .build()
        ), PageRequest.of(PAGE_NUMBER, PAGE_SIZE), TOTAL_ELEMENTS);
    }

    public static PostPutHouseRequestDTO inputPostHouseRequestDTO() {
        String inputAddressUUID = AddressTestData.expectedAddressResponseDTO()
                .uuid();

        return PostPutHouseRequestDTO.builder()
                .addressUUID(inputAddressUUID)
                .build();
    }

    public static PostPutHouseRequestDTO inputPutHouseRequestDTO() {
        String inputAddressUUID = AddressTestData.expectedAddressResponseDTO()
                .uuid();

        return PostPutHouseRequestDTO.builder()
                .uuid(EXPECTED_AND_INPUT_OUTPUT_HOUSE_UUID)
                .addressUUID(inputAddressUUID)
                .build();
    }


}
