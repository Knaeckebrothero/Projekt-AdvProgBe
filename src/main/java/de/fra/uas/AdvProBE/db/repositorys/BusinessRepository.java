package de.fra.uas.AdvProBE.db.repositorys;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.fra.uas.AdvProBE.db.entitys.Business;

//Interface which is used to get DB commands and create custom ones
public interface BusinessRepository extends MongoRepository<Business, String> {
	// Finds and returns a Business in case it exists
	public Optional<Business> findBusinessById(String id);
}
