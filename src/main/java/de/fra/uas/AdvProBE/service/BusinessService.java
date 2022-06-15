package de.fra.uas.AdvProBE.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.repositorys.BusinessRepository;
import lombok.AllArgsConstructor;

//Service class which provides the methods that are used by the API
@AllArgsConstructor
@Service
public class BusinessService {

	private BusinessRepository repository;
	private MongoTemplate template;

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// Returns a Business by city and its name
	public Business getBusiness(String city, String name) {
		Optional<Business> business = repository.findByCityAndName(city, name);
		if (business.isPresent()) {
			return business.get();
		} else {
			return null;
		}
	}

	// Returns the number of Businesses in a City
	public HashMap<String, Integer> getBusinessesPerCity(String city) {
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
	public HashMap<String, Integer> getBusinessofAllCitys() {
		List<Business> Business = repository.findAll();
		HashMap<String, Integer> CityPlusBusinessCount = new HashMap<>();
		Integer i;
		String c;

		for (Business b : Business) {
			c = b.getCity();
			if (CityPlusBusinessCount.containsKey(c)) {
				i = CityPlusBusinessCount.get(b.getCity()) + 1;
				CityPlusBusinessCount.replace(c, i);
			} else {
				CityPlusBusinessCount.put(c, 1);
			}
		}
		;
		return CityPlusBusinessCount;
	}

	// Returns the average Rating for the given City
	public HashMap<String, Double> getRatingOfCity(String city) {
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

	// Returns a map Cointaining the City names with the average stars its of
	// Businesses
	public HashMap<String, Double> getRatingOfAllCitys() {
		List<Business> Business = repository.findAll();
		HashMap<String, Double> CityPlusBusinessRating = new HashMap<>();
		HashMap<String, Integer> Counter = new HashMap<>();
		Double d;
		String c;

		for (Business b : Business) {
			c = b.getCity();
			if (CityPlusBusinessRating.containsKey(c) && b.getStars() != null) {
				Counter.merge(c, 1, Integer::sum);
				d = CityPlusBusinessRating.get(c) + b.getStars();
				CityPlusBusinessRating.replace(c, d);
			} else {
				CityPlusBusinessRating.put(c, b.getStars().doubleValue());
				Counter.put(c, 1);
			}
		}
		;
		for (String city : Counter.keySet()) {
			d = round(CityPlusBusinessRating.get(city) / Counter.get(city), 2);
			CityPlusBusinessRating.replace(city, d);
		}
		return CityPlusBusinessRating;
	}

	// Returns a list holding the Top 10 Businesses of the given city
	public List<Business> getTopTenRestaurantPerCity(String name) {
		List<Business> list = repository.findByCity(name);
		list.sort(Business.BusinessReviewCountComparator);
		if (list.size() > 10) {
			list.subList(10, list.size()).clear();
		}
		for (Business b : list) {
			b.setCheckins(null);
		}
		return list;
	}

	// Returns a list holding the Top 10 Businesses of the given state
	public List<Business> getTopTenRestaurantPerState(String name) {
		List<Business> list = repository.findByState(name);
		System.out.println(name);
		System.out.println(list.size());
		list.sort(Business.BusinessReviewCountComparator);
		if (list.size() > 10) {
			list.subList(10, list.size()).clear();
		}
		for (Business b : list) {
			b.setCheckins(null);
		}
		return list;
	}

	// Returns a list holding the Top 10 Businesses total
	public List<Business> getTopTenRestaurants() {
		List<Business> list = repository.findAll();
		list.sort(Business.BusinessReviewCountComparator);
		if (list.size() > 10) {
			list.subList(10, list.size()).clear();
		}
		for (Business b : list) {
			b.setCheckins(null);
		}
		return list;
	}

	// Returns a list holding the Top 10 Businesses of the given place
	public List<Business> getTopTenRestaurant(String designation, String name) {

		switch (designation) {
		case "city": {
			return getTopTenRestaurantPerCity(name);
		}
		case "state": {
			return getTopTenRestaurantPerState(name);
		}
		default:
			return null;
		}
	}

	// Returns a list with all the checkins for a business in the given city
	public List<LocalDateTime> getCheckins(String city, String name) {
		Optional<Business> business = repository.findByCityAndName(city, name);
		if (business.isPresent()) {
			return business.get().getCheckins();
		} else {
			return null;
		}
	}

	// Returns a list with all the citynames
	public List<String> getAllCitys() {
		List<Business> list = repository.findAll();
		Query query = new Query();
		List<String> city = Arrays.asList(list.get(0).getCity(), list.get(1).getCity());
		String s = null;
		
		query.fields().include("address").exclude("_id");
		
		System.out.println(city);
		System.out.println(list.size());
		System.out.println(city.size());

		for (Business b : list) {
			System.out.println(b.getCity());
			s = b.getCity();
			System.out.println(city.contains(s));
			if (city.contains(s)) {
			} else {
				//System.out.println("davor "+s);
				city.add(s);
				//System.out.println("schleifeIf");
			}
		}
		System.out.println(city);
		return city;
	}

	public List<Business> getAllBusinesses() {
		
		List<Business> list = repository.findAll();
		list.subList(50, list.size()).clear();
		return list;
		//return repository.findAll();
	}
}