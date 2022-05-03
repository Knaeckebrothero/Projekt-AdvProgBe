package de.fra.uas.AdvProBE.db.repositorys;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.fra.uas.AdvProBE.db.entitys.Review;

//Interface which is used to get DB commands and create custom ones
public interface ReviewRepository extends MongoRepository<Review, String> {
	// Finds and returns a Review in case it exists
	public Optional<Review> findReviewById(String id);
}