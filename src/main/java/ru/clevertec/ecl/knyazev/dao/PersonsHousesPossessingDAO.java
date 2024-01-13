package ru.clevertec.ecl.knyazev.dao;

import java.util.List;

/**
 * !!!CREATED ONLY FOR DEMONSTRATION JDBC TEMPLATE!!!
 * <p>
 * Represents data access object for persons_houses_possessing table
 */
public interface PersonsHousesPossessingDAO {
    /**
     * Find houses IDs that person with id possessing
     *
     * @param personID person id for searching possessing houses IDs
     * @return houses IDs that person with personID possessing or empty list
     */
    List<Long> findHouseIDsByPersonID(Long personID);
}
