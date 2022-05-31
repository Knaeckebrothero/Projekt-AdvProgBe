package de.fra.uas.AdvProBE.db.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.fra.uas.AdvProBE.db.entitys.Review;

//Interface which is used to get DB commands and create custom ones
public interface ReviewRepository extends MongoRepository<Review, String> {
	// Finds and returns a Review in case it exists
	public Optional<Review> findReviewById(String id);
	public List<Review> findReviewByBusinessId(String id);

	// Custom Query´s
	@Query(value = "{'businessId': {$regex: ?0, $options: 'i'}}", count = true)
	public Integer countFetchedDocumentsForRievwId(String id);

	@Query("{text:0}")
	List<Review> findAll();
}