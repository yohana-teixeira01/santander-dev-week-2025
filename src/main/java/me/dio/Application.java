package me.dio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "me.dio")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
