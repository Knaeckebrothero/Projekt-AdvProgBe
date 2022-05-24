package de.fra.uas.AdvProBE.db.entitys;

import java.util.HashMap;

import lombok.Data;

@Data
public class OpeningHours {
	private HashMap<Status, String> Monday;
	private HashMap<Status, String> Tuesday;
	private HashMap<Status, String> Wednesday;
	private HashMap<Status, String> Thursday;
	private HashMap<Status, String> Friday;
	private HashMap<Status, String> Saturday;
	private HashMap<Status, String> Sunday;
}
