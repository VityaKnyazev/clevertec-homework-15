package ru.clevertec.ecl.knyazev.data.http.address.response;

import lombok.Builder;

/**
 * Represents Address DTO that returns in
 * Response
 * <p>
 * HTTP method GET
 *
 *
 * @param uuid address uuid
 * @param area address area
 * @param country address country
 * @param city address city
 * @param street address street
 * @param number address number
 */
@Builder
public record GetAddressResponseDTO(
    String uuid,

    String area,

    String country,

    String city,

    String street,

    String number
) {
}
