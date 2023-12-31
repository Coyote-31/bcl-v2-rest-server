package com.coyote.big_city_library.rest_server_repository;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.coyote.big_city_library")
@EnableJpaRepositories(basePackages = {"com.coyote.big_city_library.rest_server_repository"})
@EntityScan(basePackages = {"com.coyote.big_city_library.rest_server_model"})
public class TestConfig {
}
