package ru.clevertec.ecl.knyazev.data.http.house.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

/**
 * Represents House DTO that returns in
 * Request
 * <p>
 * HTTP method POST and PUT
 *
 * @param uuid house uuid
 * @param addressUUID address uuid
 */
@Builder
public record PostPutHouseRequestDTO(
        @UUID(message = "Invalid house UUID. Must be in lower case")
        String uuid,
        @NotNull(message = "Address uuid must not be null")
        @UUID(message = "Invalid address UUID. Must be in lower case")
        String addressUUID
) {
}
