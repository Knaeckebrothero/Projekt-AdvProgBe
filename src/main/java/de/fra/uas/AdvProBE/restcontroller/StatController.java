package de.fra.uas.AdvProBE.restcontroller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.service.BusinessService;
import de.fra.uas.AdvProBE.service.ReviewService;
import lombok.AllArgsConstructor;

//Controller that deals with all the information needed to display statistics
@AllArgsConstructor
@RestController
@RequestMapping("stat/")
public class StatController {

	private BusinessService bService;
	private ReviewService rService;

	// Get�s a business with it�s information
	@GetMapping
	@RequestMapping("business/{city}/{name}")
	public ResponseEntity<Business> GetBusiness(@PathVariable String city, @PathVariable String name) {
		Business business = bService.GetBusiness(city, name);
		if (business != null) {
			business.setAttributes(null);
			business.setCheckins(null);
			business.setId(null);
			business.setBusinessId(null);
			return new ResponseEntity<Business>(business, HttpStatus.OK);
		} else {
			return new ResponseEntity<Business>(HttpStatus.NOT_FOUND);
		}
	}

	// Get�s the number of Businesses found in the given City
	@GetMapping
	@RequestMapping("business/total/{city}")
	public ResponseEntity<HashMap<String, Integer>> GetBusinessPerCity(@PathVariable String city) {
		HashMap<String, Integer> map = bService.GetBusinessesPerCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Integer>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
		}
	}

	// Get�s all the counts�s of Businesses found in all Citys
	@GetMapping
	@RequestMapping("business/countCity")
	public ResponseEntity<HashMap<String, Integer>> GetBusinessofAllCitys() {
		return new ResponseEntity<HashMap<String, Integer>>(bService.GetBusinessofAllCitys(), HttpStatus.OK);
	}

	// Get�s the average Rating for the given City
	@GetMapping
	@RequestMapping("city/rating/average/{city}")
	public ResponseEntity<HashMap<String, Double>> GetRatingOfCity(@PathVariable String city) {
		HashMap<String, Double> map = bService.GetRatingOfCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Double>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Double>>(HttpStatus.NOT_FOUND);
		}
	}

	// Get�s the average Rating of all Citys
	@GetMapping
	@RequestMapping("city/all/rating/average")
	public ResponseEntity<HashMap<String, Double>> GetRatingOfAllCity() {
		return new ResponseEntity<HashMap<String, Double>>(bService.GetRatingOfAllCitys(), HttpStatus.OK);
	}

	// Get�s the number of Reviews written for a Business in the given City
	@GetMapping
	@RequestMapping("reviews/city/{city}")
	public ResponseEntity<HashMap<String, Integer>> GetReviewsPerCity(@PathVariable String city) {
		HashMap<String, Integer> map = rService.GetReviewsPerCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Integer>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
		}
	}

	// Get�s all the counts�s of Reviews written for a Business in all Citys
	@GetMapping
	@RequestMapping("review/all/city/count")
	public ResponseEntity<HashMap<String, Integer>> GetReviewsofAllCitys() {
		return new ResponseEntity<HashMap<String, Integer>>(rService.GetReviewsofAllCitys(), HttpStatus.OK);
	}

	// Get�s all the Reviews in a timespan
	@GetMapping
	@RequestMapping("reviews/timespan")
	public ResponseEntity<List<LocalDateTime>> GetReviewsTimeline() {
		return new ResponseEntity<List<LocalDateTime>>(rService.GetReviewsTimeline(), HttpStatus.OK);
	}

	// Get�s all the Reviews in a timespan
	@GetMapping
	@RequestMapping("business/top/ten/{designation}/{name}")
	public ResponseEntity<List<Business>> GetTopRestaurant(@PathVariable String designation,
			@PathVariable String name) {
		List<Business> list = bService.GetTopTenRestaurant(designation, name);
		if (list != null) {
			return new ResponseEntity<List<Business>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Business>>(HttpStatus.BAD_REQUEST);
		}
	}

	// Get�s all the Reviews in a timespan
	@GetMapping
	@RequestMapping("business/top/ten/total")
	public ResponseEntity<List<Business>> GetTopRestaurantTotal() {
		List<Business> list = bService.GetTopTenRestaurants();
		if (list != null) {
			return new ResponseEntity<List<Business>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Business>>(HttpStatus.BAD_REQUEST);
		}
	}

	// Get�s all the checkins of a Business
	@GetMapping
	@RequestMapping("business/checkins/{city}/{name}")
	public ResponseEntity<List<LocalDateTime>> GetCheckins(@PathVariable String city, @PathVariable String name) {
		List<LocalDateTime> list = bService.GetCheckins(city, name);
		if (list != null) {
			return new ResponseEntity<List<LocalDateTime>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<LocalDateTime>>(HttpStatus.NOT_FOUND);
		}
	}
}