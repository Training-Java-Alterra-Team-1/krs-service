package com.akademik.krs;

import com.akademik.krs.services.KrsParametersService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EnableEncryptableProperties
@EnableEurekaClient
public class KrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrsApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(KrsParametersService kpService){
		return args -> {
			kpService.addKrsParameter("KRS_STATUS", "DRAFT");
			kpService.addKrsParameter("KRS_STATUS", "SUBMITTED");
			kpService.addKrsParameter("KRS_STATUS", "APPROVED");
			kpService.addKrsParameter("KRS_STATUS", "REJECTED");
		};
	}
}
