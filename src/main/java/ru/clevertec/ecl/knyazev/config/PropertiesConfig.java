package ru.clevertec.ecl.knyazev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ecl.knyazev.util.YAMLParser;

import java.util.Properties;

@Configuration
public class PropertiesConfig {

    private static final String PROPERTY_FILE = "application.yaml";

    @Bean
    YAMLParser yamlParser() {
        return new YAMLParser(PROPERTY_FILE);
    }

    @Bean
    DataSourceProperties dataSourceProperties(YAMLParser yamlParser) {
        return DataSourceProperties.builder()
                .driverClassName(yamlParser.getProperty("datasource", "driverClassName"))
                .jdbcUrl(yamlParser.getProperty("datasource", "jdbcUrl"))
                .username(yamlParser.getProperty("datasource", "username"))
                .password(yamlParser.getProperty("datasource", "password"))
                .maxPoolSize(Integer.parseInt(yamlParser.getProperty("datasource",
                        "maxPoolSize")))
                .connectionTimeout(Long.parseLong(yamlParser.getProperty("datasource",
                        "connectionTimeout")))
                .build();
    }

    @Bean
    Properties hibernateProperties(YAMLParser yamlParser) {
        Properties properties = new Properties();

        properties.put("hibernate.dialect", yamlParser.getProperty("hibernate", "dialect"));
        properties.put("hibernate.show_sql", Boolean.parseBoolean(yamlParser.getProperty("hibernate",
                "showSql")));
        properties.put("hibernate.format_sql", Boolean.parseBoolean(yamlParser.getProperty("hibernate",
                "formatSql")));
        properties.put("hibernate.hbm2ddl.auto", yamlParser.getProperty("hibernate",
                "hbm2ddlAuto"));

        return properties;
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
