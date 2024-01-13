package ru.clevertec.ecl.knyazev.data.http.address.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

/**
 * Represents Address DTO that returns in
 * Request
 * <p>
 * HTTP method DELETE
 *
 * @param uuid address uuid
 */
@Builder
public record DeleteAddressRequestDTO(
        @NotNull(message = "Deleting address uuid must not be null")
        @UUID(message = "Invalid address UUID. Also must be in lower case")
        String uuid
) {
}
