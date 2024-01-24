package ru.clevertec.ecl.knyazev.controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticServiceImpl;

    @GetMapping("/houses/{uuid}/tenants")
    public ResponseEntity<Page<GetPersonResponseDTO>> getAllEverLivingPersonsInHouse(@PathVariable
                                         @org.hibernate.validator.constraints.UUID(message =
                                         "house id where persons living must be uuid string in lower case")
                                                                                String uuid,
                                                                                Pageable pageable) {
        return ResponseEntity.ok(statisticServiceImpl.getAllEverLivingPersonsInHouse(
                UUID.fromString(uuid),
                pageable));
    }

    @GetMapping("/houses/{uuid}/owners")
    public ResponseEntity<Page<GetPersonResponseDTO>> getAllEverHousePossessingPersons(@PathVariable
                                             @org.hibernate.validator.constraints.UUID(message =
                                             "house id that persons possessed must be uuid string in lower case")
                                                                                   String uuid,
                                                                                   Pageable pageable) {
        return ResponseEntity.ok(statisticServiceImpl.getAllEverHousePossessingPersons(
                UUID.fromString(uuid),
                pageable));
    }

    @GetMapping("/tenants/{uuid}/houses")
    public ResponseEntity<Page<GetHouseResponseDTO>> getAllEverPersonLivingHouses(@PathVariable
                                              @org.hibernate.validator.constraints.UUID(message =
                                              "living person id must be uuid string in lower case")
                                                                                   String uuid,
                                                                                   Pageable pageable) {
        return ResponseEntity.ok(statisticServiceImpl.getAllEverPersonLivingHouses(
                UUID.fromString(uuid),
                pageable));
    }

    @GetMapping("/owners/{uuid}/houses")
    public ResponseEntity<Page<GetHouseResponseDTO>> getAllEverPersonPossessedHouses(@PathVariable
                                               @org.hibernate.validator.constraints.UUID(message =
                                               "possessing person id must be uuid string in lower case")
                                                                              String uuid,
                                                                              Pageable pageable) {
        return ResponseEntity.ok(statisticServiceImpl.getAllEverPersonPossessedHouses(
                UUID.fromString(uuid),
                pageable));
    }
}
