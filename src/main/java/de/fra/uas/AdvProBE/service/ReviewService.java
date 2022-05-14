package de.fra.uas.AdvProBE.service;

import org.springframework.stereotype.Service;

import de.fra.uas.AdvProBE.db.entitys.Review;
import de.fra.uas.AdvProBE.db.repositorys.ReviewRepository;
import lombok.AllArgsConstructor;

//Service class which provides the methods that are used by the API
@AllArgsConstructor
@Service
public class ReviewService {

	ReviewRepository repository;

	// Returns a Review by ID
	public Review GetReview(String ReviewID) {

		if (repository.findReviewById(ReviewID).isPresent()) {
			return repository.findReviewById(ReviewID).get();
		} else {
			return null;
		}
	}
}
