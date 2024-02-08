package com.company.streamingwebappproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//@SpringBootApplication()
//@EnableResourceServer
@SpringBootApplication()
public class StreamingWebappProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingWebappProjectApplication.class, args);
	}

}
