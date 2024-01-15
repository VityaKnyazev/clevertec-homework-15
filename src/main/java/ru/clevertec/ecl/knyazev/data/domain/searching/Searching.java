package ru.clevertec.ecl.knyazev.data.domain.searching;

import ru.clevertec.ecl.knyazev.data.domain.searching.exception.SearchingException;

import java.util.List;

/**
 * Represents object for working with searching input fields
 * and creating searching quires
 */
public interface Searching {

    /**
     * Get SQL searching query part using given fields
     *
     * @param fields fields on which searching will be going on
     * @return string query SQL part for searching on give fields
     * @throws SearchingException when searching not using
     */
    String getSearchingQueryPart(List<String> fields) throws SearchingException;

    /**
     * Get searching value
     *
     * @return searching value for inserting
     * into searching query
     */
    String getSearchingValue();

    /**
     * Check use of searching
     *
     * @return true - if search is using, otherwise - false
     */
    Boolean useSearching();
}
