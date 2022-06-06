package de.fra.uas.AdvProBE.db.entitys;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//Java object representing database document
@Data
@Document
public class Business implements Comparable<Business> {
	// Auto generated id used by the Database
	@Id
	private String id;

	// Values contained by the Yelp data set
	private String businessId;
	private String name;
	private BusinessAddress address;
	private Integer stars;
	private Integer reviewCount;
	private boolean isOpen;
	private HashMap<String, String> attributes;
	private List<String> categories;
	private OpeningHours hours;
	private List<LocalDateTime> checkins;

	public Integer getReviewCount() {
		return reviewCount;
	}

	public int compareTo(Business compareBusiness) {
		Integer compareStars = ((Business) compareBusiness).getReviewCount();
		return this.reviewCount - compareStars;
	}

	public static Comparator<Business> BusinessReviewCountComparator = new Comparator<Business>() {
		public int compare(Business a, Business b) {
			Integer businessReviewCountA = a.getReviewCount();
			Integer businessReviewCountB = b.getReviewCount();
			// ascending order
			return businessReviewCountB.compareTo(businessReviewCountA);
		}

	};

}
