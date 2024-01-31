package ru.clevertec.ecl.knyazev.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.entity.House;
import ru.clevertec.ecl.knyazev.entity.Passport;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.entity.type.PersonSex;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PersonTestData {
    private static final Long EXPECTED_PERSON_ID = 1L;
    private static final String EXPECTED_AND_INPUT_OUTPUT_PERSON_UUID = "8d113b5a-59d5-4f60-839b-506309d6d9f0";
    private static final String EXPECTED_AND_INPUT_OUTPUT_PERSON_NAME = "Nikolay";
    private static final String EXPECTED_AND_INPUT_OUTPUT_PERSON_SURNAME = "Klimov";
    private static final PersonSex EXPECTED_AND_INPUT_OUTPUT_PERSON_SEX = PersonSex.male;

    private static final String INPUT_PERSON_PASSPORT_UUID = "00ea7922-7bda-43b4-876d-6741e86c5921";
    private static final String INPUT_PERSON_LIVING_HOUSE_UUID = "6471b0b2-cf43-4530-a891-3c2cafed20c3";
    private static final List<String> INPUT_PERSON_POSSESSING_HOUSE_UUIDS = List.of(
            "6471b0b2-cf43-4530-a891-3c2cafed20c3"
    );

    private static final Long EXPECTED_PAGE_PERSON_ID = 12L;
    private static final String EXPECTED_PAGE_PERSON_UUID = "1ff184ed-b64f-42fc-8096-12fcadcdd56d";
    private static final String EXPECTED_PAGE_PERSON_NAME = "Elena";
    private static final String EXPECTED_PAGE_PERSON_SURNAME = "Davidenko";
    private static final PersonSex EXPECTED_PAGE_PERSON_SEX = PersonSex.female;

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 1;
    public static final long TOTAL_ELEMENTS = 1;


    public static Person expectedPerson() {
        Passport expectedPassport = PassportTestData.expectedPassport();
        House expectedLivingHouse = HouseTestData.expectedHouse();
        List<House> expectedPossessingHouses = HouseTestData.expectedPageHouses()
                .stream().collect(Collectors.toList());

        return Person.builder()
                .id(EXPECTED_PERSON_ID)
                .uuid(UUID.fromString(EXPECTED_AND_INPUT_OUTPUT_PERSON_UUID))
                .name(EXPECTED_AND_INPUT_OUTPUT_PERSON_NAME)
                .surname(EXPECTED_AND_INPUT_OUTPUT_PERSON_SURNAME)
                .sex(EXPECTED_AND_INPUT_OUTPUT_PERSON_SEX)
                .passport(expectedPassport)
                .livingHouse(expectedLivingHouse)
                .possessedHouses(expectedPossessingHouses)
                .build();
    }

    public static GetPersonResponseDTO expectedPersonResponseDTO() {
        Passport expectedPassport = PassportTestData.expectedPassport();

        return GetPersonResponseDTO.builder()
                .uuid(EXPECTED_AND_INPUT_OUTPUT_PERSON_UUID)
                .name(EXPECTED_AND_INPUT_OUTPUT_PERSON_NAME)
                .surname(EXPECTED_AND_INPUT_OUTPUT_PERSON_SURNAME)
                .sex(EXPECTED_AND_INPUT_OUTPUT_PERSON_SEX.name())
                .passportSeries(expectedPassport.getPassportSeries())
                .passportNumber(expectedPassport.getPassportNumber())
                .createDate(expectedPassport.getCreateDate().toString())
                .updateDate(expectedPassport.getUpdateDate() != null
                        ? expectedPassport.getUpdateDate().toString()
                        : null)
                .build();
    }

    public static Page<Person> expectedPagePersons() {
        Passport expectedPassport = PassportTestData.expectedPassport();
        House expectedLivingHouse = HouseTestData.expectedHouse();
        List<House> expectedPossessingHouses = HouseTestData.expectedPageHouses().getContent();

        return new PageImpl<>(List.of(
                Person.builder()
                        .id(EXPECTED_PAGE_PERSON_ID)
                        .uuid(UUID.fromString(EXPECTED_PAGE_PERSON_UUID))
                        .name(EXPECTED_PAGE_PERSON_NAME)
                        .surname(EXPECTED_PAGE_PERSON_SURNAME)
                        .sex(EXPECTED_PAGE_PERSON_SEX)
                        .passport(expectedPassport)
                        .livingHouse(expectedLivingHouse)
                        .possessedHouses(expectedPossessingHouses)
                        .build()
        ), PageRequest.of(PAGE_NUMBER, PAGE_SIZE), TOTAL_ELEMENTS);
    }

    public static PostPutPersonRequestDTO inputPostPersonRequestDTO() {
        return PostPutPersonRequestDTO.builder()
                .name(EXPECTED_AND_INPUT_OUTPUT_PERSON_NAME)
                .surname(EXPECTED_AND_INPUT_OUTPUT_PERSON_SURNAME)
                .sex(EXPECTED_AND_INPUT_OUTPUT_PERSON_SEX.name())
                .passportUUID(INPUT_PERSON_PASSPORT_UUID)
                .livingHouseUUID(INPUT_PERSON_LIVING_HOUSE_UUID)
                .possessingHouseUUIDs(INPUT_PERSON_POSSESSING_HOUSE_UUIDS)
                .build();
    }

    public static PostPutPersonRequestDTO inputPutPersonRequestDTO() {
        return PostPutPersonRequestDTO.builder()
                .uuid(EXPECTED_AND_INPUT_OUTPUT_PERSON_UUID)
                .name(EXPECTED_AND_INPUT_OUTPUT_PERSON_NAME)
                .surname(EXPECTED_AND_INPUT_OUTPUT_PERSON_SURNAME)
                .sex(EXPECTED_AND_INPUT_OUTPUT_PERSON_SEX.name())
                .passportUUID(PassportTestData.REPLACEABLE_PASSPORT_UUID)
                .livingHouseUUID(HouseTestData.REPLACEABLE_HOUSE_UUID)
                .possessingHouseUUIDs(List.of(HouseTestData.REPLACEABLE_HOUSE_UUID))
                .build();
    }
}
