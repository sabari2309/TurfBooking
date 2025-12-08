package com.example.spring.data.rest;

import com.example.spring.data.rest.model.Turf;
import com.example.spring.data.rest.repo.TurfRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class SpringDataRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRestApplication.class, args);
	}

//	@Bean
//	CommandLineRunner initData(TurfRepository repo) {
//		return args -> {
//			if (repo.count() == 0) {
//				Turf t1 = new Turf();
//				t1.setName("Green Turf Arena");
//				t1.setLocation("Chennai");
//				repo.save(t1);
//
//				Turf t2 = new Turf();
//				t2.setName("Blue Sports Turf");
//				t2.setLocation("Coimbatore");
//				repo.save(t2);
//
//				System.out.println("Sample turf data inserted ✔");
//			}
//		};
	}

