package ru.clevertec.ecl.knyazev.data.http.house.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

/**
 * Represents House DTO that returns in
 * Request
 * <p>
 * HTTP method POST and PUT
 *
 * @param uuid        house uuid
 * @param addressUUID address uuid
 */
@Schema(description = "House DTO on request methods POST, PUT")
@Builder
public record PostPutHouseRequestDTO(
        @Schema(description = "House uuid. For POST method should be null",
                example = "0929797a-e440-499e-905e-fa5e71bbe6de")
        @UUID(message = "Invalid house UUID. Must be in lower case")
        String uuid,

        @Schema(description = "House address uuid",
                example = "548f8b02-4f28-49af-864b-b50faa1c1438")
        @NotNull(message = "Address uuid must not be null")
        @UUID(message = "Invalid address UUID. Must be in lower case")
        String addressUUID
) {
}
