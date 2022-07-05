package de.fra.uas.AdvProBE.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.service.BusinessService;
import lombok.AllArgsConstructor;

//Controller that deals with all the information needed to display the map
@AllArgsConstructor
@RestController
@RequestMapping("map/")
public class MapController {

	private BusinessService bService;

	// Get All Businesses (Only for development purposes)
	@GetMapping("Preview")
	public ResponseEntity<List<Business>> getAllBusinesses() {
		return new ResponseEntity<List<Business>>(bService.getAllBusinesses(), HttpStatus.OK);
	}
	
	// Get´s a list with all the citynames
	@GetMapping("citys")
	public ResponseEntity<List<String>> getAllCitys() {
		return new ResponseEntity<List<String>>(bService.getAllCitys(), HttpStatus.OK);
	}

	// Get´s a list with all the businesses for a city
	@GetMapping("businesses/{city}/")
	public ResponseEntity<List<String>> getAllBusinessesForCity(@PathVariable String city) {
		return new ResponseEntity<List<String>>(bService.getAllCitys(), HttpStatus.OK);
	}
	
	//Get´s a list of all the categories
	@GetMapping("categories")
	public ResponseEntity<List<String>> getAllCategories(){
		return new ResponseEntity<List<String>> (bService.getAllCategories(),HttpStatus.OK);
	}
}