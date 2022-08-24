package com.review.monitoring.MonitoringSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories("com.review.monitoring.MonitoringSystem.review")
@EnableElasticsearchRepositories(basePackages = "com.review.monitoring.MonitoringSystem.elk")
public class MonitoringSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringSystemApplication.class, args);
	}


	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
