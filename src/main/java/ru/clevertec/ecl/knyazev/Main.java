package ru.clevertec.ecl.knyazev;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.ecl.knyazev.config.AppConfig;
import ru.clevertec.ecl.knyazev.config.DataSourceConfig;
import ru.clevertec.ecl.knyazev.config.PropertiesConfig;
import ru.clevertec.ecl.knyazev.dao.AddressDAO;
import ru.clevertec.ecl.knyazev.entity.Address;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(PropertiesConfig.class,
                        DataSourceConfig.class,
                        AppConfig.class);

        AddressDAO addressDAOJPAImpl =
                annotationConfigApplicationContext.getBean("addressDAOJPAImpl", AddressDAO.class);

        List<Address> addresses = addressDAOJPAImpl.findAll();

        addresses.forEach(address -> log.info(address.toString()));
    }
}
