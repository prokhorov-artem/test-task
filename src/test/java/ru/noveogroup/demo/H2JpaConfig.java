package ru.noveogroup.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "ru.noveogroup.demo.repository")
@PropertySource("classpath:application-test.yml")
@EnableTransactionManagement
public class H2JpaConfig {
}