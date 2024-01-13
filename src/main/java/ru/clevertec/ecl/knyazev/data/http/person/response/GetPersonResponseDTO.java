package ru.clevertec.ecl.knyazev.data.http.person.response;

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
public record GetPersonResponseDTO(
        String uuid,

        String name,

        String surname,

        String sex,

        String passportSeries,

        String passportNumber,

        String createDate,

        String updateDate
) {
}
