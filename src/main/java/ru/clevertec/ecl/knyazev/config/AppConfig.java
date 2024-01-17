package ru.clevertec.ecl.knyazev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {"ru.clevertec.ecl.knyazev.dao.impl",
        "ru.clevertec.ecl.knyazev.mapper",
        "ru.clevertec.ecl.knyazev.service.impl",
        "ru.clevertec.ecl.knyazev.data.domain.pagination.impl",
        "ru.clevertec.ecl.knyazev.controller"})
@EnableTransactionManagement
@EnableWebMvc
public class AppConfig {

}
