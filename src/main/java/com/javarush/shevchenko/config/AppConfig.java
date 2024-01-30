package com.javarush.shevchenko.config;

import java.util.Properties;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import static org.hibernate.cfg.AvailableSettings.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@AllArgsConstructor
@Configuration
@EnableTransactionManagement
public class AppConfig {

    private final PropertiesService propertiesService;

    // Метод для создания фабрики сессий Hibernate
    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.javarush.shevchenko.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    // Метод для настройки свойств Hibernate
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(DIALECT, propertiesService.getDialect());
        properties.put(DRIVER, propertiesService.getDriverClassName());
        properties.put(HBM2DDL_AUTO, propertiesService.getHbm2ddl());
        properties.put(SHOW_SQL, propertiesService.getShowSql());
        return properties;
    }

    // Метод для создания и настройки источника данных (DataSource)
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(propertiesService.getMaximumPoolSize());
        dataSource.setDriverClassName(propertiesService.getDriverClassName());
        dataSource.setJdbcUrl(propertiesService.getConnectionUrl());
        dataSource.setUsername(propertiesService.getUsername());
        dataSource.setPassword(propertiesService.getPassword());
        return dataSource;
    }

    // Метод для создания менеджера транзакций JPA
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }
}
