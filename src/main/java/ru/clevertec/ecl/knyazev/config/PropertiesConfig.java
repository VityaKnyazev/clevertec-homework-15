package ru.clevertec.ecl.knyazev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ecl.knyazev.util.YAMLParser;

@Configuration
public class PropertiesConfig {

    private static final String PROPERTY_FILE = "application.yaml";

    @Bean
    YAMLParser yamlParser() {
        return new YAMLParser(PROPERTY_FILE);
    }

    @Bean
    PagingProperties pagingProperties(YAMLParser yamlParser) {
        return PagingProperties.builder()
                .defaultPageSize(Integer.parseInt(yamlParser.getProperty("paging",
                        "defaultPageSize")))
                .defaultPage(Integer.parseInt(yamlParser.getProperty("paging",
                        "defaultPage")))
                .build();
    }

}
