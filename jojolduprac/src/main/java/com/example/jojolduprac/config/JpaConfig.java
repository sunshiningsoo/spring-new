package com.example.jojolduprac.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // WebTest를 위해 SpringbootApplication과 분리해줌
public class JpaConfig {
}
