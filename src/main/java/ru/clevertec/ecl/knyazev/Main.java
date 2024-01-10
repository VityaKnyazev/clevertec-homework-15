package ru.clevertec.ecl.knyazev;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.ecl.knyazev.config.AppConfig;
import ru.clevertec.ecl.knyazev.config.DataSourceConfig;
import ru.clevertec.ecl.knyazev.config.PropertiesConfig;
import ru.clevertec.ecl.knyazev.dao.AddressDAO;
import ru.clevertec.ecl.knyazev.entity.Address;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(PropertiesConfig.class,
                        DataSourceConfig.class,
                        AppConfig.class);

        AddressDAO addressDAOJPAImpl =
                annotationConfigApplicationContext.getBean("addressDAOJPAImpl", AddressDAO.class);

        List<Address> addresses = addressDAOJPAImpl.findAll();

        addresses.forEach(System.out::println);
    }
}
