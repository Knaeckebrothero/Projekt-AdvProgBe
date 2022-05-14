package de.fra.uas.AdvProBE.db.entitys;

import lombok.Data;

//Summary of attributes used to mark a review as either...
@Data
public class Tags {
	private int useful;
	private int funny;
	private int cool;
}
