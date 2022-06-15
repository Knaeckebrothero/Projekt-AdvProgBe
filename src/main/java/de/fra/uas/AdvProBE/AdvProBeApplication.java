package de.fra.uas.AdvProBE;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import de.fra.uas.AdvProBE.db.repositorys.BusinessRepository;

@SpringBootApplication
public class AdvProBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvProBeApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(BusinessRepository repository, MongoTemplate mongoTemplate){
		return args -> {
			Query query = new Query();
			query.fields().include("name").exclude("_id");

			long startTime = System.nanoTime();
			System.out.println("Gude, heut nix los.");
			long elapsedTime = System.nanoTime() - startTime;
			
			System.out.println("Total execution time was in Java in millis: "
	                + elapsedTime/1000000);
		};
		}
}