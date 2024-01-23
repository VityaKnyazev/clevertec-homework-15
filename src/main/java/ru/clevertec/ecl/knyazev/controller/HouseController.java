package ru.clevertec.ecl.knyazev.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.domain.searching.impl.SearchingImpl;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.service.HouseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseServiceImpl;

    @GetMapping("/{uuid}")
    public ResponseEntity<GetHouseResponseDTO> getHouse(@PathVariable(name = "uuid")
                                                        @Valid
                                                        @org.hibernate.validator.constraints.UUID(message =
                                                                "house id must be uuid string in lower case")
                                                        String uuid) {
        return ResponseEntity.ok(houseServiceImpl.getHouseResponseDTO(UUID.fromString(uuid)));
    }

    @GetMapping("/{uuid}/persons")
    public ResponseEntity<List<GetPersonResponseDTO>> getHouseLivingPersons(@PathVariable(name = "uuid")
                                                                            @Valid
                                                                            @org.hibernate.validator.constraints.UUID(message =
                                                                                    "house id must be uuid string in lower case")
                                                                            String uuid,
                                                                            Pageable pageable) {
        UUID houseUUID = UUID.fromString(uuid);

        return ResponseEntity.ok(houseServiceImpl.getLivingPersons(houseUUID, pageable));
    }

    @GetMapping
    public ResponseEntity<List<GetHouseResponseDTO>> getAllHouses(@RequestParam(required = false, name = "search")
                                                    @Valid
                                                    @Size(min = 3,
                                                            max = 50,
                                                            message =
                                                            "search argument must have length from 3 to 50 symbols")
                                                    String search,
                                                    Pageable pageable) {
        Searching searching = new SearchingImpl(search);

        return ResponseEntity.ok(houseServiceImpl.getAll(pageable, searching));
    }

    @PostMapping
    public ResponseEntity<GetHouseResponseDTO> saveHouse(@RequestBody
                                                         @Valid
                                                         PostPutHouseRequestDTO postPutHouseRequestDTO) {
        return ResponseEntity.ok(houseServiceImpl.add(postPutHouseRequestDTO));
    }

    @PutMapping
    public ResponseEntity<GetHouseResponseDTO> changeAllHouse(@RequestBody
                                                              @Valid
                                                              PostPutHouseRequestDTO postPutHouseRequestDTO) {
        return ResponseEntity.ok(houseServiceImpl.update(postPutHouseRequestDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteHouse(@PathVariable(name = "uuid")
                                         @Valid
                                         @org.hibernate.validator.constraints.UUID(message =
                                                 "house id must be uuid string in lower case")
                                         String uuid) {
        houseServiceImpl.remove(UUID.fromString(uuid));

        return ResponseEntity.ok().build();
    }
}
