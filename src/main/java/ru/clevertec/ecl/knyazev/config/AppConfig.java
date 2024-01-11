package ru.clevertec.ecl.knyazev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "ru.clevertec.ecl.knyazev.dao.impl" })
public class AppConfig {
}
