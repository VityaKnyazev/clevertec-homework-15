package ru.clevertec.ecl.knyazev.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class HouseServiceImplTestData {
    private static final Long EXPECTED_HOUSE_ID = 1L;
    private static final String EXPECTED_AND_INPUT_HOUSE_UUID = "ab726b0c-6b70-4d49-8127-c1c0e6b0a86c";

    private static final Long REPLACEABLE_HOUSE_ID = 14L;
    public static final String REPLACEABLE_HOUSE_UUID = "5661ec2b-fa49-4390-933f-39b489c3d45b";

    private static final Long EXPECTED_PAGE_HOUSE_ID = 5L;
    private static final String EXPECTED_PAGE_HOUSE_UUID = "2bd1d608-a634-472d-8e6e-6a1db3921784";

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 1;
    public static final long TOTAL_ELEMENTS = 1;


    public static House expectedHouse() {
        Address expectedAddress = AddressServiceImplTestData.expectedAddress();

        return House.builder()
                .id(EXPECTED_HOUSE_ID)
                .uuid(UUID.fromString(EXPECTED_AND_INPUT_HOUSE_UUID))
                .address(expectedAddress)
                .createDate(LocalDateTime.now())
                .build();
    }

    /**
     * House on which previous house
     * will be replaced after updating
     *
     * @return replaceable house
     */
    public static House replaceableHouse() {
        Address replaceableAddress = AddressServiceImplTestData.replaceableAddress();

        return House.builder()
                .id(REPLACEABLE_HOUSE_ID)
                .uuid(UUID.fromString(REPLACEABLE_HOUSE_UUID))
                .address(replaceableAddress)
                .createDate(LocalDateTime.now())
                .build();
    }

    public static Page<House> expectedPageHouses() {
        Address expectedAddress = AddressServiceImplTestData.expectedAddress();

        return new PageImpl<>(List.of(
                House.builder()
                        .id(EXPECTED_PAGE_HOUSE_ID)
                        .uuid(UUID.fromString(EXPECTED_PAGE_HOUSE_UUID))
                        .address(expectedAddress)
                        .build()
        ), PageRequest.of(PAGE_NUMBER, PAGE_SIZE), TOTAL_ELEMENTS);
    }

}
