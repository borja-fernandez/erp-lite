package com.borfer.erp_lite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.borfer.erp_lite.persistence.jpa.repository")
public class JpaConfiguration {
}