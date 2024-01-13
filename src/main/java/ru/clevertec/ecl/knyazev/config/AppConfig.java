package ru.clevertec.ecl.knyazev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {"ru.clevertec.ecl.knyazev.dao.impl",
        "ru.clevertec.ecl.knyazev.mapper",
        "ru.clevertec.ecl.knyazev.service.impl",
        "ru.clevertec.ecl.knyazev.pagination.impl"})
@EnableTransactionManagement
public class AppConfig {

}
