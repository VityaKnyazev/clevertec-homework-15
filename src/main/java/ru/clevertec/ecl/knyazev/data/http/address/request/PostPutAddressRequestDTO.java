package ru.clevertec.ecl.knyazev.data.http.address.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

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
@Builder
public record PostPutAddressRequestDTO(
        @UUID(message = "Invalid address UUID. Also must be in lower case")
        String uuid,

        @NotBlank(message = "Address area must be not null or whitespaces")
        @Size(min = 3, max = 20,
                message = "Address area must contains from 3 to 20 symbols")
        String area,

        @NotBlank(message = "Address country must be not null or whitespaces")
        @Size(min = 3, max = 25,
                message = "Address country must contains from 3 to 25 symbols")
        String country,

        @NotBlank(message = "Address city must be not null or whitespaces")
        @Size(min = 3, max = 20,
                message = "Address city must contains from 3 to 20 symbols")
        String city,

        @NotBlank(message = "Address street must be not null or whitespaces")
        @Size(min = 3, max = 30,
                message = "Address street must contains from 3 to 30 symbols")
        String street,

        @NotNull(message = "Address number must be not null")
        @Positive(message = "Address number must be positive")
        String number
) {
}
