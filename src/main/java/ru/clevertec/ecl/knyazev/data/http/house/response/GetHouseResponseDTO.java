package ru.clevertec.ecl.knyazev.data.http.house.response;

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
@Builder
public record GetHouseResponseDTO(
        String uuid,

        String area,

        String country,

        String city,

        String street,

        Integer number,

        String createDate
) {
}
