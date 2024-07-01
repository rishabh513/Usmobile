package com.example.usmobile.Us.Mobile.Assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableMongoAuditing
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class UsMobileAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsMobileAssignmentApplication.class, args);
	}

}
