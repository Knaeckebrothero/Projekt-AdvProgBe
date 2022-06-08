package de.fra.uas.AdvProBE.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.repositorys.BusinessRepository;
import lombok.AllArgsConstructor;

//Service class which provides the methods that are used by the API
@AllArgsConstructor
@Service
public class BusinessService {

	private BusinessRepository repository;

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// Returns a Business by city and its name
	public Business GetBusiness(String city, String name) {
		Optional<Business> business = repository.findByCityAndName(city, name);
		if (business.isPresent()) {
			return business.get();
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
		}
		;
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

	// Returns a map Cointaining the City names with the average stars its of
	// Businesses
	public HashMap<String, Double> GetRatingOfAllCitys() {
		List<Business> Business = repository.findAll();
		HashMap<String, Double> CityPlusBusinessRating = new HashMap<>();
		HashMap<String, Integer> Counter = new HashMap<>();
		Double d;
		String c;

		for (Business b : Business) {
			c = b.getAddress().getCity();
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
	public List<Business> GetTopTenRestaurantPerCity(String name) {
		List<Business> list = repository.findByCity(name);
		list.sort(Business.BusinessReviewCountComparator);
		if (list.size() > 10) {
			list.subList(10, list.size()).clear();
		}
		for(Business b: list) {
			b.setCheckins(null);
		}
		return list;
	}

	// Returns a list holding the Top 10 Businesses of the given state
	public List<Business> GetTopTenRestaurantPerState(String name) {
		List<Business> list = repository.findByState(name);
		System.out.println(name);
		System.out.println(list.size());
		list.sort(Business.BusinessReviewCountComparator);
		if (list.size() > 10) {
			list.subList(10, list.size()).clear();
		}
		for(Business b: list) {
			b.setCheckins(null);
		}
		return list;
	}

	// Returns a list holding the Top 10 Businesses total
	public List<Business> GetTopTenRestaurants() {
		List<Business> list = repository.findAll();
		list.sort(Business.BusinessReviewCountComparator);
		if (list.size() > 10) {
			list.subList(10, list.size()).clear();
		}
		for(Business b: list) {
			b.setCheckins(null);
		}
		return list;
	}

	// Returns a list holding the Top 10 Businesses of the given place
	public List<Business> GetTopTenRestaurant(String designation, String name) {

		switch (designation) {
		case "city": {
			return GetTopTenRestaurantPerCity(name);
		}
		case "state": {
			return GetTopTenRestaurantPerState(name);
		}
		default:
			return null;
		}
	}

	public List<LocalDateTime> GetCheckins(String city, String name) {
		Optional<Business> business = repository.findByCityAndName(city, name);
		if(business.isPresent()) {
			return business.get().getCheckins();
		} else {
			return null;
		}
	}

	public List<String> GetAllCitys() {
		LinkedList<Business> list = (LinkedList<Business>) repository.findAll();
		List<String> city = List.of(list.get(0).getAddress().getCity());
		list.remove(0);
		
		for(Business b : list) {
		if(city.contains(b.getAddress().getCity())) {
			
		}
		}
		return null;
	}
}