package ru.clevertec.ecl.knyazev.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;

import java.util.List;
import java.util.UUID;

public class AddressServiceImplTestData {
    private static final Long EXPECTED_ADDRESS_ID = 1L;
    private static final String EXPECTED_AND_INPUT_ADDRESS_UUID = "8665f91c-80df-4205-b66d-4259f9e66020";
    private static final String EXPECTED_AND_INPUT_ADDRESS_AREA = "West";
    private static final String EXPECTED_AND_INPUT_ADDRESS_COUNTRY = "China";
    private static final String EXPECTED_AND_INPUT_ADDRESS_CITY = "Hong-Kong";
    private static final String EXPECTED_AND_INPUT_ADDRESS_STREET = "Chsji Po";
    private static final Integer EXPECTED_AND_INPUT_ADDRESS_NUMBER = 12;

    private static final Long REPLACEABLE_ADDRESS_ID = 18L;
    private static final String REPLACEABLE_ADDRESS_UUID = "fb01413c-f1af-4f35-8a4e-9f49f4db75e9";
    private static final String REPLACEABLE_ADDRESS_AREA = "East";
    private static final String REPLACEABLE_ADDRESS_COUNTRY = "Poalnd";
    private static final String REPLACEABLE_ADDRESS_CITY = "Rrakov";
    private static final String REPLACEABLE_ADDRESS_STREET = "Zhabkina";
    private static final Integer REPLACEABLE_ADDRESS_NUMBER = 28;

    private static final Long EXPECTED_PAGE_ADDRESS_ID = 3L;
    private static final String EXPECTED_PAGE_ADDRESS_UUID = "99669641-dc08-4c49-b36d-b6e301e0976a";
    private static final String EXPECTED_PAGE_ADDRESS_AREA = "West";
    private static final String EXPECTED_PAGE_ADDRESS_COUNTRY = "Russia";
    private static final String EXPECTED_PAGE_ADDRESS_CITY = "Suraj";
    private static final String EXPECTED_PAGE_ADDRESS_STREET = "Lenina";
    private static final Integer EXPECTED_PAGE_ADDRESS_NUMBER = 15;

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 1;
    public static final long TOTAL_ELEMENTS = 1;

    public static final String SEARCHING_PARAM = "West";


    public static Address expectedAddress() {
        return Address.builder()
                .id(EXPECTED_ADDRESS_ID)
                .uuid(UUID.fromString(EXPECTED_AND_INPUT_ADDRESS_UUID))
                .area(EXPECTED_AND_INPUT_ADDRESS_AREA)
                .country(EXPECTED_AND_INPUT_ADDRESS_COUNTRY)
                .city(EXPECTED_AND_INPUT_ADDRESS_CITY)
                .street(EXPECTED_AND_INPUT_ADDRESS_STREET)
                .number(EXPECTED_AND_INPUT_ADDRESS_NUMBER)
                .build();
    }

    /**
     * Address on which previous address
     * will be replaced after updating
     *
     * @return replaceable address
     */
    public static Address replaceableAddress() {
        return Address.builder()
                .id(REPLACEABLE_ADDRESS_ID)
                .uuid(UUID.fromString(REPLACEABLE_ADDRESS_UUID))
                .area(REPLACEABLE_ADDRESS_AREA)
                .country(REPLACEABLE_ADDRESS_COUNTRY)
                .city(REPLACEABLE_ADDRESS_CITY)
                .street(REPLACEABLE_ADDRESS_STREET)
                .number(REPLACEABLE_ADDRESS_NUMBER)
                .build();
    }

    public static Page<Address> expectedPageAddresses() {
        House expectedHouse = HouseServiceImplTestData.expectedHouse();

        return new PageImpl<>(List.of(
                Address.builder()
                        .id(EXPECTED_PAGE_ADDRESS_ID)
                        .uuid(UUID.fromString(EXPECTED_PAGE_ADDRESS_UUID))
                        .area(EXPECTED_PAGE_ADDRESS_AREA)
                        .country(EXPECTED_PAGE_ADDRESS_COUNTRY)
                        .city(EXPECTED_PAGE_ADDRESS_CITY)
                        .street(EXPECTED_PAGE_ADDRESS_STREET)
                        .number(EXPECTED_PAGE_ADDRESS_NUMBER)
                        .house(expectedHouse)
                        .build()
        ), PageRequest.of(PAGE_NUMBER, PAGE_SIZE), TOTAL_ELEMENTS);
    }

    public static PostPutAddressRequestDTO inputPostAddressRequestDTO() {
        return PostPutAddressRequestDTO.builder()
                .area(EXPECTED_AND_INPUT_ADDRESS_AREA)
                .country(EXPECTED_AND_INPUT_ADDRESS_COUNTRY)
                .city(EXPECTED_AND_INPUT_ADDRESS_CITY)
                .street(EXPECTED_AND_INPUT_ADDRESS_STREET)
                .number(EXPECTED_AND_INPUT_ADDRESS_NUMBER.toString())
                .build();
    }

    public static PostPutAddressRequestDTO inputPutAddressRequestDTO() {
        return PostPutAddressRequestDTO.builder()
                .uuid(EXPECTED_AND_INPUT_ADDRESS_UUID)
                .area(EXPECTED_AND_INPUT_ADDRESS_AREA)
                .country(EXPECTED_AND_INPUT_ADDRESS_COUNTRY)
                .city(EXPECTED_AND_INPUT_ADDRESS_CITY)
                .street(EXPECTED_AND_INPUT_ADDRESS_STREET)
                .number(EXPECTED_AND_INPUT_ADDRESS_NUMBER.toString())
                .build();
    }
}
