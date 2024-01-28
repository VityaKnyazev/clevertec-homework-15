package ru.clevertec.ecl.knyazev.integration.util;

import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.entity.type.PersonSex;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonSerciceIntegrationTestData {
    private static final String EXISTING_PERSON_UUID = "e45a120c-5c08-4715-bab5-740fc0cad9f5";

    private static final String SAVING_PERSON_NAME = "Mikle";
    private static final String SAVING_PERSON_SURNAME = "Akimov";
    private static final String SAVING_PERSON_PASSPORT_UUID = "4d184958-7a7f-4efa-adf2-1529b0c4b39c";
    private static final String SAVING_PERSON_LIVING_HOUSE_UUID = "46d2e812-80cd-4485-9fec-b20496c24963";
    private static final List<String> SAVING_PERSON_POSSESSING_HOUSE_UUIDs = new ArrayList<>() {{
       add("46d2e812-80cd-4485-9fec-b20496c24963");
       add("2005eeed-ced9-4408-b3ea-87d7358f78d1");
    }};

    private static final String UPDATING_PERSON_UUID = "45638d63-20b5-4335-9bc6-f678ad578dac";
    private static final String UPDATING_PERSON_NAME = "Genya";
    private static final String UPDATING_PERSON_SURNAME = "Ivanov";
    private static final String UPDATING_PERSON_PASSPORT_UUID = "760d95fe-66b4-4b38-abc3-b8c2294a2db3";
    private static final String UPDATING_PERSON_LIVING_HOUSE_UUID = "204647f0-caf6-45be-9512-acac4c628366";
    private static final List<String> UPDATING_PERSON_POSSESSING_HOUSE_UUIDs = new ArrayList<>() {{
        add("204647f0-caf6-45be-9512-acac4c628366");
    }};

    private static final String DELETING_PERSON_UUID ="d285046e-68f7-46b1-8bf9-d0c6e5b22148";

    public static UUID personGettingUUID() {
        return UUID.fromString(EXISTING_PERSON_UUID);
    }

    public static PostPutPersonRequestDTO personSavingRequest() {
        return PostPutPersonRequestDTO.builder()
                .name(SAVING_PERSON_NAME)
                .surname(SAVING_PERSON_SURNAME)
                .sex(PersonSex.male.name())
                .passportUUID(SAVING_PERSON_PASSPORT_UUID)
                .livingHouseUUID(SAVING_PERSON_LIVING_HOUSE_UUID)
                .possessingHouseUUIDs(SAVING_PERSON_POSSESSING_HOUSE_UUIDs)
                .build();
    }

    public static PostPutPersonRequestDTO personUpdatingRequest() {
        return PostPutPersonRequestDTO.builder()
                .uuid(UPDATING_PERSON_UUID)
                .name(UPDATING_PERSON_NAME)
                .surname(UPDATING_PERSON_SURNAME)
                .sex(PersonSex.male.name())
                .passportUUID(UPDATING_PERSON_PASSPORT_UUID)
                .livingHouseUUID(UPDATING_PERSON_LIVING_HOUSE_UUID)
                .possessingHouseUUIDs(UPDATING_PERSON_POSSESSING_HOUSE_UUIDs)
                .build();
    }

    public static UUID personDeletingUUID() {
        return UUID.fromString(DELETING_PERSON_UUID);
    }


}
