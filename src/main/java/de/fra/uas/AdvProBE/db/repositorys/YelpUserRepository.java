package de.fra.uas.AdvProBE.db.repositorys;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.fra.uas.AdvProBE.db.entitys.YelpUser;

//Interface which is used to get DB commands and create custom ones
public interface YelpUserRepository extends MongoRepository<YelpUser, String> {
	// Finds and returns a User in case it exists
	public Optional<YelpUser> findYelpUserById(String id);
}