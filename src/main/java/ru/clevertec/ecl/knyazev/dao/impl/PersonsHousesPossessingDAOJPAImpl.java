package ru.clevertec.ecl.knyazev.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.knyazev.dao.PersonsHousesPossessingDAO;
import ru.clevertec.ecl.knyazev.dao.exception.DAOException;

import java.util.ArrayList;
import java.util.List;

/**
 * !!!CREATED ONLY FOR DEMONSTRATION JDBC TEMPLATE!!!
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PersonsHousesPossessingDAOJPAImpl implements PersonsHousesPossessingDAO {

    private static final String FIND_POSSESSING_HOUSES = """
            SELECT house_id FROM persons_houses_possessing WHERE person_id = ?
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Long> findHouseIDsByPersonID(Long personID) {
        List<Long> houseIds = new ArrayList<>();

        try {
            houseIds = jdbcTemplate.query(FIND_POSSESSING_HOUSES,
                    new BeanPropertyRowMapper<>(Long.class),
                    personID);
        } catch (DataAccessException e) {
            log.error(String.format("%s: %s%n%s", DAOException.FIND_ALL_ERROR, e.getMessage(), e));
        }

        return houseIds;
    }
}
