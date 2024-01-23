package ru.clevertec.ecl.knyazev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.knyazev.entity.Passport;

import java.util.Optional;
import java.util.UUID;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    Optional<Passport> findByUuid(UUID uuid);

    Page<Passport> findAll(Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM Address a WHERE a.uuid = :uuid")
    void deleteByUuid(UUID uuid);
}
