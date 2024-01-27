package ru.clevertec.ecl.knyazev.data.http.person.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.UUID;
import ru.clevertec.ecl.knyazev.validation.constraint.ListUUID;

import java.util.List;

/**
 * Represents Person DTO that returns in
 * Request
 * <p>
 * HTTP method POST and PUT
 *
 * @param uuid                 person uuid
 * @param name                 person name
 * @param surname              person surname
 * @param sex                  person sex
 * @param passportUUID         person passport uuid
 * @param livingHouseUUID      person living house uuid
 * @param possessingHouseUUIDs person possessing house UUIDs
 */
@Schema(description = "Person DTO on request methods POST, PUT")
@Builder
public record PostPutPersonRequestDTO(
        @Schema(description = "Person uuid. For POST method should be null",
                example = "285b3607-22be-47b0-8bbc-f1f20ee0c17b")
        @UUID(message = "Invalid person UUID. Also must be in lower case")
        String uuid,

        @Schema(description = "person name",
                example = "Jane")
        @NotBlank(message = "Person name must be not null or whitespaces")
        @Size(min = 3, max = 15, message = "Person name must contains from 3 to 15 symbols")
        String name,

        @Schema(description = "person surname",
                example = "Smith")
        @NotBlank(message = "Person surname must be not null or whitespaces")
        @Size(min = 3, max = 15, message = "Person surname must contains from 3 to 15 symbols")
        String surname,

        @Schema(description = "person sex",
                example = "female")
        @NotNull(message = "Persson sex must not be null")
        @Pattern(regexp = "^(male|female)$",
                message = "Person sex must be male or female")
        String sex,

        @Schema(description = "person passport uuid",
                example = "8cd97277-2e0a-4eba-ab22-83c3d43ec1de")
        @NotNull(message = "Passport uuid must not be null")
        @UUID(message = "Invalid person passport UUID. Also must be in lower case")
        String passportUUID,

        @Schema(description = "person living house uuid",
                example = "452053c7-f1f9-431b-a5bc-9254a93f7a59")
        @NotNull(message = "Living house uuid must not be null")
        @UUID(message = "Invalid person living house UUID. Also must be in lower case")
        String livingHouseUUID,

        @Schema(description = "person possessing houses uuid's",
                example = """
                        ['e14bdfad-2acd-4dbe-955b-8fddcbbcf388', '204647f0-caf6-45be-9512-acac4c628366']
                        """)
        @ListUUID(message = "Invalid person possessing house UUIDs list. Also must be in lower case")
        List<String> possessingHouseUUIDs
) {
}
