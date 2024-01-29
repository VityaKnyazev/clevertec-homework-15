package ru.clevertec.ecl.knyazev.integration.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.ecl.knyazev.cache.operator.AbstractCacheOperator;
import ru.clevertec.ecl.knyazev.integration.config.TestContainerInitializer;
import ru.clevertec.ecl.knyazev.data.http.person.request.PostPutPersonRequestDTO;
import ru.clevertec.ecl.knyazev.entity.Person;
import ru.clevertec.ecl.knyazev.service.PersonService;
import ru.clevertec.ecl.knyazev.integration.util.PersonSerciceIntegrationTestData;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@ActiveProfiles("test")
public class PersonServiceImplTest extends TestContainerInitializer {

    @Autowired
    private PersonService personServiceImpl;

    @SpyBean
    private AbstractCacheOperator<UUID, Person> personCacheOperatorSpy;

    @Test
    public void checkPersonServiceOperationsWithEnabledCache() throws InterruptedException {
        UUID getPersonUUID = PersonSerciceIntegrationTestData.personGettingUUID();
        PostPutPersonRequestDTO personSavingRequestDTO = PersonSerciceIntegrationTestData.personSavingRequest();
        PostPutPersonRequestDTO personUpdatingRequestDTO = PersonSerciceIntegrationTestData.personUpdatingRequest();
        UUID deletingPersonUUID = PersonSerciceIntegrationTestData.personDeletingUUID();


        ExecutorService executorService = Executors.newFixedThreadPool(6);

        executorService.submit(() -> personServiceImpl.get(getPersonUUID));
        executorService.submit(() -> personServiceImpl.add(personSavingRequestDTO));
        executorService.submit(() -> personServiceImpl.update(personUpdatingRequestDTO));
        executorService.submit(() -> personServiceImpl.remove(deletingPersonUUID));

        executorService.awaitTermination(4L, TimeUnit.SECONDS);
        executorService.shutdown();

        Mockito.verify(personCacheOperatorSpy,Mockito.times(2))
                .find(Mockito.any(UUID.class));
        Mockito.verify(personCacheOperatorSpy, Mockito.times(4))
                .add(Mockito.any(UUID.class), Mockito.any(Person.class));
        Mockito.verify(personCacheOperatorSpy, Mockito.never()).delete(Mockito.any(UUID.class));
    }
}
