package ru.clevertec.ecl.knyazev.service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.ecl.knyazev.config.TestContainerInitializer;
import ru.clevertec.ecl.knyazev.service.PersonService;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class PersonServiceImplTest extends TestContainerInitializer {

    @Autowired
    private PersonService personServiceImpl;

    @Test
    public void check() {
        personServiceImpl.get(UUID.fromString("e45a120c-5c08-4715-bab5-740fc0cad9f5"));
    }
}
