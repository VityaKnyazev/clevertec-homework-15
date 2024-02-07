package ru.clevertec.ecl.knyazev.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.filter.impl.AddressHTTPPatchFilter;
import ru.clevertec.ecl.knyazev.service.StatisticService;
import ru.clevertec.ecl.knyazev.util.HouseTestData;
import ru.clevertec.ecl.knyazev.util.PersonTestData;
import ru.clevertec.ecl.knyazev.util.UrlTestData;

import java.util.UUID;
import java.util.stream.Stream;


@WebMvcTest(value = {StatisticController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                                value = {AddressHTTPPatchFilter.class})})
@RequiredArgsConstructor
public class StatisticControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    StatisticService statisticServiceImplMock;

    @Test
    public void checkGetAllEverLivingPersonsInHouseShouldReturnPagePersonsResponseDTOsJSON() throws Exception {
        Page<GetPersonResponseDTO> expectedPagePersonResponseDTOS =
                PersonTestData.expectedPagePersonResponseDTOs();

        when(statisticServiceImplMock.getAllEverLivingPersonsInHouse(any(UUID.class), any(Pageable.class)))
                .thenReturn(expectedPagePersonResponseDTOS);

        UUID expectedLivingHouseUUID = HouseTestData.expectedHouse().getUuid();
        String housePersonsUrl = UrlTestData.getStatisticHouseLivingPersonsRequestUrl(expectedLivingHouseUUID
                .toString());

        mockMvc.perform(get(housePersonsUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)));
    }

    @ParameterizedTest
    @MethodSource("getInvalidUUIDData")
    public void checkGetAllEverLivingPersonsInHouseShouldReturnBadRequest(String invalidUUID) throws Exception {

        String housePersonsUrl = UrlTestData.getStatisticHouseLivingPersonsRequestUrl(invalidUUID);

        mockMvc.perform(get(housePersonsUrl))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    @Test
    public void checkGetAllEverHousePossessingPersonsShouldReturnPagePersonsResponseDTOsJSON() throws Exception {
        Page<GetPersonResponseDTO> expectedPagePersonResponseDTOS =
                PersonTestData.expectedPagePersonResponseDTOs();

        when(statisticServiceImplMock.getAllEverHousePossessingPersons(any(UUID.class), any(Pageable.class)))
                .thenReturn(expectedPagePersonResponseDTOS);

        UUID expectedOwnerHouseUUID = HouseTestData.expectedHouse().getUuid();
        String housePersonsUrl = UrlTestData.getStatisticHousePossessingPersonsRequestUrl(expectedOwnerHouseUUID
                .toString());

        mockMvc.perform(get(housePersonsUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)));
    }

    @ParameterizedTest
    @MethodSource("getInvalidUUIDData")
    public void checkGetAllEverHousePossessingPersonsShouldReturnBadRequest(String invalidUUID) throws Exception {

        String housePersonsUrl = UrlTestData.getStatisticHousePossessingPersonsRequestUrl(invalidUUID);

        mockMvc.perform(get(housePersonsUrl))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    @Test
    public void checkGetAllEverPersonLivingHousesShouldReturnPageHousesResponseDTOsJSON() throws Exception {
        Page<GetHouseResponseDTO> expectedPageHouseResponseDTOS =
                HouseTestData.expectedPageHouseResponseDTOs();

        when(statisticServiceImplMock.getAllEverPersonLivingHouses(any(UUID.class), any(Pageable.class)))
                .thenReturn(expectedPageHouseResponseDTOS);

        UUID expectedTenantUUID = PersonTestData.expectedPerson().getUuid();
        String tenantHousesUrl = UrlTestData.getStatisticPersonLivingHousesRequestUrl(expectedTenantUUID
                .toString());

        mockMvc.perform(get(tenantHousesUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)));
    }

    @ParameterizedTest
    @MethodSource("getInvalidUUIDData")
    public void checkGetAllEverPersonLivingHousesShouldReturnBadRequest(String invalidUUID) throws Exception {
        String personHousesUrl = UrlTestData.getStatisticPersonLivingHousesRequestUrl(invalidUUID);

        mockMvc.perform(get(personHousesUrl))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    @Test
    public void checkGetAllEverPersonPossessedHousesShouldReturnPageHousesResponseDTOsJSON() throws Exception {
        Page<GetHouseResponseDTO> expectedPageHouseResponseDTOS =
                HouseTestData.expectedPageHouseResponseDTOs();

        when(statisticServiceImplMock.getAllEverPersonPossessedHouses(any(UUID.class), any(Pageable.class)))
                .thenReturn(expectedPageHouseResponseDTOS);

        UUID expectedOwnerUUID = PersonTestData.expectedPerson().getUuid();
        String ownerHousesUrl = UrlTestData.getStatisticPersonPossessingHousesRequestUrl(expectedOwnerUUID
                .toString());

        mockMvc.perform(get(ownerHousesUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)));
    }

    @ParameterizedTest
    @MethodSource("getInvalidUUIDData")
    public void checkGetAllEverPersonPossessedHousesShouldReturnBadRequest(String invalidUUID) throws Exception {
        String personHousesUrl = UrlTestData.getStatisticPersonPossessingHousesRequestUrl(invalidUUID);

        mockMvc.perform(get(personHousesUrl))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertThat(result.getResolvedException())
                        .isExactlyInstanceOf(HandlerMethodValidationException.class));
    }

    private static Stream<String> getInvalidUUIDData() {
        return Stream.of(
                null,
                "-1",
                " ",
                "   ",
                System.lineSeparator(),
                "128-568-325"
        );
    }
}
