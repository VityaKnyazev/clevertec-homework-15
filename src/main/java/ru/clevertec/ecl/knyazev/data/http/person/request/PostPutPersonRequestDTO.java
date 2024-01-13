package ru.clevertec.ecl.knyazev.data.http.person.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

/**
 * Represents Person DTO that returns in
 * Request
 * <p>
 * HTTP method POST and PUT
 *
 * @param uuid person uuid
 * @param name person name
 * @param surname person surname
 * @param sex person sex
 * @param passportUUID person passport uuid
 * @param livingHouseUUID person living house uuid
 * @param possessingHouseUUIDs person possessing house UUIDs
 */
@Builder
public record PostPutPersonRequestDTO(
        @UUID(message = "Invalid person UUID. Also must be in lower case")
        String uuid,

        @NotBlank(message = "Person name must be not null or whitespaces")
        @Size(min = 3, max = 15, message = "Person name must contains from 3 to 15 symbols")
        String name,

        @NotBlank(message = "Person surname must be not null or whitespaces")
        @Size(min = 3, max = 15, message = "Person surname must contains from 3 to 15 symbols")
        String surname,

        @NotNull(message = "Persson sex must not be null")
        @Pattern(regexp = "^(male|female)$",
                message = "Person sex must be male or female")
        String sex,

        @NotNull(message = "Passport uuid must not be null")
        @UUID(message = "Invalid person passport UUID. Also must be in lower case")
        String passportUUID,

        @NotNull(message = "Living house uuid must not be null")
        @UUID(message = "Invalid person living house UUID. Also must be in lower case")
        String livingHouseUUID,

        @NotNull(message = "Possessing house UUIDs must not be null")
        @UUID(message = "Invalid person possessing house UUIDs. Also must be in lower case")
        List<String> possessingHouseUUIDs
) {
}
