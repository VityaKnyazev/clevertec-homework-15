package ru.clevertec.ecl.knyazev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.knyazev.entity.HouseHistory;
import ru.clevertec.ecl.knyazev.entity.type.PersonHouseType;

import java.util.UUID;

public interface HouseHistoryRepository extends JpaRepository<HouseHistory, Long> {

    @EntityGraph(value = "HouseHistory.person.passport")
    Page<HouseHistory> findAllByHouseUuidAndType(UUID houseUuid,
                                                 PersonHouseType type,
                                                 Pageable pageable);

    @EntityGraph("HouseHistory.house.address")
    Page<HouseHistory> findAllByPersonUuidAndType(UUID personUuid,
                                                  PersonHouseType type,
                                                  Pageable pageable);
}
