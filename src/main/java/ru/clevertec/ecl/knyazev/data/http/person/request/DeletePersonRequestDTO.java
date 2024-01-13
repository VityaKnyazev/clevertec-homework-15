package ru.clevertec.ecl.knyazev.data.http.person.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

/**
 * Represents Person DTO that returns in
 * Request
 * <p>
 * HTTP method DELETE
 *
 * @param uuid person uuid
 */
@Builder
public record DeletePersonRequestDTO(
        @NotNull(message = "Deleting person uuid must not be null")
        @UUID(message = "Invalid person UUID. Also must be in lower case")
        String uuid
) {
}
