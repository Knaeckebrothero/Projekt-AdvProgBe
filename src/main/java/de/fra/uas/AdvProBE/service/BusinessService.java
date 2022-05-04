package de.fra.uas.AdvProBE.service;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.repositorys.BusinessRepository;

//Service class which provides the methods that are used by the API
public class BusinessService {

	private BusinessRepository repository;

	// Returns a Business by ID
	public Business GetBusiness(String BusinessID) {

		if (repository.findBusinessById(BusinessID).isPresent()) {
			return repository.findBusinessById(BusinessID).get();
		} else {
			return null;
		}
	}
}
