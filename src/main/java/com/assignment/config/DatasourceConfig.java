package com.assignment.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class DatasourceConfig {

	@Primary
	@Bean(name = "data")
	@ConfigurationProperties(prefix="spring.data")
	@Profile(value = { "local" })
	public DataSource localDataSource() {
		return DataSourceBuilder.create().build();
	}
	
}
