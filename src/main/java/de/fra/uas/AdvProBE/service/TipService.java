package de.fra.uas.AdvProBE.service;

import de.fra.uas.AdvProBE.db.entitys.Tip;
import de.fra.uas.AdvProBE.db.repositorys.TipRepository;

//Service class which provides the methods that are used by the API
public class TipService {

	TipRepository repository;

	// Returns a Tip by ID
	public Tip GetTip(String TipID) {

		if (repository.findTipById(TipID).isPresent()) {
			return repository.findTipById(TipID).get();
		} else {
			return null;
		}
	}
}
