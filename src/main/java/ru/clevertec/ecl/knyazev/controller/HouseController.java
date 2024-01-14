package ru.clevertec.ecl.knyazev.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.knyazev.config.PagingProperties;
import ru.clevertec.ecl.knyazev.data.http.house.request.DeleteHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.request.PostPutHouseRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.data.http.person.response.GetPersonResponseDTO;
import ru.clevertec.ecl.knyazev.pagination.Paging;
import ru.clevertec.ecl.knyazev.pagination.impl.PagingImpl;
import ru.clevertec.ecl.knyazev.service.HouseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HouseController {

    private final HouseService houseServiceImpl;

    private final PagingProperties pagingPropertiesImpl;

    @GetMapping("/houses/{uuid}")
    public ResponseEntity<GetHouseResponseDTO> getHouse(@PathVariable(name = "uuid")
                                                        @Valid
                                                        @org.hibernate.validator.constraints.UUID(message =
                                                                "house id must be uuid string in lower case")
                                                        String uuid) {
        return ResponseEntity.ok(houseServiceImpl.get(UUID.fromString(uuid)));
    }

    @GetMapping("/houses/{uuid}/persons")
    public ResponseEntity<List<GetPersonResponseDTO>> getHouseLivingPersons(@PathVariable(name = "uuid")
                                                            @Valid
                                                            @org.hibernate.validator.constraints.UUID(message =
                                                                   "house id must be uuid string in lower case")
                                                            String uuid,
                                                            @RequestParam(required = false, name = "page")
                                                            @Valid
                                                            @Positive(message = "page number must be from 1")
                                                            Integer page,
                                                            @RequestParam(required = false, name = "page_size")
                                                            @Valid
                                                            @Positive(message = "page size must be from 1")
                                                            Integer pageSize) {
        UUID houseUUID = UUID.fromString(uuid);
        Paging paging = new PagingImpl(page, pageSize, pagingPropertiesImpl);

        return ResponseEntity.ok(houseServiceImpl.getLivingPersons(houseUUID, paging));
    }

    @GetMapping("/houses")
    public ResponseEntity<List<GetHouseResponseDTO>> getAllHouses(@RequestParam(required = false, name = "page")
                                                                   @Valid
                                                                   @Positive(message = "page number must be from 1")
                                                                   Integer page,
                                                                   @RequestParam(required = false, name = "page_size")
                                                                   @Valid
                                                                   @Positive(message = "page size must be from 1")
                                                                   Integer pageSize) {
        Paging paging = new PagingImpl(page, pageSize, pagingPropertiesImpl);

        return ResponseEntity.ok(houseServiceImpl.getAll(paging));
    }

    @PostMapping("/houses")
    public ResponseEntity<GetHouseResponseDTO> saveHouse(@RequestBody
                                                         @Valid
                                                         PostPutHouseRequestDTO postPutHouseRequestDTO) {
        return ResponseEntity.ok(houseServiceImpl.add(postPutHouseRequestDTO));
    }

    @PutMapping("/houses")
    public ResponseEntity<GetHouseResponseDTO> changeAllHouse(@RequestBody
                                                              @Valid
                                                              PostPutHouseRequestDTO postPutHouseRequestDTO) {
        return ResponseEntity.ok(houseServiceImpl.update(postPutHouseRequestDTO));
    }

    @DeleteMapping("/houses")
    public ResponseEntity<?> deleteHouse(@RequestBody
                                         @Valid
                                         DeleteHouseRequestDTO deleteHouseRequestDTO) {
        houseServiceImpl.remove(deleteHouseRequestDTO);

        return ResponseEntity.ok().build();
    }
}
