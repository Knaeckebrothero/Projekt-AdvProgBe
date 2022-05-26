package de.fra.uas.AdvProBE.db.entitys;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//Java object representing database document
@Data
@Document
public class Business {
	// Auto generated id used by the Database
	@Id
	private String id;

	// Values contained by the Yelp data set
	private String businessId;
	private String name;
	private BusinessAddress address;
	private int stars;
	private int reviewCount;
	private boolean isOpen;
	private HashMap<String, String> attributes;
	private List<String> categories;
	private OpeningHours hours;
	private List<LocalDateTime> checkins;
	public Integer getStars() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getBusinessId() {
		// TODO Auto-generated method stub
		return null;
	}
}
