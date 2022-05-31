package de.fra.uas.AdvProBE.db.entitys;

import lombok.Data;

//Summary of attributes related to address data
@Data
public class BusinessAddress {
	private String address;
	private String city;
	private String state;
	private Integer postalCode;
	private Coordinates coordinates;
}
