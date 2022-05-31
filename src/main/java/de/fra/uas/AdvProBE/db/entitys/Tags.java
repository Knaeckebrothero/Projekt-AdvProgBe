package de.fra.uas.AdvProBE.db.entitys;

import lombok.Data;

//Summary of attributes used to mark a review as either...
@Data
public class Tags {
	private Integer useful;
	private Integer funny;
	private Integer cool;
}
