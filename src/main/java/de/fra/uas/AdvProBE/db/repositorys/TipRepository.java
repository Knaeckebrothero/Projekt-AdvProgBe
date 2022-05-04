package de.fra.uas.AdvProBE.db.repositorys;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.fra.uas.AdvProBE.db.entitys.Tip;

//Interface which is used to get DB commands and create custom ones
public interface TipRepository extends MongoRepository<Tip, String> {
	// Finds and returns a Tip in case it exists
	public Optional<Tip> findTipById(String id);
}