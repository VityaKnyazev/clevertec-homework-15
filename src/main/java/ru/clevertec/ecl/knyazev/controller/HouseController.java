package ru.clevertec.ecl.knyazev.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.domain.searching.impl.SearchingImpl;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.service.HouseService;
import ru.clevertec.ecl.knyazev.validation.group.Update;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/houses")
@Tag(name = "Houses API")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseServiceImpl;

    @Operation(summary = "Get house response DTO by house uuid")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/{uuid}")
    public ResponseEntity<GetHouseResponseDTO> getHouse(@PathVariable
                                                        @Parameter(name = "uuid",
                                                                required = true,
                                                                description = "House uuid",
                                                                example = "204647f0-caf6-45be-9512-acac4c628366")
                                                        @Valid
                                                        @org.hibernate.validator.constraints.UUID(message =
                                                                "house id must be uuid string in lower case")
                                                        String uuid) {
        return ResponseEntity.ok(houseServiceImpl.getHouseResponseDTO(UUID.fromString(uuid)));
    }

    @Operation(summary = "Get living person response DTOs by house uuid")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/{uuid}/persons")
    public ResponseEntity<List<GetPersonResponseDTO>> getHouseLivingPersons(@PathVariable
                                                                @Parameter(name = "uuid",
                                                                        required = true,
                                                                        description = "House uuid",
                                                                        example = "204647f0-caf6-45be-9512-acac4c628366")
                                                                @Valid
                                                                @org.hibernate.validator.constraints.UUID(message =
                                                                        "house id must be uuid string in lower case")
                                                                String uuid,
                                                                @ParameterObject
                                                                Pageable pageable) {
        UUID houseUUID = UUID.fromString(uuid);

        return ResponseEntity.ok(houseServiceImpl.getLivingPersons(houseUUID, pageable));
    }

    @Operation(summary = "Get all houses response DTOs")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping
    public ResponseEntity<List<GetHouseResponseDTO>> getAllHouses(@RequestParam(required = false)
                                                        @Parameter(name = "search",
                                                            description = "parameter for searching on house text fields",
                                                            example = "West")
                                                        @Valid
                                                        @Size(min = 3,
                                                            max = 50,
                                                            message =
                                                            "search argument must have length from 3 to 50 symbols")
                                                        String search,
                                                        @ParameterObject
                                                        Pageable pageable) {
        Searching searching = new SearchingImpl(search != null
                                                ? search.strip()
                                                : null);

        return ResponseEntity.ok(houseServiceImpl.getAll(pageable, searching));
    }

    @Operation(summary = "Save house")
    @ApiResponse(responseCode = "201", description = "Successfully saved")
    @PostMapping
    public ResponseEntity<GetHouseResponseDTO> saveHouse(@RequestBody
                                                         @Valid
                                                         PostPutHouseRequestDTO postPutHouseRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(houseServiceImpl.add(postPutHouseRequestDTO));
    }

    @Operation(summary = "Update house")
    @ApiResponse(responseCode = "200", description = "Successfully updated")
    @PutMapping
    public ResponseEntity<GetHouseResponseDTO> changeAllHouse(@RequestBody
                                                              @Validated({Default.class, Update.class})
                                                              PostPutHouseRequestDTO postPutHouseRequestDTO) {
        return ResponseEntity.ok(houseServiceImpl.update(postPutHouseRequestDTO));
    }

    @Operation(summary = "Delete house")
    @ApiResponse(responseCode = "204", description = "No content - successfully deleted")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteHouse(@PathVariable
                                         @Parameter(name = "uuid",
                                         required = true,
                                         description = "house uuid",
                                         example = "204647f0-caf6-45be-9512-acac4c628366")
                                         @Valid
                                         @org.hibernate.validator.constraints.UUID(message =
                                                 "house id must be uuid string in lower case")
                                         String uuid) {
        houseServiceImpl.remove(UUID.fromString(uuid));

        return ResponseEntity.noContent().build();
    }
}
