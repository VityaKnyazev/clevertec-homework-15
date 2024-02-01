package ru.clevertec.ecl.knyazev.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.service.StatisticService;

import java.util.UUID;

@RestController
@RequestMapping("/statistics")
@Tag(name = "Statistic API")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticServiceImpl;

    @Operation(summary = "get all page persons that living and were live in this house")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/houses/{uuid}/tenants")
    public ResponseEntity<Page<GetPersonResponseDTO>> getAllEverLivingPersonsInHouse(@PathVariable
                                         @Parameter(name = "house uuid",
                                            required = true,
                                            description = "searching all ever living persons (tenants)" +
                                                 "in this house",
                                            example = "204647f0-caf6-45be-9512-acac4c628366")
                                         @org.hibernate.validator.constraints.UUID(message =
                                         "house id where persons living must be uuid string in lower case")
                                         String uuid,
                                         @ParameterObject
                                         Pageable pageable) {
        return ResponseEntity.ok(statisticServiceImpl.getAllEverLivingPersonsInHouse(
                UUID.fromString(uuid),
                pageable));
    }

    @Operation(summary = "get all page persons that possessing or were possess current house")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/houses/{uuid}/owners")
    public ResponseEntity<Page<GetPersonResponseDTO>> getAllEverHousePossessingPersons(@PathVariable
                                             @Parameter(name = "house uuid",
                                                     required = true,
                                                     description = "searching all ever possessing persons (owners)" +
                                                             "in this house",
                                                     example = "204647f0-caf6-45be-9512-acac4c628366")
                                             @org.hibernate.validator.constraints.UUID(message =
                                             "house id that persons possessed must be uuid string in lower case")
                                             String uuid,
                                             @ParameterObject
                                             Pageable pageable) {
        return ResponseEntity.ok(statisticServiceImpl.getAllEverHousePossessingPersons(
                UUID.fromString(uuid),
                pageable));
    }

    @Operation(summary = "get all page houses that person with given uuid living or lived")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/tenants/{uuid}/houses")
    public ResponseEntity<Page<GetHouseResponseDTO>> getAllEverPersonLivingHouses(@PathVariable
                                              @Parameter(name = "person (tenant) uuid",
                                                      required = true,
                                                      description = "searching all houses that person (tenant)" +
                                                              "with given uuid living or lived",
                                                      example = "e45a120c-5c08-4715-bab5-740fc0cad9f5")
                                              @org.hibernate.validator.constraints.UUID(message =
                                              "living person id must be uuid string in lower case")
                                              String uuid,
                                              @ParameterObject
                                              Pageable pageable) {
        return ResponseEntity.ok(statisticServiceImpl.getAllEverPersonLivingHouses(
                UUID.fromString(uuid),
                pageable));
    }

    @Operation(summary = "get all page houses that person with given uuid possessing or possessed")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/owners/{uuid}/houses")
    public ResponseEntity<Page<GetHouseResponseDTO>> getAllEverPersonPossessedHouses(@PathVariable
                                               @Parameter(name = "person (tenant) uuid",
                                                       required = true,
                                                       description = "searching all houses that person (owner)" +
                                                               "with given uuid possessing or possessed",
                                                       example = "e45a120c-5c08-4715-bab5-740fc0cad9f5")
                                               @org.hibernate.validator.constraints.UUID(message =
                                               "possessing person id must be uuid string in lower case")
                                               String uuid,
                                               @ParameterObject
                                               Pageable pageable) {
        return ResponseEntity.ok(statisticServiceImpl.getAllEverPersonPossessedHouses(
                UUID.fromString(uuid),
                pageable));
    }
}
