package ru.clevertec.ecl.knyazev.data.http.house.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

/**
 * Represents House DTO that returns in
 * Request
 * <p>
 * HTTP method DELETE
 *
 * @param uuid house uuid
 */
@Builder
public record DeleteHouseRequestDTO(
        @NotNull(message = "Deleting house uuid must not be null")
        @UUID(message = "Invalid house UUID. Also must be in lower case")
        String uuid
) {
}
