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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.knyazev.data.domain.searching.Searching;
import ru.clevertec.ecl.knyazev.data.domain.searching.impl.SearchingImpl;
import ru.clevertec.ecl.knyazev.data.http.address.request.PatchAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.request.PostPutAddressRequestDTO;
import ru.clevertec.ecl.knyazev.data.http.address.response.GetAddressResponseDTO;
import ru.clevertec.ecl.knyazev.service.AddressService;
import ru.clevertec.ecl.knyazev.validation.group.Update;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Addresses API")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressServiceImpl;

    @Operation(summary = "Get address response DTO by address uuid")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping("/{uuid}")
    public ResponseEntity<GetAddressResponseDTO> getAddress(@PathVariable
                                                        @Parameter(name = "uuid",
                                                                required = true,
                                                                description = "Address uuid",
                                                                example = "8ad957d6-9a44-4a6a-8789-0e3638bcb46c")
                                                        @Valid
                                                        @org.hibernate.validator.constraints.UUID(message =
                                                                "address id must be uuid string in lower case")
                                                        String uuid) {

        return ResponseEntity.ok(addressServiceImpl.getAddressResponseDTO(UUID.fromString(uuid)));
    }

    @Operation(summary = "Get all addresses response DTOs")
    @ApiResponse(responseCode = "200", description = "Successfully found")
    @GetMapping
    public ResponseEntity<List<GetAddressResponseDTO>> getAllAddresses(@RequestParam(required = false)
                                                @Parameter(name = "search",
                                                      description = "parameter for searching on address text fields",
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

        return ResponseEntity.ok(addressServiceImpl.getAllAddressResponseDTO(pageable, searching));
    }

    @Operation(summary = "Save address")
    @ApiResponse(responseCode = "201", description = "Successfully saved")
    @PostMapping
    public ResponseEntity<GetAddressResponseDTO> saveAddress(@RequestBody
                                                         @Valid
                                                         PostPutAddressRequestDTO postPutAddressRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(addressServiceImpl.add(postPutAddressRequestDTO));
    }

    @Operation(summary = "Update address")
    @ApiResponse(responseCode = "200", description = "Successfully updated")
    @PutMapping
    public ResponseEntity<GetAddressResponseDTO> changeAllAddresses(@RequestBody
                                                              @Validated({Default.class, Update.class})
                                                              PostPutAddressRequestDTO postPutAddressRequestDTO) {
        return ResponseEntity.ok(addressServiceImpl.update(postPutAddressRequestDTO));
    }

    @Operation(summary = "Partial update address on address uuid")
    @ApiResponse(responseCode = "200", description = "Successfully updated")
    @PatchMapping("/{uuid}")
    public ResponseEntity<GetAddressResponseDTO> partialChangeAddresses(@PathVariable
                                                        @Parameter(name = "uuid",
                                                                required = true,
                                                                description = "address uuid",
                                                                example = "8ad957d6-9a44-4a6a-8789-0e3638bcb46c")
                                                        @Valid
                                                        @org.hibernate.validator.constraints.UUID(message =
                                                                "address id must be uuid string in lower case")
                                                        String uuid,
                                                        @RequestBody
                                                        @Valid
                                                        PatchAddressRequestDTO patchAddressRequestDTO) {
        return ResponseEntity.ok(addressServiceImpl.partialUpdate(UUID.fromString(uuid), patchAddressRequestDTO));
    }

    @Operation(summary = "Delete address")
    @ApiResponse(responseCode = "204", description = "No content - successfully deleted")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteAddress(@PathVariable
                                           @Parameter(name = "uuid",
                                           required = true,
                                           description = "address uuid",
                                           example = "8ad957d6-9a44-4a6a-8789-0e3638bcb46c")
                                           @Valid
                                           @org.hibernate.validator.constraints.UUID(message =
                                                 "address id must be uuid string in lower case")
                                           String uuid) {
        addressServiceImpl.remove(UUID.fromString(uuid));

        return ResponseEntity.noContent().build();
    }
}
