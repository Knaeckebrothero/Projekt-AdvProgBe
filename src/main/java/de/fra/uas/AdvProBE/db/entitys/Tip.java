package de.fra.uas.AdvProBE.db.entitys;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//Java object representing database document
@Data
@Document
public class Tip {
	// Auto generated id used by the Database
	@Id
	private String id;

	// Values contained by the Yelp data set
	private String userId;
	private String businessId;
	private String text;
	private LocalDateTime date;
	private int complimentCount;
}
