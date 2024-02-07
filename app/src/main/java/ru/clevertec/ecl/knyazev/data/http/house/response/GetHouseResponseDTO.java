package ru.clevertec.ecl.knyazev.data.http.house.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Represents House DTO that returns in
 * Response
 * <p>
 * HTTP method GET
 *
 * @param uuid       house uuid
 * @param area       house area
 * @param country    house country
 * @param city       house city
 * @param street     house street
 * @param number     house number
 * @param createDate house create date
 */
@Schema(description = "House DTO on response method GET")
@Builder
public record GetHouseResponseDTO(

        @Schema(description = "House uuid",
                example = "70847027-c60f-4fb9-a65f-73cb657893b9")
        String uuid,

        @Schema(description = "House area",
                example = "West")
        String area,

        @Schema(description = "House country",
                example = "Australia")
        String country,

        @Schema(description = "House city",
                example = "Sydney")
        String city,

        @Schema(description = "House street",
                example = "George Street")
        String street,

        @Schema(description = "House number",
                example = "345")
        Integer number,

        @Schema(description = "House create date",
                example = "2023-01-05T00:00:00.000")
        String createDate
) {
}
