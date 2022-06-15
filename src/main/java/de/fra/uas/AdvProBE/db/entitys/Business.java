package de.fra.uas.AdvProBE.db.entitys;

import java.time.LocalDateTime;
import java.util.Comparator;
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
	// Address Data
	private String address;
	private String city;
	private String state;
	private Integer postalCode;
	// Coordinates
	private double latitude;
	private double longitude;
	// Infos
	private Integer stars;
	private Integer reviewCount;
	private boolean isOpen;
	private List<String> categories;
	//Opening Hours
	private String Monday;
	private String Tuesday;
	private String Wednesday;
	private String Thursday;
	private String Friday;
	private String Saturday;
	private String Sunday;
	//Checkins
	private List<LocalDateTime> checkins;
	
	public Integer getReviewCount() {
		return reviewCount;
	}
	
	public Business nullNonInfo () {
		this.id = null;
		this.businessId = null;
		this.checkins = null;
		return this;
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
