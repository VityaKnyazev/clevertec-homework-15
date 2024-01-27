package ru.clevertec.ecl.knyazev.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.service.PersonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
@Tag(name = "Persons API")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personServiceImpl;

    @Operation(summary = "Get person response DTO by person uuid")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/{uuid}")
    public ResponseEntity<GetPersonResponseDTO> getPerson(@PathVariable(name = "uuid")
                                                          @Parameter(name = "uuid",
                                                                  required = true,
                                                                  description = "Person uuid",
                                                                  example = "e45a120c-5c08-4715-bab5-740fc0cad9f5")
                                                          @Valid
                                                          @org.hibernate.validator.constraints.UUID(message =
                                                                  "person id must be uuid string in lower case")
                                                          String uuid) {
        return ResponseEntity.ok(personServiceImpl.get(UUID.fromString(uuid)));
    }

    @Operation(summary = "Get person possessing houses response DTOs by person uuid")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/{uuid}/houses")
    public ResponseEntity<List<GetHouseResponseDTO>> getPersonPossessingHouses(@PathVariable(name = "uuid")
                                                               @Parameter(name = "uuid",
                                                                      required = true,
                                                                      description = "Person uuid",
                                                                      example = "e45a120c-5c08-4715-bab5-740fc0cad9f5")
                                                               @Valid
                                                               @org.hibernate.validator.constraints.UUID(message =
                                                                      "person id must be uuid string in lower case")
                                                               String uuid,
                                                               @ParameterObject
                                                               Pageable pageable) {
        UUID personUUID = UUID.fromString(uuid);

        return ResponseEntity.ok(personServiceImpl.getPossessingHouses(personUUID, pageable));
    }

    @Operation(summary = "Get all person response DTOs")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping
    public ResponseEntity<List<GetPersonResponseDTO>> getAllPersons(@ParameterObject Pageable pageable) {

        return ResponseEntity.ok(personServiceImpl.getAll(pageable));
    }

    @Operation(summary = "Save person")
    @ApiResponse(responseCode = "200", description = "Successfully saved")
    @PostMapping
    public ResponseEntity<GetPersonResponseDTO> savePerson(@RequestBody
                                                           @Valid
                                                           PostPutPersonRequestDTO postPutPersonRequestDTO) {
        return ResponseEntity.ok(personServiceImpl.add(postPutPersonRequestDTO));
    }

    @Operation(summary = "Update person")
    @ApiResponse(responseCode = "200", description = "Successfully updated")
    @PutMapping
    public ResponseEntity<GetPersonResponseDTO> changeAllPerson(@RequestBody
                                                                 @Valid
                                                                 PostPutPersonRequestDTO postPutPersonRequestDTO) {
        return ResponseEntity.ok(personServiceImpl.update(postPutPersonRequestDTO));
    }

    @Operation(summary = "Delete person")
    @ApiResponse(responseCode = "204", description = "No content - successfully deleted")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePerson(@PathVariable(name = "uuid")
                                          @Parameter(name = "uuid",
                                                  required = true,
                                                  description = "person uuid",
                                                  example = "e45a120c-5c08-4715-bab5-740fc0cad9f5")
                                          @Valid
                                          @org.hibernate.validator.constraints.UUID(message =
                                                  "persson id must be uuid string in lower case")
                                          String uuid) {
        personServiceImpl.remove(UUID.fromString(uuid));

        return ResponseEntity.ok().build();
    }
}
