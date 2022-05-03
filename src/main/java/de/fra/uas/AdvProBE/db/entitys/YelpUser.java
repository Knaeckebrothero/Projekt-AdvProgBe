package de.fra.uas.AdvProBE.db.entitys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//Java object representing database document
//Named YelpUser to avoid conflicts with spring security user class
@Data
@Document
public class YelpUser {
	//Auto generated id used by the Database
	@Id
	private String id;
	//Dummy object without values
}
