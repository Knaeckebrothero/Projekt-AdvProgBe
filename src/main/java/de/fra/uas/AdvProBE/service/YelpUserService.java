package de.fra.uas.AdvProBE.service;

import de.fra.uas.AdvProBE.db.entitys.YelpUser;
import de.fra.uas.AdvProBE.db.repositorys.YelpUserRepository;

//Service class which provides the methods that are used by the API
public class YelpUserService {

	YelpUserRepository repository;

	// Returns a Tip by ID
	public YelpUser GetYelpUser(String UserID) {

		if (repository.findYelpUserById(UserID).isPresent()) {
			return repository.findYelpUserById(UserID).get();
		} else {
			return null;
		}
	}
}