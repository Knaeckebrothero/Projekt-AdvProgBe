package de.fra.uas.AdvProBE.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.preCalculate.preProcessed;
import de.fra.uas.AdvProBE.service.BusinessService;
import lombok.AllArgsConstructor;

//Controller that deals with all the information needed to display the map
@AllArgsConstructor
@RestController
@RequestMapping("map/")
public class MapController {

	private MongoTemplate template;
	private BusinessService bService;

	// Get All Businesses (Only for development purposes)
	@GetMapping("dev")
	public ResponseEntity<List<Business>> getAllBusinesses() {
		return new ResponseEntity<List<Business>>(bService.getAllBusinesses(), HttpStatus.OK);
	}

	// Get큦 a list with all the citynames
	@GetMapping("citys")
	public ResponseEntity<List<String>> getAllCitys() {
		return new ResponseEntity<List<String>>(bService.getAllCitys(), HttpStatus.OK);
	}
	
	// Get큦 a list with all the citynames
		@GetMapping("states")
		public ResponseEntity<List<String>> getAllStates() {
			return new ResponseEntity<List<String>>(bService.getAllStates(), HttpStatus.OK);
		}

	// Get큦 a list with all the businesses for a city
	@GetMapping("businesses/{city}")
	public ResponseEntity<List<String>> getAllBusinessesForCity(@PathVariable String city) {
		return new ResponseEntity<List<String>>(bService.getAllBusinessesForACity(city), HttpStatus.OK);
	}

	// Get큦 a list of all the categories
	@GetMapping("categories")
	public ResponseEntity<List<String>> getAllCategories() {
		return new ResponseEntity<List<String>>(bService.getAllCategories(), HttpStatus.OK);
	}

	// Get큦 a list of all the dates that exist in checkins or reviews
	@GetMapping("dates")
	public ResponseEntity<List<LocalDate>> getAllDates() {
		Query query = new Query();
		query.fields().include("allDates").exclude("_id");
		return new ResponseEntity<List<LocalDate>>(template.find(query, preProcessed.class).get(0).getAllDates(),
				HttpStatus.OK);
	}

	// Get all Businesses filtered
	@GetMapping("businesses/filtered/basic/{state}/{city}/{stars}/{open}/{review}/{categorie}")
	public ResponseEntity<List<Business>> getBasicFilteredBusinesses(@PathVariable String state, @PathVariable String city,
			@PathVariable String stars, @PathVariable String open, @PathVariable String review, @PathVariable String categorie) {
		return new ResponseEntity<List<Business>>(bService.getFilteredBusinesses(state, city, stars, open, review, categorie), HttpStatus.OK);
	}
	
	// Get all Businesses filtered
		@GetMapping("businesses/filtered/advanced/{stateList}/{cityList}/{operatorStars}/{stars}/{open}/{operatorReview}/{review}")
		public ResponseEntity<List<Business>> getAdvancedFilteredBusinesses(@PathVariable String stateList, @PathVariable String cityList,
				@PathVariable String operatorStars, @PathVariable String stars, @PathVariable String open,@PathVariable String operatorReview, @PathVariable String review) {
			return new ResponseEntity<List<Business>>(bService.getAdvancedFilteredBusinesses(stateList, cityList, operatorStars, stars, open, operatorReview, review), HttpStatus.OK);
		}
}