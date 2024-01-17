package ru.clevertec.ecl.knyazev.entity.listener;

import jakarta.persistence.PrePersist;
import ru.clevertec.ecl.knyazev.entity.House;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Listener for House entity
 */
public class HouseListener {
    @PrePersist
    private void setUUIDAndDateBeforeSaving(House house) {
        house.setUuid(UUID.randomUUID());
        house.setCreateDate(LocalDateTime.now());
    }
}
