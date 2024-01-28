package ru.clevertec.ecl.knyazev.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.passport.request.PostPutPassportRequestDTO;
import ru.clevertec.ecl.knyazev.entity.Address;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Passport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PassportServiceImplTestData {
    private static final Long EXPECTED_PASSPORT_ID = 1L;
    private static final String EXPECTED_AND_INPUT_PASSPORT_UUID = "1c8d196b-5b76-40c8-b3f5-7bccb8a43d45";
    private static final String EXPECTED_AND_INPUT_PASSPORT_SERIES ="KB";
    private static final String EXPECTED_AND_INPUT_PASSPORT_NUMBER = "1256398";

    private static final Long REPLACEABLE_PASSPORT_ID = 12L;
    public static final String REPLACEABLE_PASSPORT_UUID = "179ff207-569a-46d8-a6f8-daa7f31974b6";
    private static final String REPLACEABLE_PASSPORT_SERIES ="KC";
    private static final String REPLACEABLE_PASSPORT_NUMBER = "2945137";

    private static final Long EXPECTED_PAGE_PASSPORT_ID = 5L;
    private static final String EXPECTED_PAGE_PASSPORT_UUID = "63100726-97fb-458a-941d-07ab4e539b2f";
    private static final String EXPECTED_PAGE_PASSPORT_SERIES ="KP";
    private static final String EXPECTED_PAGE_PASSPORT_NUMBER = "2561963";

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 1;
    public static final long TOTAL_ELEMENTS = 1;


    public static Passport expectedPassport() {
        return Passport.builder()
                .id(EXPECTED_PASSPORT_ID)
                .uuid(UUID.fromString(EXPECTED_AND_INPUT_PASSPORT_UUID))
                .passportSeries(EXPECTED_AND_INPUT_PASSPORT_SERIES)
                .passportNumber(EXPECTED_AND_INPUT_PASSPORT_NUMBER)
                .createDate(LocalDateTime.now())
                .build();
    }

    /**
     * Passport on which previous passport
     * will be replaced after updating
     *
     * @return replaceable passport
     */
    public static Passport replaceablePassport() {
        return Passport.builder()
                .id(REPLACEABLE_PASSPORT_ID)
                .uuid(UUID.fromString(REPLACEABLE_PASSPORT_UUID))
                .passportSeries(REPLACEABLE_PASSPORT_SERIES)
                .passportNumber(REPLACEABLE_PASSPORT_NUMBER)
                .createDate(LocalDateTime.now())
                .build();
    }

    public static Page<Passport> expectedPagePassports() {
        return new PageImpl<>(List.of(
                Passport.builder()
                        .id(EXPECTED_PAGE_PASSPORT_ID)
                        .uuid(UUID.fromString(EXPECTED_PAGE_PASSPORT_UUID))
                        .passportSeries(EXPECTED_PAGE_PASSPORT_SERIES)
                        .passportNumber(EXPECTED_PAGE_PASSPORT_NUMBER)
                        .createDate(LocalDateTime.now())
                        .build()
        ), PageRequest.of(PAGE_NUMBER, PAGE_SIZE), TOTAL_ELEMENTS);
    }

    public static PostPutPassportRequestDTO inputPostPassportRequestDTO() {
        return PostPutPassportRequestDTO.builder()
                .passportSeries(EXPECTED_AND_INPUT_PASSPORT_SERIES)
                .passportNumber(EXPECTED_AND_INPUT_PASSPORT_NUMBER)
                .build();
    }

    public static PostPutPassportRequestDTO inputPutPassportRequestDTO() {
        return PostPutPassportRequestDTO.builder()
                .uuid(EXPECTED_AND_INPUT_PASSPORT_UUID)
                .passportSeries(EXPECTED_AND_INPUT_PASSPORT_SERIES)
                .passportNumber(EXPECTED_AND_INPUT_PASSPORT_NUMBER)
                .build();
    }
}
