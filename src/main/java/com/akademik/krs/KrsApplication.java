package com.akademik.krs;

import com.akademik.krs.services.KrsParametersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrsApplication.class, args);
	}

	// //unmark these comments below to add initial data to the database
//	@Bean
//	public CommandLineRunner run(KrsParametersService kpService){
//		return args -> {
//			kpService.addKrsParameter("KRS_STATUS", "DRAFT");
//			kpService.addKrsParameter("KRS_STATUS", "SUBMITTED");
//			kpService.addKrsParameter("KRS_STATUS", "APPROVED");
//			kpService.addKrsParameter("KRS_STATUS", "REJECTED");
//		};
//	}
}
