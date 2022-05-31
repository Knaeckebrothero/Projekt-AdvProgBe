package de.fra.uas.AdvProBE.restcontroller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fra.uas.AdvProBE.service.ReviewService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/review/")
public class ReviewAPI {

	private ReviewService rService;

	// Get큦 the number of Reviews written for a Business in the given City
	@GetMapping
	@RequestMapping("get/total/{city}")
	public ResponseEntity<HashMap<String, Integer>> GetReviewsPerCity(@PathVariable String city) {
		HashMap<String, Integer> map = rService.GetReviewsPerCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Integer>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
		}
	}

	// Get큦 all the counts큦 of Reviews written for a Business in all Citys
	@GetMapping
	@RequestMapping("get/countCity")
	public ResponseEntity<HashMap<String, Integer>> GetReviewsofAllCitys() {
		return new ResponseEntity<HashMap<String, Integer>>(rService.GetReviewsofAllCitys(), HttpStatus.OK);
	}
	
	// Get큦 all the counts큦 of Reviews written for a Business in all Citys
		@GetMapping
		@RequestMapping("get/Test")
		public ResponseEntity<List<String>> Test() {
			return new ResponseEntity<List<String>>(rService.Test(), HttpStatus.OK);
		}
}