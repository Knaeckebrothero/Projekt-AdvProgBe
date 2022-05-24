package de.fra.uas.AdvProBE.service;

import org.springframework.stereotype.Service;

import de.fra.uas.AdvProBE.db.entitys.YelpUser;
import de.fra.uas.AdvProBE.db.repositorys.YelpUserRepository;
import lombok.AllArgsConstructor;

//Service class which provides the methods that are used by the API
@AllArgsConstructor
@Service
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