package de.fra.uas.AdvProBE.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.repositorys.BusinessRepository;
import lombok.AllArgsConstructor;

//Service class which provides the methods that are used by the API
@AllArgsConstructor
@Service
public class BusinessService {

	private BusinessRepository repository;

	// Returns a Business by ID
	public Business GetBusiness(String Business) {
		if (repository.findBusinessByName(Business).isPresent()) {
			return repository.findBusinessById(Business).get();
		} else {
			return null;
		}
	}

	// Returns the number of Businesses in a City
	public HashMap<String, Integer> GetBusinessesPerCity(String city) {
		List<Business> BusinessOfCity = repository.findByCity(city);
		if (BusinessOfCity != null) {
			HashMap<String, Integer> map = new HashMap<>();
			map.put(city, BusinessOfCity.size());
			return map;
		} else {
			return null;
		}
	}

	// Returns a map Cointaining the City names with the number of Businesses
	public HashMap<String, Integer> GetBusinessofAllCitys() {
		List<Business> Business = repository.findAll();
		HashMap<String, Integer> CityPlusBusinessCount = new HashMap<>();
		Integer i;
		String c;

		for (Business b : Business) {
			c = b.getAddress().getCity();
			if (CityPlusBusinessCount.containsKey(c)) {
				i = CityPlusBusinessCount.get(b.getAddress().getCity()) + 1;
				CityPlusBusinessCount.replace(c, i);
			} else {
				CityPlusBusinessCount.put(c, 1);
			}
		};
		return CityPlusBusinessCount;
	}

	// Returns the average Rating for the given City
	public HashMap<String, Double> GetRatingOfCity(String city) {
		List<Business> BusinessOfCity = repository.findByCity(city);
		Integer rating = 0;

		for (Business b : BusinessOfCity) {
			rating += b.getStars();
		}
		if (BusinessOfCity != null && BusinessOfCity.size() != 0) {
			HashMap<String, Double> map = new HashMap<>();
			map.put(city, Double.valueOf(rating / BusinessOfCity.size()));
			return map;
		} else {
			return null;
		}
	}
}
