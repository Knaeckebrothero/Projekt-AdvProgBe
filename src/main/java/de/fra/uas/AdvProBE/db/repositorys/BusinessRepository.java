package de.fra.uas.AdvProBE.db.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.fra.uas.AdvProBE.db.entitys.Business;

//Interface which is used to get DB commands and create custom ones
public interface BusinessRepository extends MongoRepository<Business, String> {
	// Finds and returns a Business in case it exists
	public Optional<Business> findBusinessById(String id);
	public Optional<Business> findBusinessByName(String name);
	
	//Custom Query`s
	@Query("{'address.city': ?0}")
	  List<Business> findByCity(String city);
}
