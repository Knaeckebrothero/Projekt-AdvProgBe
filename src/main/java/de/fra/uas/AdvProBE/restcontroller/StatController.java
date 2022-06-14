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

<<<<<<< Updated upstream
	// Getï¿½s a business with itï¿½s information
	@GetMapping
	@RequestMapping("business/custom/{city}/{name}")
	public ResponseEntity<Business> GetBusiness(@PathVariable String city, @PathVariable String name) {
		Business business = bService.GetBusiness(city, name);
=======
	// Get´s a business with it´s information
	@GetMapping("business/custom/{city}/{name}")
	public ResponseEntity<Business> getBusiness(@PathVariable String city, @PathVariable String name) {
		Business business = bService.getBusiness(city, name);
>>>>>>> Stashed changes
		if (business != null) {
			business.setCheckins(null);
			business.setId(null);
			business.setBusinessId(null);
			return new ResponseEntity<Business>(business, HttpStatus.OK);
		} else {
			return new ResponseEntity<Business>(HttpStatus.NOT_FOUND);
		}
	}

	// Getï¿½s the number of Businesses found in the given City
	@GetMapping
	@RequestMapping("business/totalStats/{city}")
	public ResponseEntity<HashMap<String, Integer>> getBusinessPerCity(@PathVariable String city) {
		HashMap<String, Integer> map = bService.getBusinessesPerCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Integer>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
		}
	}

	// Getï¿½s all the countsï¿½s of Businesses found in all Citys
	@GetMapping
<<<<<<< Updated upstream
	@RequestMapping("business/countCity")
	public ResponseEntity<HashMap<String, Integer>> GetBusinessofAllCitys() {
		return new ResponseEntity<HashMap<String, Integer>>(bService.GetBusinessofAllCitys(), HttpStatus.OK);
=======
	@RequestMapping("busines/countCity")
	public ResponseEntity<HashMap<String, Integer>> getBusinessofAllCitys() {
		return new ResponseEntity<HashMap<String, Integer>>(bService.getBusinessofAllCitys(), HttpStatus.OK);
>>>>>>> Stashed changes
	}

	// Getï¿½s the average Rating for the given City
	@GetMapping
	@RequestMapping("city/rating/average/{city}")
	public ResponseEntity<HashMap<String, Double>> getRatingOfCity(@PathVariable String city) {
		HashMap<String, Double> map = bService.getRatingOfCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Double>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Double>>(HttpStatus.NOT_FOUND);
		}
	}

	// Getï¿½s the average Rating of all Citys
	@GetMapping
	@RequestMapping("city/all/rating/average")
	public ResponseEntity<HashMap<String, Double>> getRatingOfAllCity() {
		return new ResponseEntity<HashMap<String, Double>>(bService.getRatingOfAllCitys(), HttpStatus.OK);
	}

	// Getï¿½s the number of Reviews written for a Business in the given City
	@GetMapping
	@RequestMapping("reviews/city/{city}")
	public ResponseEntity<HashMap<String, Integer>> getReviewsPerCity(@PathVariable String city) {
		HashMap<String, Integer> map = rService.getReviewsPerCity(city);
		if (map != null) {
			return new ResponseEntity<HashMap<String, Integer>>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<HashMap<String, Integer>>(HttpStatus.NOT_FOUND);
		}
	}

	// Getï¿½s all the countsï¿½s of Reviews written for a Business in all Citys
	@GetMapping
	@RequestMapping("review/all/city/count")
	public ResponseEntity<HashMap<String, Integer>> getReviewsofAllCitys() {
		return new ResponseEntity<HashMap<String, Integer>>(rService.getReviewsofAllCitys(), HttpStatus.OK);
	}

	// Getï¿½s all the Reviews in a timespan
	@GetMapping
	@RequestMapping("reviews/timespan")
	public ResponseEntity<List<LocalDateTime>> getReviewsTimeline() {
		return new ResponseEntity<List<LocalDateTime>>(rService.getReviewsTimeline(), HttpStatus.OK);
	}

	// Getï¿½s all the Reviews in a timespan
	@GetMapping
	@RequestMapping("business/top/ten/{designation}/{name}")
	public ResponseEntity<List<Business>> getTopRestaurant(@PathVariable String designation,
			@PathVariable String name) {
		List<Business> list = bService.getTopTenRestaurant(designation, name);
		if (list != null) {
			return new ResponseEntity<List<Business>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Business>>(HttpStatus.BAD_REQUEST);
		}
	}

	// Getï¿½s all the Reviews in a timespan
	@GetMapping
	@RequestMapping("business/top/ten/total")
	public ResponseEntity<List<Business>> getTopRestaurantTotal() {
		List<Business> list = bService.getTopTenRestaurants();
		if (list != null) {
			return new ResponseEntity<List<Business>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Business>>(HttpStatus.BAD_REQUEST);
		}
	}

	// Getï¿½s all the checkins of a Business
	@GetMapping
	@RequestMapping("business/checkins/{city}/{name}")
	public ResponseEntity<List<LocalDateTime>> getCheckins(@PathVariable String city, @PathVariable String name) {
		List<LocalDateTime> list = bService.getCheckins(city, name);
		if (list != null) {
			return new ResponseEntity<List<LocalDateTime>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<LocalDateTime>>(HttpStatus.NOT_FOUND);
		}
	}
}