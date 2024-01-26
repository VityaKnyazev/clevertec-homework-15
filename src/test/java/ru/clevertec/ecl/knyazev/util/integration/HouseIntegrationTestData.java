package ru.clevertec.ecl.knyazev.util.integration;

import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;

import java.util.UUID;

public class HouseIntegrationTestData {
    private static final String EXISTING_HOUSE_UUID = "e14bdfad-2acd-4dbe-955b-8fddcbbcf388";

    private static final String SAVING_HOUSE_ADDRESS_UUID = "f1a7308b-92bc-4a50-8559-7cb87a7a7436";

    private static final String UPDATING_HOUSE_UUID = "9850dd00-895e-452b-9b0e-d3ade6680d77";
    private static final String UPDATING_HOUSE_ADDRESS_UUID ="63efdd52-1e58-4066-bde3-b9507f5499b3";

    private static final String DELETING_HOUSE_UUID ="70847027-c60f-4fb9-a65f-73cb657893b9";

    public static UUID houseGettingUUID() {
        return UUID.fromString(EXISTING_HOUSE_UUID);
    }

    public static PostPutHouseRequestDTO houseSavingRequest() {
        return PostPutHouseRequestDTO.builder()
                .addressUUID(SAVING_HOUSE_ADDRESS_UUID)
                .build();
    }

    public static PostPutHouseRequestDTO houseUpdatingRequest() {
        return PostPutHouseRequestDTO.builder()
                .uuid(UPDATING_HOUSE_UUID)
                .addressUUID(UPDATING_HOUSE_ADDRESS_UUID)
                .build();
    }

    public static UUID houseDeletingUUID() {
        return UUID.fromString(DELETING_HOUSE_UUID);
    }


}
