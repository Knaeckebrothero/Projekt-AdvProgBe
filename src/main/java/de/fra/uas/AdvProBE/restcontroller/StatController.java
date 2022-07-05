package de.fra.uas.AdvProBE.restcontroller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.entitys.Tip;
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
	@GetMapping("busines/countCity")
	public ResponseEntity<List<String>> getBusinessofAllCitys() {
		return new ResponseEntity<List<String>>(bService.getBusinessofAllCitys(), HttpStatus.OK);
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
		return new ResponseEntity<List<String>>(bService.getRatingOfAllCitys(), HttpStatus.OK);
	}

	// KAPUT REPARIEREN!!!
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
	public ResponseEntity<List<String>> getReviewsofAllCitys() {
		return new ResponseEntity<List<String>>(rService.getReviewsofAllCitys(), HttpStatus.OK);
	}

	// Get�s all the Reviews in a timespan
	@GetMapping("reviews/timespan")
	public ResponseEntity<List<LocalDateTime>> getReviewsTimeline() {
		return new ResponseEntity<List<LocalDateTime>>(rService.getReviewsTimeline(), HttpStatus.OK);
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
		List<Business> list = bService.getTopTenRestaurantTotal();
		if (list != null) {
			return new ResponseEntity<List<Business>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Business>>(HttpStatus.BAD_REQUEST);
		}
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