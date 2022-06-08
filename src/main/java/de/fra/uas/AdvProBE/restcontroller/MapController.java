package de.fra.uas.AdvProBE.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fra.uas.AdvProBE.service.BusinessService;
import lombok.AllArgsConstructor;

//Controller that deals with all the information needed to display the map
@AllArgsConstructor
@RestController
@RequestMapping("map/")
public class MapController {

	private BusinessService bService;

	// Get´s all the counts´s of Businesses found in all Citys
	@GetMapping("citys")
	public ResponseEntity<List<String>> GetBusinessofAllCitys() {
		return new ResponseEntity<List<String>>(bService.GetAllCitys(), HttpStatus.OK);
	}
}