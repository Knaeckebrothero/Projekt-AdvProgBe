package de.fra.uas.AdvProBE.service;

import de.fra.uas.AdvProBE.db.entitys.Checkin;
import de.fra.uas.AdvProBE.db.repositorys.CheckinRepository;

//Service class which provides the methods that are used by the API
public class CheckinService {

	CheckinRepository repository;

	// Returns a Checkin by ID
	public Checkin GetCheckin(String CheckinID) {

		if (repository.findCheckinById(CheckinID).isPresent()) {
			return repository.findCheckinById(CheckinID).get();
		} else {
			return null;
		}
	}
}