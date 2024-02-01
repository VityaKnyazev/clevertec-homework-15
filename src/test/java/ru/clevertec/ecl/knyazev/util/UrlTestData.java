package ru.clevertec.ecl.knyazev.util;

public class UrlTestData {
    private static final String SLASH = "/";

    private static final String SEARCH_PARAM = "search";

    private static final String HOUSE_REQUEST_URL = "/houses";

    private static final String PERSON_REQUEST_URL = "/persons";

    private static final String STATISTIC_REQUEST_URL = "/statistics";
    private static final String TENANTS_REQUEST_URL = "/tenants";
    private static final String OWNERS_REQUEST_URL = "/owners";



    public static String getSearchParam() {
        return SEARCH_PARAM;
    }

    public static String getHouseRequestUrl() {
        return HOUSE_REQUEST_URL;
    }

    public static String getHouseRequestUrl(String houseUUID) {
        return HOUSE_REQUEST_URL + SLASH + houseUUID;
    }

    public static String getPersonRequestUrl() {
        return PERSON_REQUEST_URL;
    }

    public static String getPersonRequestUrl(String personUUID) {
        return PERSON_REQUEST_URL + SLASH + personUUID;
    }

    public static String getHousePersonsRequestUrl(String houseUUID) {
        return HOUSE_REQUEST_URL + SLASH + houseUUID + PERSON_REQUEST_URL;
    }

    public static String getPersonHousesRequestUrl(String personUUID) {
        return PERSON_REQUEST_URL + SLASH + personUUID + HOUSE_REQUEST_URL;
    }

    public static String getStatisticHouseLivingPersonsRequestUrl(String houseUUID) {
        return STATISTIC_REQUEST_URL +
                HOUSE_REQUEST_URL +
                SLASH + houseUUID
                + TENANTS_REQUEST_URL;
    }

    public static String getStatisticHousePossessingPersonsRequestUrl(String houseUUID) {
        return STATISTIC_REQUEST_URL +
                HOUSE_REQUEST_URL +
                SLASH + houseUUID
                + OWNERS_REQUEST_URL;
    }

    public static String getStatisticPersonLivingHousesRequestUrl(String personUUID) {
        return STATISTIC_REQUEST_URL +
                TENANTS_REQUEST_URL +
                SLASH + personUUID
                + HOUSE_REQUEST_URL;
    }

    public static String getStatisticPersonPossessingHousesRequestUrl(String personUUID) {
        return STATISTIC_REQUEST_URL +
                OWNERS_REQUEST_URL +
                SLASH + personUUID
                + HOUSE_REQUEST_URL;
    }
}
