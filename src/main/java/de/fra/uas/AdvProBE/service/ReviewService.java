package de.fra.uas.AdvProBE.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.entitys.Review;
import de.fra.uas.AdvProBE.db.repositorys.BusinessRepository;
import de.fra.uas.AdvProBE.db.repositorys.ReviewRepository;
import lombok.AllArgsConstructor;

//Service class which provides the methods that are used by the API
@AllArgsConstructor
@Service
public class ReviewService {

	private ReviewRepository repository;
	private BusinessRepository bRepository;
	private MongoTemplate template;

	// Returns a Review by ID
	public Review GetReview(String ReviewID) {
		if (repository.findReviewById(ReviewID).isPresent()) {
			return repository.findReviewById(ReviewID).get();
		} else {
			return null;
		}
	}

	// Returns the number of Reviews written for a Business of the given City
	public HashMap<String, Integer> GetReviewsPerCity(String city) {

		Query query = new Query();
		query.fields().exclude("text");

		List<Business> BusinessOfCity = bRepository.findByCity(city);
		Integer reviews = 0;
		for (Business b : BusinessOfCity) {
			reviews += repository.findReviewByBusinessId(b.getBusinessId()).size();
		}
		if (reviews < 0) {
			HashMap<String, Integer> map = new HashMap<>();
			map.put(city, reviews);
			return map;
		} else {
			return null;
		}
	}

	// Returns a map Cointaining the City names with the number of reviews
	public HashMap<String, Integer> GetReviewsofAllCitys() {
		LinkedList<Review> Reviews = new LinkedList<>();
		Reviews.addAll(repository.findAll());
		LinkedList<Business> Business = new LinkedList<>();
		Business.addAll(bRepository.findAll());
		HashMap<String, Integer> CityPlusReviewsCount = new HashMap<>();
		// int rCount = Reviews.size();
		int rCount = 3;
		Integer i;
		String c;

		for (int count = 0; count > rCount; count++) {
			c = Business.get(count).getAddress().getCity();
			if (CityPlusReviewsCount.containsKey(c)) {
				i = repository.countFetchedDocumentsForRievwId(Business.get(count).getId());
				CityPlusReviewsCount.replace(c, i);
			} else {
				CityPlusReviewsCount.put(c, repository.countFetchedDocumentsForRievwId(Business.get(count).getId()));
			}
		}
		;
		return CityPlusReviewsCount;
	}

	// Returns a sorted list with dates representing the date at which a review was
	// written
	public List<LocalDateTime> GetReviewsTimeline() {
		Query query = new Query();
		query.fields().include("date").exclude("_id");
		// query.with(Sort.by(Sort.Direction.ASC, "date"));
		List<Review> list = template.find(query, Review.class);
		ArrayList<LocalDateTime> listDate = new ArrayList<>();

		for (Review r : list) {
			listDate.add(r.getDate());
		}
		Collections.sort(listDate);
		return listDate;
	}
}
