package ru.clevertec.ecl.knyazev.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personServiceImpl;

    @GetMapping("/{uuid}")
    public ResponseEntity<GetPersonResponseDTO> getPerson(@PathVariable(name = "uuid")
                                                          @Valid
                                                          @org.hibernate.validator.constraints.UUID(message =
                                                                  "person id must be uuid string in lower case")
                                                          String uuid) {
        return ResponseEntity.ok(personServiceImpl.get(UUID.fromString(uuid)));
    }

    @GetMapping("/{uuid}/houses")
    public ResponseEntity<List<GetHouseResponseDTO>> getPersonPossessingHouses(@PathVariable(name = "uuid")
                                                                               @Valid
                                                                               @org.hibernate.validator.constraints.UUID(message =
                                                                                       "person id must be uuid string in lower case")
                                                                               String uuid,
                                                                               Pageable pageable) {
        UUID personUUID = UUID.fromString(uuid);

        return ResponseEntity.ok(personServiceImpl.getPossessingHouses(personUUID, pageable));
    }

    @GetMapping
    public ResponseEntity<List<GetPersonResponseDTO>> getAllPersons(Pageable pageable) {

        return ResponseEntity.ok(personServiceImpl.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<GetPersonResponseDTO> savePerson(@RequestBody
                                                           @Valid
                                                           PostPutPersonRequestDTO postPutPersonRequestDTO) {
        return ResponseEntity.ok(personServiceImpl.add(postPutPersonRequestDTO));
    }

    @PutMapping
    public ResponseEntity<GetPersonResponseDTO> changeAllPerson(@RequestBody
                                                                 @Valid
                                                                 PostPutPersonRequestDTO postPutPersonRequestDTO) {
        return ResponseEntity.ok(personServiceImpl.update(postPutPersonRequestDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePerson(@PathVariable(name = "uuid")
                                          @Valid
                                          @org.hibernate.validator.constraints.UUID(message =
                                                  "persson id must be uuid string in lower case")
                                          String uuid) {
        personServiceImpl.remove(UUID.fromString(uuid));

        return ResponseEntity.ok().build();
    }
}
