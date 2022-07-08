package de.fra.uas.AdvProBE.restcontroller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.entitys.Tip;
import de.fra.uas.AdvProBE.preCalculate.preProcessed;
import de.fra.uas.AdvProBE.preCalculate.preProcessedReviews;
import de.fra.uas.AdvProBE.service.BusinessService;
import de.fra.uas.AdvProBE.service.ReviewService;
import de.fra.uas.AdvProBE.service.TipService;
import lombok.AllArgsConstructor;

//Controller that deals with all the information needed to display statistics
@AllArgsConstructor
@RestController
@RequestMapping("stat/")
public class StatController {

	private BusinessService bService;
	private ReviewService rService;
	private TipService tService;
	private MongoTemplate template;

	// Get�s a business with it�s information
	@GetMapping("business/custom/{city}/{name}")
	public ResponseEntity<Business> getBusiness(@PathVariable String city, @PathVariable String name) {
		Business business = bService.getBusiness(city, name);

		if (business != null) {
			return new ResponseEntity<Business>((business.nullNonInfo()), HttpStatus.OK);
		} else {
			return new ResponseEntity<Business>(HttpStatus.NOT_FOUND);
		}
	}

	// Get�s the number of Businesses found in the given City
	@GetMapping("business/totalStats/{city}")
	public ResponseEntity<Integer> getBusinessPerCity(@PathVariable String city) {
		Integer i = bService.getBusinessesPerCity(city);
		if (i != null) {
			return new ResponseEntity<Integer>(i, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
	}

	// Get�s all the counts�s of Businesses found in all Citys
	@GetMapping("business/countCity")
	public ResponseEntity<List<String>> getBusinessofAllCitys() {
		Query query = new Query();
		query.fields().include("allBusinessesPerCity").exclude("_id");
		return new ResponseEntity<List<String>>(template.find(query, preProcessed.class).get(0).getAllBusinessesPerCity(), HttpStatus.OK);
	}

	// Get�s the average Rating for the given City
	@GetMapping("city/rating/average/{city}")
	public ResponseEntity<Double> getRatingOfCity(@PathVariable String city) {
		Double d = bService.getRatingOfCity(city);
		if (d != null) {
			return new ResponseEntity<Double>(d, HttpStatus.OK);
		} else {
			return new ResponseEntity<Double>(HttpStatus.NOT_FOUND);
		}
	}

	// Get�s the average Rating of all Citys
	@GetMapping("city/all/rating/average")
	public ResponseEntity<List<String>> getRatingOfAllCity() {
		Query query = new Query();
		query.fields().include("allAverageRatingsPerCity").exclude("_id");
		return new ResponseEntity<List<String>>(template.find(query, preProcessed.class).get(0).getAllAverageRatingsPerCity(), HttpStatus.OK);
	}

	//Gets the sum of reivews written for a business in the given city
	@GetMapping("reviews/city/{city}")
	public ResponseEntity<Integer> getReviewsPerCity(@PathVariable String city) {
		Integer i = rService.getReviewsPerCity(city);
		if (i != null) {
			return new ResponseEntity<Integer>(i, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
	}

	// KAPUT REPARIEREN!!!
	@GetMapping("review/all/city/count")
	public ResponseEntity<HashMap<String, Integer>> getReviewsofAllCitys() {
		return new ResponseEntity<HashMap<String, Integer>>(rService.getReviewsofAllCitys(), HttpStatus.OK);
	}

	// Get�s all the Reviews in a timespan
	@GetMapping("reviews/timespan/{counter}")
	public ResponseEntity<List<LocalDateTime>> getReviewsTimeline(@PathVariable Integer counter) {
		Query query = new Query();
		query.fields().include("allReviewsTimespan","counter").exclude("_id");
		query.addCriteria(Criteria.where("preprocessedAtDay").is(LocalDate.now()).and("counter").is(counter));
		return new ResponseEntity<List<LocalDateTime>>(template.find(query, preProcessedReviews.class).get(0).getAllReviewsTimespan(), HttpStatus.OK);
	}

	// Gets the top 10 Businesses
	@GetMapping("business/top/ten/{designation}/{name}")
	public ResponseEntity<List<Business>> getTopRestaurant(@PathVariable String designation,
			@PathVariable String name) {
		List<Business> list = bService.getTopTenRestaurant(designation, name);
		if (list != null) {
			return new ResponseEntity<List<Business>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Business>>(HttpStatus.BAD_REQUEST);
		}
	}

	// Gets the top 10 Businesses worldwide
	@GetMapping("business/top/ten/total")
	public ResponseEntity<List<Business>> getTopRestaurantTotal() {
		Query query = new Query();
		query.fields().include("topTenBusinessesWorldWide").exclude("_id");
			return new ResponseEntity<List<Business>>(template.find(query, preProcessed.class).get(0).getTopTenBusinessesWorldWide(), HttpStatus.OK);
	}

	// Get�s all the checkins of a Business
	@GetMapping("business/checkins/{city}/{name}")
	public ResponseEntity<List<LocalDateTime>> getCheckins(@PathVariable String city, @PathVariable String name) {
		List<LocalDateTime> list = bService.getCheckins(city, name);
		if (list != null) {
			return new ResponseEntity<List<LocalDateTime>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<LocalDateTime>>(HttpStatus.NOT_FOUND);
		}
	}

	// Gets random Tip worldwide
	@GetMapping("tip/random/total")
	public ResponseEntity<Tip> getRadnomTip() {
		return new ResponseEntity<Tip> (tService.getRandomTipTotal(), HttpStatus.OK);
	}

	// Gets random Tip
	@GetMapping("tip/random/{designation}/{name}")
	public ResponseEntity<Tip> getTopTipTotal(@PathVariable String designation, @PathVariable String name) {
		Tip t = tService.getRandomTip(designation, name);
		if (t != null) {
			return new ResponseEntity<Tip>(t, HttpStatus.OK);
		} else {
			return new ResponseEntity<Tip>(HttpStatus.BAD_REQUEST);
		}
	}
}