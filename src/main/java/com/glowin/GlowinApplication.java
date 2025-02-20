package com.glowin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GlowinApplication {

	public static void main(String[] args) {
		System.out.println(org.hibernate.Version.getVersionString());
		SpringApplication.run(GlowinApplication.class, args);
	}

}
