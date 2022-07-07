package de.fra.uas.AdvProBE.preCalculate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//Java object representing database document
@Data
@Document
public class preProcessedReviews {
	// Auto generated id used by the Database
	@Id
	private String id;

	// General Info
	private LocalDate preprocessedAtDay;
	private int counter;
	
	//Values
	private List<LocalDateTime> allReviewsTimespan;

	public preProcessedReviews(LocalDate preprocessedAtDay, int counter,
			List<LocalDateTime> allReviewsTimespan) {
		super();
		this.preprocessedAtDay = preprocessedAtDay;
		this.counter = counter;
		this.allReviewsTimespan = allReviewsTimespan;
	}
}
