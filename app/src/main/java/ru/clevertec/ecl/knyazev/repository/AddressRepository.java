package ru.clevertec.ecl.knyazev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.knyazev.entity.Address;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {
    Optional<Address> findByUuid(UUID uuid);

    @Modifying
    @Query(value = "DELETE FROM Address a WHERE a.uuid = :uuid")
    void deleteByUuid(UUID uuid);
}
