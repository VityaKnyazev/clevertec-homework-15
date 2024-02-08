package ru.clevertec.ecl.knyazev.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import ru.clevertec.ecl.knyazev.servlet.request.wrapper.PatchServletRequestWrapper;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractHTTPPatchFilter implements Filter {
    private static final String APPLICATION_JSON_PATCH_MEDIA_TYPE = "application/json-patch+json";

    protected static final String URL_SEPARATOR = "/";

    private final ObjectMapper objectMapper;

    /**
     * Change json request body to patched json DTO using PATCH RFC 6902 HTTP method.
     *
     * {@inheritDoc}
     * @throws JsonPatch exception when PATCH RFC 6902 HTTP method failed
     */
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {

        if (request instanceof HttpServletRequest &&
                HttpMethod.PATCH.name().equals(((HttpServletRequest) request).getMethod()) &&
                request.getContentType().contains(APPLICATION_JSON_PATCH_MEDIA_TYPE)) {
            String requestBody = request.getReader().lines()
                    .collect(Collectors.joining());
            String pathUUID = getUUIDPart((HttpServletRequest) request);

            JsonPatch jsonPatch = JsonPatch.fromJson(objectMapper.readTree(requestBody));

            JsonNode patchingJson = objectMapper.readTree(
                    objectMapper.writeValueAsString(getPatchingDTO(pathUUID)));

            String patchedJsonResult = objectMapper.writeValueAsString(jsonPatch.apply(patchingJson));

            PatchServletRequestWrapper patchServletRequestWrapper =
                    new PatchServletRequestWrapper((HttpServletRequest) request, patchedJsonResult);

            chain.doFilter(patchServletRequestWrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * Get DTO for using HTTP method PATCH on it
     *
     * @param uuid DTO uuid
     * @return data transfer object for using HTTP method PATCH on it
     * @param <T> DTO type
     */
    abstract protected  <T> T getPatchingDTO(String uuid);

    /**
     *
     * Get UUID part from URI
     *
     * @param httpServletRequest request
     * @return uuid from given uri
     */
    abstract protected String getUUIDPart(HttpServletRequest httpServletRequest);
}
