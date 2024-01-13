package ru.clevertec.ecl.knyazev;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.ecl.knyazev.config.AppConfig;
import ru.clevertec.ecl.knyazev.config.DataSourceConfig;
import ru.clevertec.ecl.knyazev.config.PropertiesConfig;
import ru.clevertec.ecl.knyazev.data.http.house.response.GetHouseResponseDTO;
import ru.clevertec.ecl.knyazev.service.HouseService;

import java.util.UUID;

@Slf4j
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(PropertiesConfig.class,
                        DataSourceConfig.class,
                        AppConfig.class);

        HouseService houseServiceImpl =
                annotationConfigApplicationContext.getBean("houseServiceImpl", HouseService.class);

        GetHouseResponseDTO house = houseServiceImpl.get(UUID.fromString("70847027-c60f-4fb9-a65f-73cb657893b9"));

        log.info(house.toString());
    }
}
