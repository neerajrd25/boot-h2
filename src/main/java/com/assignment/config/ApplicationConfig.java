package com.assignment.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableSpringDataWebSupport
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "com.assignment.*")
@EntityScan("com.assignment.*")
@EnableJpaRepositories(basePackages = "com.assignment.repository")
@EnableJpaAuditing(auditorAwareRef="userProvider")
public class ApplicationConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	 @Bean
     public AuditorAware<String> userProvider() {
         return new UserAuditing();
     }

}
