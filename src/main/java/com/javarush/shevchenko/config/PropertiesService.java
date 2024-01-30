package com.javarush.shevchenko.config;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Service
@Getter
@PropertySource("classpath:./application.properties")
public class PropertiesService {
    @Value("${hikari.maximum_pool_size}")
    private Integer maximumPoolSize;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.connection.driver_class}")
    private String driverClassName;

    @Value("${hibernate.connection.url}")
    private String connectionUrl;

    @Value("${hibernate.connection.username}")
    private String username;

    @Value("${hibernate.connection.password}")
    private String password;

    @Value("${hibernate.show_sql}")
    private Boolean showSql;

    @Value("${hibernate.hbm2ddl}")
    private String hbm2ddl;

    @Value("${template.resolver.prefix}")
    private String resolverPrefix;

    @Value("${template.resolver.suffix}")
    private String resolverSuffix;

    @Value("${template.resolver.encoding}")
    private String encoding;

    @Value("${template.resolver.cacheable}")
    private Boolean cacheable;
}
