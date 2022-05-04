package de.fra.uas.AdvProBE.db.entitys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//Java object representing database document
@Data
@Document
public class Review {
	//Auto generated id used by the Database
	@Id
	private String id;
	//Dummy object without values
}
