package ru.clevertec.ecl.knyazev.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.filter.impl.AddressHTTPPatchFilter;
import ru.clevertec.ecl.knyazev.service.PersonService;
import ru.clevertec.ecl.knyazev.util.HouseTestData;
import ru.clevertec.ecl.knyazev.util.PersonTestData;
import ru.clevertec.ecl.knyazev.util.UrlTestData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@WebMvcTest(value = {PersonController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                                value = {AddressHTTPPatchFilter.class})})
@RequiredArgsConstructor
public class PersonControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    PersonService personServiceImplMock;

    @Test
    public void checkGetPersonShouldReturnPersonResponseDTOJSON() throws Exception {
        GetPersonResponseDTO expectedPersonResponseDTO = PersonTestData.expectedPersonResponseDTO();

        String personUrl = UrlTestData.getPersonRequestUrl(expectedPersonResponseDTO.uuid());

        when(personServiceImplMock.get(any(UUID.class)))
                .thenReturn(expectedPersonResponseDTO);

        mockMvc.perform(get(personUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expectedPersonResponseDTO.uuid()))
                .andExpect(jsonPath("$.name").value(expectedPersonResponseDTO.name()))
                .andExpect(jsonPath("$.surname").value(expectedPersonResponseDTO.surname()))
                .andExpect(jsonPath("$.sex").value(expectedPersonResponseDTO.sex()))
                .andExpect(jsonPath("$.passportSeries").value(expectedPersonResponseDTO.passportSeries()))
                .andExpect(jsonPath("$.passportNumber").value(expectedPersonResponseDTO.passportNumber()))
                .andExpect(jsonPath("$.createDate").value(expectedPersonResponseDTO.createDate()))
                .andExpect(jsonPath("$.updateDate", Matchers.nullValue()));
    }

    @ParameterizedTest
    @MethodSource("getInvalidPersonUUIDData")
    public void checkGetPersonShouldReturnBadRequest(String invalidUUID) throws Exception {

        String personUrl = UrlTestData.getPersonRequestUrl(invalidUUID);

        mockMvc.perform(get(personUrl))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    @Test
    public void checkGetPersonPossessingHousesShouldReturnHouseResponseDTOsJSON() throws Exception {
        List<GetHouseResponseDTO> expectedHouseResponseDTOS = List.of(
                HouseTestData.expectedHouseResponseDTO()
        );

        String personHousesUrl = UrlTestData.getPersonHousesRequestUrl(expectedHouseResponseDTOS.stream()
                .findFirst().get()
                .uuid());

        when(personServiceImplMock.getPossessingHouses(any(UUID.class), any(Pageable.class)))
                .thenReturn(expectedHouseResponseDTOS);

        mockMvc.perform(get(personHousesUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void checkGetAllPersonsShouldReturnAllPersonsJson() throws Exception {
        List<GetPersonResponseDTO> expectedPersonResponseDTOs = PersonTestData.expectedPersonResponseDTOs();

        String personUrl = UrlTestData.getPersonRequestUrl();

        when(personServiceImplMock.getAll(any(Pageable.class)))
                .thenReturn(expectedPersonResponseDTOs);

        mockMvc.perform(get(personUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void checkSavePersonShouldReturnSavedPersonDTO() throws Exception {
        String perssonUrl = UrlTestData.getPersonRequestUrl();

        GetPersonResponseDTO expectedPersonResponseDTO = PersonTestData.expectedPersonResponseDTO();

        when(personServiceImplMock.add(any(PostPutPersonRequestDTO.class)))
                .thenReturn(expectedPersonResponseDTO);

        String jsonPersonInput = objectMapper.writeValueAsString(PersonTestData.inputPostPersonRequestDTO());

        mockMvc.perform(post(perssonUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPersonInput))
                .andExpect(jsonPath("$.uuid").value(expectedPersonResponseDTO.uuid()))
                .andExpect(jsonPath("$.name").value(expectedPersonResponseDTO.name()))
                .andExpect(jsonPath("$.surname").value(expectedPersonResponseDTO.surname()))
                .andExpect(jsonPath("$.sex").value(expectedPersonResponseDTO.sex()))
                .andExpect(jsonPath("$.passportSeries").value(expectedPersonResponseDTO.passportSeries()))
                .andExpect(jsonPath("$.passportNumber").value(expectedPersonResponseDTO.passportNumber()))
                .andExpect(jsonPath("$.createDate").value(expectedPersonResponseDTO.createDate()))
                .andExpect(jsonPath("$.updateDate", Matchers.nullValue()));
    }

    @ParameterizedTest
    @MethodSource("getInvalidPostPersonRequestDTOs")
    public void checkSaveHouseShouldReturnBadRequest(PostPutPersonRequestDTO invalidPostPersonRequestDTO)
            throws Exception {
        String personUrl = UrlTestData.getPersonRequestUrl();

        String invalidPostPersonRequestDTOJson = objectMapper.writeValueAsString(
                invalidPostPersonRequestDTO);

        mockMvc.perform(post(personUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPostPersonRequestDTOJson))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(MethodArgumentNotValidException.class));
    }

    @Test
    public void checkUpdatePersonShouldReturnUpdatedPersonDTO() throws Exception {
        String personUrl = UrlTestData.getPersonRequestUrl();

        GetPersonResponseDTO expectedPersonResponseDTO = PersonTestData.expectedPersonResponseDTO();

        when(personServiceImplMock.update(any(PostPutPersonRequestDTO.class)))
                .thenReturn(expectedPersonResponseDTO);

        String jsonPersonInput = objectMapper.writeValueAsString(PersonTestData.inputPutPersonRequestDTO());

        mockMvc.perform(put(personUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPersonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expectedPersonResponseDTO.uuid()))
                .andExpect(jsonPath("$.name").value(expectedPersonResponseDTO.name()))
                .andExpect(jsonPath("$.surname").value(expectedPersonResponseDTO.surname()))
                .andExpect(jsonPath("$.sex").value(expectedPersonResponseDTO.sex()))
                .andExpect(jsonPath("$.passportSeries").value(expectedPersonResponseDTO.passportSeries()))
                .andExpect(jsonPath("$.passportNumber").value(expectedPersonResponseDTO.passportNumber()))
                .andExpect(jsonPath("$.createDate").value(expectedPersonResponseDTO.createDate()))
                .andExpect(jsonPath("$.updateDate", Matchers.nullValue()));
    }

    @ParameterizedTest
    @MethodSource("getInvalidPutPersonRequestDTOs")
    public void checkUpdatePersonShouldReturnBadRequest(PostPutPersonRequestDTO invalidPutPersonRequestDTO)
            throws Exception {
        String personUrl = UrlTestData.getPersonRequestUrl();

        String invalidPutPersonRequestDTOJson = objectMapper.writeValueAsString(
                invalidPutPersonRequestDTO);

        mockMvc.perform(put(personUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPutPersonRequestDTOJson))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(MethodArgumentNotValidException.class));
    }

    @Test
    public void checkDeletePersonShouldReturnNoContentStatus() throws Exception {
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        UUID expectedDeletingPersonUUID = PersonTestData.expectedPerson().getUuid();
        String deletingPersonUrl = UrlTestData.getPersonRequestUrl(
                expectedDeletingPersonUUID.toString());

        mockMvc.perform(delete(deletingPersonUrl))
                .andExpect(status().isNoContent());

        verify(personServiceImplMock).remove(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(expectedDeletingPersonUUID);
    }

    @ParameterizedTest
    @MethodSource("getInvalidPersonUUIDData")
    public void checkDeletePersonShouldReturnBadRequestStatus(String invalidPersonUUID)
            throws Exception {

        String invalidDeletingPersonUrl = UrlTestData.getPersonRequestUrl(invalidPersonUUID);

        mockMvc.perform(get(invalidDeletingPersonUrl))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    private static Stream<String> getInvalidPersonUUIDData() {
        return Stream.of(
                null,
                "-1",
                " ",
                "   ",
                System.lineSeparator(),
                "128-568-325"
        );
    }

    private static Stream<PostPutPersonRequestDTO> getInvalidPostPersonRequestDTOs() {
        return Stream.of(
                PostPutPersonRequestDTO.builder()
                        .uuid("5ccs5-ddb51515d-vd5v15d")
                        .name("Igor")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),

                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name(null)
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("   ")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Xz")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Zelemhan Ibn God")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),

                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname(null)
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("   ")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Na")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Nasrala Alya Mag")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),

                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Maksimov")
                        .sex(null)
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Maksimov")
                        .sex("lgbt")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),

                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID(null)
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("12fdvd8-dvds554-415c")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),

                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID(null)
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),
                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("ds2154sf-5151sf5-sf55sm")
                        .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                        .build(),

                PostPutPersonRequestDTO.builder()
                        .uuid("3368aa6a-81b5-417e-9e8b-ba5682c3a7f6")
                        .name("Igor")
                        .surname("Maksimov")
                        .sex("male")
                        .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                        .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                        .possessingHouseUUIDs(List.of("2d5451s-21d5451-fs2f15s12"))
                        .build()
        );
    }

    private static Stream<PostPutPersonRequestDTO> getInvalidPutPersonRequestDTOs() {
        return Stream.concat(
                Stream.of(
                        PostPutPersonRequestDTO.builder()
                                .uuid(null)
                                .name("Igor")
                                .surname("Maksimov")
                                .sex("male")
                                .passportUUID("6c4a809d-137c-4489-a897-e6a791532900")
                                .livingHouseUUID("d2a4b71b-d5c9-48dc-a0fe-37fdb0627835")
                                .possessingHouseUUIDs(List.of("e68cec88-c940-48a3-80e9-2a0b51ac0de3"))
                                .build()
                ),
                getInvalidPostPersonRequestDTOs()
        );
    }
}
