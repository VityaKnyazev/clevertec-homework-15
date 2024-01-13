package ru.clevertec.ecl.knyazev.data.http.passport.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

/**
 * Represents Passport DTO that returns in
 * Request
 * <p>
 * HTTP method DELETE
 *
 * @param uuid passport uuid
 */
@Builder
public record DeletePassportRequestDTO(
        @NotNull(message = "Deleting passport uuid must not be null")
        @UUID(message = "Invalid passport UUID. Also must be in lower case")
        String uuid
) {
}
