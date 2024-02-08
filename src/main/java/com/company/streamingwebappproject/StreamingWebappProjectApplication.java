package com.company.streamingwebappproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication()
//@EnableResourceServer
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StreamingWebappProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingWebappProjectApplication.class, args);
	}

}
