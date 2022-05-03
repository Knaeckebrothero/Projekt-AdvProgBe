package de.fra.uas.AdvProBE.db.repositorys;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.fra.uas.AdvProBE.db.entitys.Checkin;

//Interface which is used to get DB commands and create custom ones
public interface CheckinRepository extends MongoRepository<Checkin, String> {
}
