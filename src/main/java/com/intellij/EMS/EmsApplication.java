package com.intellij.EMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.intellij")
public class EmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsApplication.class, args);
	}

}
