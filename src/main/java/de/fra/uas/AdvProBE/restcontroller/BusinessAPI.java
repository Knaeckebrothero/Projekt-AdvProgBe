package de.fra.uas.AdvProBE.restcontroller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fra.uas.AdvProBE.service.BusinessService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/business/")
public class BusinessAPI {

	private BusinessService bService;

	// Get큦 the number of Businesses found in the given City
	@GetMapping
	@RequestMapping("get/total/{city}")
	public ResponseEntity<HashMap<String, Integer>> GetBusinessPerCity(@PathVariable String city) {
		HashMap<String, Integer> map = bService.GetBusinessesPerCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Integer>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
		}
	}

	// Get큦 all the counts큦 of Businesses found in all Citys
	@GetMapping
	@RequestMapping("get/count")
	public ResponseEntity<HashMap<String, Integer>> GetBusinessofAllCitys() {
		return new ResponseEntity<HashMap<String, Integer>>(bService.GetBusinessofAllCitys(), HttpStatus.OK);
	}

	// Get큦 the average Rating for the given City
	@GetMapping
	@RequestMapping("get/rating/{city}")
	public ResponseEntity<HashMap<String, Double>> GetRatingOfCity(@PathVariable String city) {
		HashMap<String, Double> map = bService.GetRatingOfCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Double>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Double>>(HttpStatus.NOT_FOUND);
		}
	}
}