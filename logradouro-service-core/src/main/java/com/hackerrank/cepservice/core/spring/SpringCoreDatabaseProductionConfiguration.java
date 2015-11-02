package com.hackerrank.cepservice.core.spring;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Driver;

@Configuration
@Profile("production")
@PropertySource("classpath:/logradouro-service-core.properties")
public class SpringCoreDatabaseProductionConfiguration {

    @Value("${mysql.host}")
    private String mysqlHost;

    @Value("${mysql.port}")
    private String mysqlPort;

    @Value("${mysql.database}")
    private String mysqlDatabase;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws PropertyVetoException {

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        comboPooledDataSource.setDriverClass(Driver.class.getName());

        comboPooledDataSource.setMinPoolSize(1);
        comboPooledDataSource.setMaxPoolSize(55);
        comboPooledDataSource.setMaxIdleTime(180);
        comboPooledDataSource.setAcquireIncrement(3);
        comboPooledDataSource.setInitialPoolSize(1);

        comboPooledDataSource.setJdbcUrl("jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + mysqlDatabase);

        Properties properties = new Properties();

        properties.setProperty("user", "root");
        properties.setProperty("password", "");
        properties.setProperty("c3p0.min_size", "1");
        properties.setProperty("c3p0.max_size", "10");
        properties.setProperty("c3p0.acquire_increment", "2");
        properties.setProperty("c3p0.idle_test_period", "300");
        properties.setProperty("c3p0.max_statements", "0");
        properties.setProperty("c3p0.timeout", "120");

        comboPooledDataSource.setProperties(properties);

        return comboPooledDataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void setMysqlHost(String mysqlHost) {
        this.mysqlHost = mysqlHost;
    }

    public void setMysqlPort(String mysqlPort) {
        this.mysqlPort = mysqlPort;
    }

    public void setMysqlDatabase(String mysqlDatabase) {
        this.mysqlDatabase = mysqlDatabase;
    }
}