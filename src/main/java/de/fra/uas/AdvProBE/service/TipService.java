package de.fra.uas.AdvProBE.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import de.fra.uas.AdvProBE.db.entitys.Business;
import de.fra.uas.AdvProBE.db.entitys.Tip;
import de.fra.uas.AdvProBE.db.repositorys.TipRepository;
import lombok.AllArgsConstructor;

//Service class which provides the methods that are used by the API
@AllArgsConstructor
@Service
public class TipService {

	private TipRepository repository;
	private MongoTemplate template;

	// Returns a Tip by ID
	public Tip GetTip(String TipID) {

		if (repository.findTipById(TipID).isPresent()) {
			return repository.findTipById(TipID).get();
		} else {
			return null;
		}
	}

	public Tip getRandomTipTotal() {
		List<Tip> list = repository.findAll();
		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));
	}
	
	public Tip getRandomTip(String designation, String name) {

		switch (designation) {
		case "city": {
			return getRandomTipPerState(name);
		}
		case "state": {
			return getRandomTipPerCity(name);
		}
		default:
			return null;
		}
	}

	private Tip getRandomTipPerState(String name) {
		Query bQuery = new Query();
		bQuery.addCriteria(Criteria.where("city").is(name));
		bQuery.fields().include("businessId").exclude("_id");
		List<Business> bList = template.find(bQuery, Business.class);
		LinkedList<String> sList = new LinkedList<>();
		for(Business b: bList) {
			sList.add(b.getBusinessId());
		}
		
		Query tQuery = new Query();
		tQuery.fields().include("businessId").exclude("_id");
		List<Tip> tList = template.find(tQuery, Tip.class);
		
		for(int i=0; i> sList.size();i++) {
			if(sList.contains(tList.get(i).getBusinessId()));
			
		}
		
		return null;
	}

	private Tip getRandomTipPerCity(String name) {
		//List<Tip> list = template.find(query, Tip.class);
		return null;
	}
}
