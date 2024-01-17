package ru.clevertec.ecl.knyazev.entity.listener;

import jakarta.persistence.PrePersist;
import ru.clevertec.ecl.knyazev.entity.Person;

import java.util.UUID;

/**
 * Listener for Person entity
 */
public class PersonListener {
    @PrePersist
    private void setUUIDBeforeSaving(Person person) {
        person.setUuid(UUID.randomUUID());
    }
}
