package ru.clevertec.ecl.knyazev.util;

public class UrlTestData {
    private static final String SEARCH_PARAM = "search";

    private static final String HOUSE_REQUEST_URL = "/houses";

    private static final String PERSON_REQUEST_URL = "/persons";

    public static String getSearchParam() {
        return SEARCH_PARAM;
    }

    public static String getHouseRequestUrl() {
        return HOUSE_REQUEST_URL;
    }

    public static String getHouseRequestUrl(String houseUUID) {
        return HOUSE_REQUEST_URL + "/" + houseUUID;
    }

    public static String getHousePersonsRequestUrl(String houseUUID) {
        return HOUSE_REQUEST_URL + "/" + houseUUID + PERSON_REQUEST_URL;
    }
}
