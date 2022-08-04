package de.fra.uas.AdvProBE.preCalculate;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import de.fra.uas.AdvProBE.db.entitys.Business;
import lombok.Data;

//Java object representing database document
@Data
@Document
public class PreProcessed {
	// Auto generated id used by the Database
	@Id
	private String id;

	// General Info
	private LocalDate preprocessedAtDay;
	private long timePreprocessingTookInMs;

	// Preprocessed Data
	private List<LocalDate> allDates;
	private List<String> allBusinessesPerCity;
	private List<String> allAverageRatingsPerCity;
	//private List<String> allReviewsForAllCitys;
	private List<Business> topTenBusinessesWorldWide;

	public PreProcessed(LocalDate preprocessedAtDay) {
		super();
		this.preprocessedAtDay = preprocessedAtDay;
		/*this.timePreprocessingTookInMs = timePreprocessingTookInMs;
		this.allDates = allDates;
		this.allBusinessesPerCity = allBusinessesPerCity;
		this.allAverageRatingsPerCity = allAverageRatingsPerCity;
		this.allReviewsTimespan = allReviewsTimespan;
		this.topTenBusinessesWorldWide = topTenBusinessesWorldWide;
		*/
	}
}
