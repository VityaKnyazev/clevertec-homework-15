package ru.clevertec.ecl.knyazev.data.http.passport.response;

import lombok.Builder;

/**
 * Represents Passport DTO that returns in
 * Response
 * <p>
 * HTTP method GET
 *
 * @param uuid passport uuid
 * @param passportSeries passport series
 * @param passportNumber passport number
 */
@Builder
public record GetPassportResponseDTO(
        String uuid,

        String passportSeries,

        String passportNumber,

        String createDate,

        String updateDate
) {
}
