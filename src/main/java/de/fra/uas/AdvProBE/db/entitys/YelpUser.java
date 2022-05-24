package de.fra.uas.AdvProBE.db.entitys;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//Java object representing database document
//Named YelpUser to avoid conflicts with spring security user class
@Data
@Document
public class YelpUser {
	// Auto generated id used by the Database
	@Id
	private String id;

	// Values contained by the Yelp data set
	private String userId;
	private String name;
	private int reviewCount;
	private LocalDateTime yelpingSince;
	private Tags tags;
	private List<Integer> elite;
	private List<String> friends;
	private int fans;
	private double averageStars;
	private UserCompliment compliments;
}