package ru.clevertec.ecl.knyazev.entity.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import ru.clevertec.ecl.knyazev.entity.Passport;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Listener for Passport entity
 */
public class PassportListener {
    @PrePersist
    private void setUUIDAndDateBeforeSaving(Passport passport) {
        passport.setUuid(UUID.randomUUID());
        passport.setCreateDate(LocalDateTime.now());
    }

    @PreUpdate
    private void setDateBeforeUpdating(Passport passport) {
        passport.setUpdateDate(LocalDateTime.now());
    }
}
