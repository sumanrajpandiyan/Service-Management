package com.order.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableAutoConfiguration
public class ServiceManagementApplication {

	public static void main(String[] args) { 
                System.out.println("Hello spring Hello");
		SpringApplication.run(ServiceManagementApplication.class, args);
	}

}
