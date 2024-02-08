package ru.clevertec.ecl.knyazev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.knyazev.entity.House;

import java.util.Optional;
import java.util.UUID;

public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByUuid(UUID uuid);

    Page<House> findAll(Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM House h WHERE h.uuid = :uuid")
    void deleteByUuid(UUID uuid);
}
