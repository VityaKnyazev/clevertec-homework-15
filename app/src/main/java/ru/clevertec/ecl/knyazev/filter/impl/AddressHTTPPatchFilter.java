package ru.clevertec.ecl.knyazev.filter.impl;

import static ru.clevertec.ecl.knyazev.filter.impl.AddressHTTPPatchFilter.filterPattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.knyazev.data.http.address.request.PatchAddressRequestDTO;
import ru.clevertec.ecl.knyazev.filter.AbstractHTTPPatchFilter;
import ru.clevertec.ecl.knyazev.service.AddressService;

import java.util.UUID;

@Component
@WebFilter(urlPatterns = {filterPattern})
public class AddressHTTPPatchFilter extends AbstractHTTPPatchFilter {

    static final String filterPattern = "/addresses";

    private final AddressService addressServiceImpl;

    @Autowired
    public AddressHTTPPatchFilter(ObjectMapper objectMapper, AddressService addressServiceImpl) {
        super(objectMapper);
        this.addressServiceImpl = addressServiceImpl;
    }

    /**
     * Get Patch Address Request DTO for executing PATCH HTTP method on it
     *
     * @param uuid patch address request DTO uuid
     * @return patch address request DTO for executing PATCH HTTP method on it
     */
    @Override
    protected PatchAddressRequestDTO getPatchingDTO(@Valid
                                                   @NotNull(message = "address id must not be null")
                                                   @org.hibernate.validator.constraints.UUID(
                                                           message = "address id must be uuid string in lower case"
                                                   )
                                                   String uuid) {
        UUID addressUUID = UUID.fromString(uuid);
        return addressServiceImpl.patchAddressRequestDTO(addressUUID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUUIDPart(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().replaceFirst(filterPattern + URL_SEPARATOR, "");
    }
}
