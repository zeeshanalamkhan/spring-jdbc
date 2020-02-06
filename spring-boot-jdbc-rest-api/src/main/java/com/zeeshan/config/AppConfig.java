package com.zeeshan.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = "com.zeeshan")
@PropertySource("classpath:application.yml")
public class AppConfig {

	@Autowired
	private Environment env;

	@Bean
	public JdbcTemplate getTemplate() {

		JdbcTemplate jt = new JdbcTemplate();

		jt.setDataSource(dataSource());

		return jt;
	}

	private DataSource dataSource() {

		return new DriverManagerDataSource(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
	}
}
