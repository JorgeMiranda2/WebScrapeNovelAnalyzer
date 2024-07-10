package com.SeriesAnalyzer.main.Configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "seriesEntityManagerFactory", transactionManagerRef = "seriesTransactionManager",
        basePackages = { "com.SeriesAnalyzer.main.repositories.Series"})
public class SeriesConfig {

    @Autowired
    private Environment env;

    @Bean(name = "seriesDataSource")
    public DataSource userDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("series.datasource.url"));
        dataSource.setUsername(env.getProperty("series.datasource.username"));
        dataSource.setPassword(env.getProperty("series.datasource.password"));
        dataSource.setDriverClassName(env.getProperty("series.datasource.driver-class-name"));

        return dataSource;
    }

    @Bean(name = "seriesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDatasource());
        em.setPackagesToScan("com.SeriesAnalyzer.main.Models.Series");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("series.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.show-sql", env.getProperty("series.jpa.show-sql"));
        properties.put("hibernate.dialect", env.getProperty("series.jpa.database-platform"));

        em.setJpaPropertyMap(properties);

        return em;

    }

    @Bean(name = "seriesTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }


}