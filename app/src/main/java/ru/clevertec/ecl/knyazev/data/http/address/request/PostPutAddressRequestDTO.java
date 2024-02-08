package ru.clevertec.ecl.knyazev.data.http.address.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;
import ru.clevertec.ecl.knyazev.validation.group.Update;

/**
 * Represents Address DTO that returns in
 * Request
 * <p>
 * HTTP method POST and PUT
 *
 * @param uuid    address uuid
 * @param area    address area
 * @param country address country
 * @param city    address city
 * @param street  address street
 * @param number  address number
 */
@Schema(description = "Address DTO on request methods POST, PUT")
@Builder
public record PostPutAddressRequestDTO(
        @Schema(description = "Address uuid",
        example = "0c1d7573-ab23-467b-884f-3d1ee62ffebb")
        @UUID(message = "Invalid address UUID. Also must be in lower case")
        @NotNull(groups = {Update.class}, message = "Address uuid must not be null")
        String uuid,

        @Schema(description = "address area",
                example = "East")
        @NotBlank(message = "Address area must be not null or whitespaces")
        @Size(min = 3, max = 20,
                message = "Address area must contains from 3 to 20 symbols")
        String area,

        @Schema(description = "address country",
                example = "Italy")
        @NotBlank(message = "Address country must be not null or whitespaces")
        @Size(min = 3, max = 25,
                message = "Address country must contains from 3 to 25 symbols")
        String country,

        @Schema(description = "address city",
                example = "Rome")
        @NotBlank(message = "Address city must be not null or whitespaces")
        @Size(min = 3, max = 20,
                message = "Address city must contains from 3 to 20 symbols")
        String city,

        @Schema(description = "address street",
                example = "St. Franchesko")
        @NotBlank(message = "Address street must be not null or whitespaces")
        @Size(min = 3, max = 30,
                message = "Address street must contains from 3 to 30 symbols")
        String street,

        @Schema(description = "address number",
                example = "12")
        @NotNull(message = "Address number must be not null")
        @Positive(message = "Address number must be positive")
        String number
) {
}
