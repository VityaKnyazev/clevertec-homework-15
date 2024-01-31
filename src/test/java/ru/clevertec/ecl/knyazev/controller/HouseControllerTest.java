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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.service.HouseService;
import ru.clevertec.ecl.knyazev.util.HouseTestData;
import ru.clevertec.ecl.knyazev.util.PersonTestData;
import ru.clevertec.ecl.knyazev.util.UrlTestData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@WebMvcTest(value = {HouseController.class})
@RequiredArgsConstructor
public class HouseControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    HouseService houseServiceImplMock;

    @Test
    public void checkGetHouseShouldReturnHouseResponseDTOJSON() throws Exception {
        GetHouseResponseDTO expectedHouseResponseDTO = HouseTestData.expectedHouseResponseDTO();

        String houseUrl = UrlTestData.getHouseRequestUrl(expectedHouseResponseDTO.uuid());

        when(houseServiceImplMock.getHouseResponseDTO(any(UUID.class)))
                .thenReturn(expectedHouseResponseDTO);

        mockMvc.perform(get(houseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expectedHouseResponseDTO.uuid()))
                .andExpect(jsonPath("$.area").value(expectedHouseResponseDTO.area()))
                .andExpect(jsonPath("$.country").value(expectedHouseResponseDTO.country()))
                .andExpect(jsonPath("$.city").value(expectedHouseResponseDTO.city()))
                .andExpect(jsonPath("$.street").value(expectedHouseResponseDTO.street()))
                .andExpect(jsonPath("$.number").value(expectedHouseResponseDTO.number()))
                .andExpect(jsonPath("$.createDate").value(expectedHouseResponseDTO.createDate()));
    }

    @ParameterizedTest
    @MethodSource("getInvalidHouseUUIDData")
    public void checkGetHouseShouldReturnBadRequest(String invalidUUID) throws Exception {

        String houseUrl = UrlTestData.getHouseRequestUrl(invalidUUID);

        mockMvc.perform(get(houseUrl))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    @Test
    public void checkGetHouseLivingPersons() throws Exception {
        List<GetPersonResponseDTO> expectedPersonResponseDTOS = List.of(
                PersonTestData.expectedPersonResponseDTO()
        );

        String housePersonsUrl = UrlTestData.getHousePersonsRequestUrl(expectedPersonResponseDTOS.stream()
                .findFirst().get()
                .uuid());

        when(houseServiceImplMock.getLivingPersons(any(UUID.class), any(Pageable.class)))
                .thenReturn(expectedPersonResponseDTOS);

        mockMvc.perform(get(housePersonsUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @ParameterizedTest
    @MethodSource("getValidHouseSearchData")
    public void checkGetAllHousesShouldReturnAllHousesJson(String searchParamVal) throws Exception {
        List<GetHouseResponseDTO> expectedHouseResponseDTOs = HouseTestData.expectedHouseResponseDTOs();

        String houseUrl = UrlTestData.getHouseRequestUrl();
        String searchParam = UrlTestData.getSearchParam();

        when(houseServiceImplMock.getAll(any(Pageable.class), any(Searching.class)))
                .thenReturn(expectedHouseResponseDTOs);

        mockMvc.perform(get(houseUrl)
                        .param(searchParam, searchParamVal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @ParameterizedTest
    @MethodSource("getInvalidHouseSearchData")
    public void checkGetAllHousesShouldReturnBadRequest(String invalidSearchParamVal)
            throws Exception {
        String houseUrl = UrlTestData.getHouseRequestUrl();
        String searchParam = UrlTestData.getSearchParam();

        mockMvc.perform(get(houseUrl)
                        .param(searchParam, invalidSearchParamVal))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    @Test
    public void checkSaveHouseShouldReturnSavedHouseDTO() throws Exception {
        String houseUrl = UrlTestData.getHouseRequestUrl();

        GetHouseResponseDTO expectedHouseResponseDTO = HouseTestData.expectedHouseResponseDTO();

        when(houseServiceImplMock.add(any(PostPutHouseRequestDTO.class)))
                .thenReturn(expectedHouseResponseDTO);

        String jsonHouseInput = objectMapper.writeValueAsString(HouseTestData.inputPostHouseRequestDTO());

        mockMvc.perform(post(houseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHouseInput))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.uuid").value(expectedHouseResponseDTO.uuid()))
                .andExpect(jsonPath("$.area").value(expectedHouseResponseDTO.area()))
                .andExpect(jsonPath("$.country").value(expectedHouseResponseDTO.country()))
                .andExpect(jsonPath("$.city").value(expectedHouseResponseDTO.city()))
                .andExpect(jsonPath("$.street").value(expectedHouseResponseDTO.street()))
                .andExpect(jsonPath("$.number").value(expectedHouseResponseDTO.number()))
                .andExpect(jsonPath("$.createDate").value(expectedHouseResponseDTO.createDate()));
    }

    @ParameterizedTest
    @MethodSource("getInvalidPostHouseRequestDTOs")
    public void checkSaveHouseShouldReturnBadRequest(PostPutHouseRequestDTO invalidPostHouseRequestDTO)
            throws Exception {
        String houseUrl = UrlTestData.getHouseRequestUrl();

        String invalidPostHouseRequestDTOJson = objectMapper.writeValueAsString(
                invalidPostHouseRequestDTO);

        mockMvc.perform(post(houseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPostHouseRequestDTOJson))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(MethodArgumentNotValidException.class));
    }

    @Test
    public void checkUpdateHouseShouldReturnUpdatedHouseDTO() throws Exception {
        String houseUrl = UrlTestData.getHouseRequestUrl();

        GetHouseResponseDTO expectedHouseResponseDTO = HouseTestData.expectedHouseResponseDTO();

        when(houseServiceImplMock.update(any(PostPutHouseRequestDTO.class)))
                .thenReturn(expectedHouseResponseDTO);

        String jsonHouseInput = objectMapper.writeValueAsString(HouseTestData.inputPutHouseRequestDTO());

        mockMvc.perform(put(houseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHouseInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expectedHouseResponseDTO.uuid()))
                .andExpect(jsonPath("$.area").value(expectedHouseResponseDTO.area()))
                .andExpect(jsonPath("$.country").value(expectedHouseResponseDTO.country()))
                .andExpect(jsonPath("$.city").value(expectedHouseResponseDTO.city()))
                .andExpect(jsonPath("$.street").value(expectedHouseResponseDTO.street()))
                .andExpect(jsonPath("$.number").value(expectedHouseResponseDTO.number()))
                .andExpect(jsonPath("$.createDate").value(expectedHouseResponseDTO.createDate()));
    }

    @ParameterizedTest
    @MethodSource("getInvalidPutHouseRequestDTOs")
    public void checkUpdateHouseShouldReturnBadRequest(PostPutHouseRequestDTO invalidPutHouseRequestDTO)
            throws Exception {
        String houseUrl = UrlTestData.getHouseRequestUrl();

        String invalidPutHouseRequestDTOJson = objectMapper.writeValueAsString(
                invalidPutHouseRequestDTO);

        mockMvc.perform(put(houseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPutHouseRequestDTOJson))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(MethodArgumentNotValidException.class));
    }

    @Test
    public void checkDeleteHouseShouldReturnNoContentStatus() throws Exception {
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        UUID expectedDeletingHouseUUID = HouseTestData.expectedHouse().getUuid();
        String deletingHouseUrl = UrlTestData.getHouseRequestUrl(
                expectedDeletingHouseUUID.toString());

        mockMvc.perform(delete(deletingHouseUrl))
                .andExpect(status().isNoContent());

        verify(houseServiceImplMock).remove(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(expectedDeletingHouseUUID);
    }

    @ParameterizedTest
    @MethodSource("getInvalidHouseUUIDData")
    public void checkDeleteHouseShouldReturnBadRequestStatus(String invalidHouseUUID)
            throws Exception {

        String invalidDeletingHouseUrl = UrlTestData.getHouseRequestUrl(invalidHouseUUID);

        mockMvc.perform(get(invalidDeletingHouseUrl))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    private static Stream<String> getValidHouseSearchData() {
        return Stream.of(
                "Wes",
                "hello west side-east man. You are invited to our g"
        );
    }

    private static Stream<String> getInvalidHouseSearchData() {
        return Stream.of(
                null,
                "-1",
                "hs",
                " ",
                "   ",
                System.lineSeparator() + System.lineSeparator() + System.lineSeparator(),
                "hello west side-east man. You are invited to our gr"
        );
    }

    private static Stream<String> getInvalidHouseUUIDData() {
        return Stream.of(
                null,
                "-1",
                " ",
                "   ",
                System.lineSeparator(),
                "128-568-325"
        );
    }

    private static Stream<PostPutHouseRequestDTO> getInvalidPostHouseRequestDTOs() {
        return Stream.of(
                PostPutHouseRequestDTO.builder()
                        .uuid("5ccs5-ddb51515d-vd5v15d")
                        .addressUUID("0f82c9c4-b484-45be-8f92-c70c7c72f514")
                        .build(),
                PostPutHouseRequestDTO.builder()
                        .uuid("7148ff7e-d969-4b4d-ad41-96e4ab7d6d46")
                        .addressUUID(null)
                        .build(),
                PostPutHouseRequestDTO.builder()
                        .uuid("7148ff7e-d969-4b4d-ad41-96e4ab7d6d46")
                        .addressUUID("1836-5d25f6d-25d5554")
                        .build()
        );
    }

    private static Stream<PostPutHouseRequestDTO> getInvalidPutHouseRequestDTOs() {
        return Stream.concat(
                Stream.of(
                        PostPutHouseRequestDTO.builder()
                                .uuid(null)
                                .addressUUID("5cb284a7-a824-4473-b6dc-86d5114a3373")
                                .build()
                ),
                getInvalidPostHouseRequestDTOs()
        );
    }
}
