package com.luca.film;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class FilmNetworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmNetworkApiApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner runner(RoleRepository roleRepository) {
//		return args-> {
////			if (roleRepository.findByName("USER").isEmpty()) {
////				roleRepository.save(Role.builder().name("USER").build()
////				);
////			}
//		};
//	}
}
