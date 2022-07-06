package de.fra.uas.AdvProBE;


import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.data.mongodb.core.query.Update;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.preCalculate.preProcessed;
import de.fra.uas.AdvProBE.service.BusinessService;
import de.fra.uas.AdvProBE.service.ReviewService;

@SpringBootApplication
public class AdvProBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvProBeApplication.class, args);
	}

	//@Bean
	CommandLineRunner runner(MongoTemplate template, BusinessService bService, ReviewService rService){
		return args -> {
			long startTime = System.nanoTime();
			System.out.println("Application running, preprocessing data...");
			
			Query query = new Query();
			query.addCriteria(Criteria.where("preprocessedAtDay").is(LocalDate.now()));
			preProcessed pre = new preProcessed(LocalDate.now());
			template.insert(pre);
			
			try {
			List<LocalDate> allDates = bService.getAllDates();
			Update uAllDates = new Update();
			uAllDates.set("allDates", allDates);
			template.updateFirst(query, uAllDates, preProcessed.class);
			} catch(Exception E) {
				System.out.println("allDates");
			}
			
			try {
			List<String> allBusinessesPerCity= bService.getBusinessofAllCitys();
			Update uAllBusinessesPerCity = new Update();
			uAllBusinessesPerCity.set("allBusinessesPerCity", allBusinessesPerCity);
			template.updateFirst(query, uAllBusinessesPerCity, preProcessed.class);
			} catch(Exception E) {
				System.out.println("allBusinessesPerCity");
			}
			
			try {
			List<String> allAverageRatingsPerCity = bService.getRatingOfAllCitys();
			Update uAllAverageRatingsPerCity = new Update();
			uAllAverageRatingsPerCity.set("allAverageRatingsPerCity", allAverageRatingsPerCity);
			template.updateFirst(query, uAllAverageRatingsPerCity, preProcessed.class);
			} catch(Exception E) {
				System.out.println("allAverageRatingsPerCity");
			}
			
			try {
			List<LocalDateTime> allReviewsTimespan = rService.getReviewsTimeline();
			Update uAllReviewsTimespan = new Update();
			uAllReviewsTimespan.set("allReviewsTimespan", allReviewsTimespan);
			template.updateFirst(query, uAllReviewsTimespan, preProcessed.class);
			} catch(Exception E) {
				System.out.println("allReviewsTimespan");
			}
			
			try {
			List<Business> topTenBusinessesWorldWide = bService.getTopTenRestaurantTotal();
			Update uTopTenBusinessesWorldWide = new Update();
			uTopTenBusinessesWorldWide.set("topTenBusinessesWorldWide", topTenBusinessesWorldWide);
			template.updateFirst(query, uTopTenBusinessesWorldWide, preProcessed.class);
			} catch(Exception E) {
				System.out.println("topTenBusinessesWorldWide");
			}
			
			Update uTimePreprocessingTookInMs = new Update();
			uTimePreprocessingTookInMs.set("timePreprocessingTookInMs", System.nanoTime() - startTime);
			template.updateFirst(query, uTimePreprocessingTookInMs, preProcessed.class);
			
			long elapsedTime = System.nanoTime() - startTime;
			System.out.println("Total execution time was in Java in millis: "
	                + elapsedTime/1000000);
		};
		}
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(
				Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token",
				"Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}