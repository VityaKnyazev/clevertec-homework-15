package ru.clevertec.ecl.knyazev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.knyazev.entity.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUuid(UUID uuid);

    Page<Person> findAll(Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM Person p WHERE p.uuid = :uuid")
    void deleteByUuid(UUID uuid);
}
