package de.fra.uas.AdvProBE.db.repositorys;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.fra.uas.AdvProBE.db.entitys.YelpUser;

//Interface which is used to get DB commands and create custom ones
public interface YelpUserRepository extends MongoRepository<YelpUser, String>{
}
