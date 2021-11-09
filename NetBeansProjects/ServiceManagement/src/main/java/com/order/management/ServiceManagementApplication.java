package com.order.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

public class ServiceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceManagementApplication.class, args);
	}

}
