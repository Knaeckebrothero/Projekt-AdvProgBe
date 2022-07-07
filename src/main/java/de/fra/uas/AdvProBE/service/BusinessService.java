package de.fra.uas.AdvProBE.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.entitys.Review;
import de.fra.uas.AdvProBE.db.entitys.Tip;
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

	private static Query businessFormat() {
		Query query = new Query();
		query.fields().exclude("_id").include("name", "city", "latitude", "longitude", "stars", "reviewCount",
				"isOpen");
		return query;
	}

	// Returns a Business by city and its name
	public Business getBusiness(String city, String name) {
		List<Business> business = template.find(businessFormat().addCriteria(Criteria.where("city").is(city).and("name").is(name)), Business.class);
		if (!business.isEmpty()) {
			return business.get(0);
		} else {
			return null;
		}
	}

	// Returns the number of Businesses in a City
	public Integer getBusinessesPerCity(String city) {
		return repository.countFetchedDocumentsForBusinessCity(city);
	}

	// Returns a map Cointaining the City names with the number of Businesses
	public List<String> getBusinessofAllCitys() {
		Query query = new Query();
		query.fields().include();
		List<Business> Business = template.find(query, Business.class);

		ArrayList<String> CityPlusBusinessCount = new ArrayList<>();
		Integer i = 0;
		Integer count;
		String c;

		for (Business b : Business) {
			c = b.getCity();
			if (CityPlusBusinessCount.contains(c)) {
				i = CityPlusBusinessCount.indexOf(c) + 1;
				count = Integer.parseInt(CityPlusBusinessCount.get(CityPlusBusinessCount.indexOf(c) + 1)) + 1;
				CityPlusBusinessCount.set(i, count.toString());
			} else {
				CityPlusBusinessCount.add(c);
				CityPlusBusinessCount.add("1");
			}
		}
		;
		return CityPlusBusinessCount;
	}

	// Returns the average Rating for the given City
	public Double getRatingOfCity(String city) {
		Query query = new Query();
		query.fields().include("stars").exclude("_id");
		query.addCriteria(Criteria.where("city").is(city));
		List<Business> BusinessOfCity = template.find(query, Business.class);
		Integer rating = 0;

		for (Business b : BusinessOfCity) {
			rating += b.getStars();
		}
		if (BusinessOfCity != null && BusinessOfCity.size() != 0) {
			return round(Double.valueOf(Double.valueOf(rating) / BusinessOfCity.size()), 2);
		} else {
			return null;
		}
	}

	// Returns a map Cointaining the City names with the average stars its of
	// Businesses
	public HashMap<String, Double> getRatingOfAllCitysOLD() {
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

	// Returns a map Cointaining the City names with the average stars its of
	// Businesses
	public List<String> getRatingOfAllCitys() {
		List<String> business = getAllCitys();
		ArrayList<String> list = new ArrayList<>();

		for (String b : business) {
			list.add(b);
			list.add(getRatingOfCity(b).toString());
		}
		return list;
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
	public List<Business> getTopTenRestaurantTotal() {
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
		Query query = new Query();
		query.fields().include("city").exclude("_id");

		List<Business> list = template.find(query, Business.class);
		ArrayList<String> city = new ArrayList<>();
		String s = "Santa Barbara";

		for (Business b : list) {
			s = b.getCity();

			if (!(city.contains(s) || s == null)) {
				city.add(s);
			}
		}
		return city;
	}

	public List<String> getAllBusinessesForACity(String city) {
		Query query = new Query();
		query.addCriteria(Criteria.where("city").is(city));
		query.fields().include("name").exclude("_id");

		List<Business> bList = template.find(query, Business.class);
		List<String> nList = new ArrayList();

		for (Business b : bList) {
			nList.add(b.getName());
		}
		return nList;
	}

	public List<Business> getAllBusinesses() {
		/*
		 * List<Business> list = repository.findAll(); list.subList(50,
		 * list.size()).clear(); return list;
		 */
		return repository.findAll();
	}

	public List<String> getAllCategories() {
		Query query = new Query();
		query.fields().include("categories").exclude("_id");
		List<Business> list = template.find(query, Business.class);
		List<String> categories = new ArrayList<>();

		for (Business b : list) {
			for (String s : b.getCategories()) {
				if (!categories.contains(s)) {
					categories.add(s);
				}
			}
		}
		return categories;
	}

	public List<LocalDate> getAllDates() {
		Query bQuery = new Query();
		bQuery.fields().include("checkins").exclude("_id");
		List<Business> bList = template.find(bQuery, Business.class);

		Query query = new Query();
		query.fields().include("date").exclude("_id");
		List<Review> rList = template.find(query, Review.class);

		List<Tip> tList = template.find(query, Tip.class);

		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate l;

		List<LocalDate> categories = new ArrayList<>();

		for (Business b : bList) {
			if (b.getCheckins() != null) {
				for (LocalDateTime s : b.getCheckins()) {
					l = s.toLocalDate();
					// l.format(formatter);
					if (!categories.contains(l)) {
						categories.add(l);
					}
				}
			}
		}

		for (Review r : rList) {
			if (r != null) {
				l = r.getDate().toLocalDate();
				if (!categories.contains(l)) {
					categories.add(l);
				}
			}
		}

		for (Tip t : tList) {
			if (t != null) {
				l = t.getDate().toLocalDate();
				if (!categories.contains(l)) {
					categories.add(l);
				}
			}
		}
		Collections.sort(categories);
		return categories;
	}

	public List<Business> getFilteredBusinesses(String state, String city, String stars, String open,
			String review) {
		Query filter = businessFormat();
		
		if(!state.equals("empty")) {
			filter.addCriteria(Criteria.where("state").is(state));
		}
		
		if(!city.equals("empty")) {
			filter.addCriteria(Criteria.where("city").is(city));
		}
		
		if(!stars.equals("empty")) {
			filter.addCriteria(Criteria.where("stars").is(Integer.parseInt(stars)));
		}
		
		if(!open.equals("empty")) {
			filter.addCriteria(Criteria.where("isOpen").is(Boolean.parseBoolean(open)));
		}
		
		if(!review.equals("empty")) {
			filter.addCriteria(Criteria.where("reviewCount").is(Integer.parseInt(review)));
		}
		
		return template.find(filter, Business.class);
	}
}