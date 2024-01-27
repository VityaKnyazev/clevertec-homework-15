package ru.clevertec.ecl.knyazev.data.http.person.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents Person DTO that returns in
 * Response
 * <p>
 * HTTP method GET
 *
 * @param uuid           person uuid
 * @param name           person name
 * @param surname        person surname
 * @param sex            person sex
 * @param passportSeries person passport series
 * @param passportNumber person passport number
 * @param createDate     person create date
 * @param updateDate     person update date
 */
@Schema(description = "Person DTO on response method GET")
public record GetPersonResponseDTO(
        @Schema(description = "person uuid",
                example = "285b3607-22be-47b0-8bbc-f1f20ee0c17b")
        String uuid,

        @Schema(description = "person name",
                example = "Jane")
        String name,

        @Schema(description = "person surname",
                example = "Smith")
        String surname,

        @Schema(description = "person sex",
                example = "female")
        String sex,

        @Schema(description = "person passport series",
                example = "CD")
        String passportSeries,

        @Schema(description = "person passport number",
                example = "7890124")
        String passportNumber,

        @Schema(description = "person passport create date",
                example = "2013-01-05T00:00:00.000")
        String createDate,

        @Schema(description = "person passport update date",
                example = "2023-01-05T00:00:00.000")
        String updateDate
) {
}
