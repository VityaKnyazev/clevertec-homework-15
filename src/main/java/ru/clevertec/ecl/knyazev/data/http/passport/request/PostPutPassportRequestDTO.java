package ru.clevertec.ecl.knyazev.data.http.passport.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

/**
 * Represents Passport DTO that returns in
 * Request
 * <p>
 * HTTP method POST and PUT
 *
 * @param uuid           passport uuid
 * @param passportSeries passport series
 * @param passportNumber passport number
 */
@Builder
public record PostPutPassportRequestDTO(
        @UUID(message = "Invalid passport UUID. Also must be in lower case")
        String uuid,

        @NotNull(message = "Passport series must be not null")
        @Pattern(regexp = "^[A-Z]{2}$",
                message = "Passport series must contains only two upper case chars from A to Z")
        String passportSeries,

        @NotNull(message = "Passport number must be not null")
        @Pattern(regexp = "^[0-9]{7}$",
                message = "Passport number must contains only seven digits from 0 to 9")
        String passportNumber
) {
}
