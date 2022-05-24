@Bean
	CommandLineRunner convertingBusinessCheckin(BusinessRepository newRepo, BusinessRepositoryOld oldRepo,
			CheckinRepositoryOld oldRepoCheckin) {
		return args -> {

			// Sends a request to the DB which removes all existing business documents
			System.out.println("Preparing...");
			newRepo.deleteAll();

			// Puts all old businesses and checkins into Lists
			List<yelp_academic_dataset_business> list = oldRepo.findAll();
			List<yelp_academic_dataset_checkin> listC = oldRepoCheckin.findAll();
			// Returns an empty list which can be filled later on
			// Don't ask me how this shit works i have no idea xD
			List<Business> PreWrite = newRepo.findAll();

			// Declares some data storages which will be used later
			ArrayList<String> IncompleteData = new ArrayList<String>();
			HashMap<String, List<LocalDateTime>> CheckinMap = new HashMap<String, List<LocalDateTime>>();
			// Attributes are a placeholder, need to come back to this at a later date
			HashMap<String, String> attributes = new HashMap<String, String>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String[] DayString;

			// Initializing control variables
			int old = list.size();
			int oldC = listC.size();

			// Converting Checking data form string object into a date map
			for (int i = 0; i < oldC; i++) {
				System.out.println("Converting Checkins: " + oldC + "/" + i);
				String[] dateString = listC.get(i).getDate().split(", ");
				ArrayList<LocalDateTime> dateArray = new ArrayList<>();
				for (String e : dateString) {
					dateArray.add(LocalDateTime.parse(e, formatter));
				}
				List<LocalDateTime> dateList = dateArray;
				CheckinMap.put(listC.get(i).getBusiness_id(), dateList);
			}

			// Main Loop
			for (int Counter = 0; Counter < old; Counter++) {
				System.out.println("Converting Businesses: " + old + "/" + Counter);
				// Gets the business that will be converted
				yelp_academic_dataset_business b = list.get(Counter);

				try {

					// Merges Coordinates
					Coordinates coordinates = new Coordinates(b.getLatitude(), b.getLongitude());
					// Merges Address
					BusinessAddress address = new BusinessAddress(b.getAddress(), b.getCity(), b.getState(),
							Integer.parseInt(b.getPostal_code()), coordinates);

					// Converts is_open from string to boolean
					boolean isOpenBool;
					if (b.getIs_open() == 1) {
						isOpenBool = true;
					} else {
						isOpenBool = false;
					}

					// Converts categories from single string to list
					String[] CategoriesString = b.getCategories().split(", ");
					List<String> categories = List.of(CategoriesString);

					// Declares or clears data storage needed for conversion of hours
					HashMap<Status, String> MondayMap = new HashMap<Status, String>();
					HashMap<Status, String> TuesdayMap = new HashMap<Status, String>();
					HashMap<Status, String> WednesdayMap = new HashMap<Status, String>();
					HashMap<Status, String> ThursdayMap = new HashMap<Status, String>();
					HashMap<Status, String> FridayMap = new HashMap<Status, String>();
					HashMap<Status, String> SaturdayMap = new HashMap<Status, String>();
					HashMap<Status, String> SundayMap = new HashMap<Status, String>();
					OpeningHours hours = new OpeningHours(MondayMap, TuesdayMap, WednesdayMap, ThursdayMap, FridayMap,
							SaturdayMap, SundayMap);

					// Checks for every day if data is present and converts
					// Tried to do this with LocalDateTime but MongoDB has no time only data type
					// Also 0:0-0:0 was causing problems, this solution should be sufficient
					if (b.getHours() != null) {
						if (b.getHours().getMonday() != null) {
							DayString = b.getHours().getMonday().split("-");
							MondayMap.put(Status.OPENS, DayString[0]);
							MondayMap.put(Status.CLOSES, DayString[1]);
							hours.setMonday(MondayMap);
						} else {
							hours.setMonday(null);
						}
						if (b.getHours().getTuesday() != null) {
							DayString = b.getHours().getTuesday().split("-");
							TuesdayMap.put(Status.OPENS, DayString[0]);
							TuesdayMap.put(Status.CLOSES, DayString[1]);
							hours.setTuesday(TuesdayMap);
						} else {
							hours.setTuesday(null);
						}
						if (b.getHours().getWednesday() != null) {
							DayString = b.getHours().getWednesday().split("-");
							WednesdayMap.put(Status.OPENS, DayString[0]);
							WednesdayMap.put(Status.CLOSES, DayString[1]);
							hours.setWednesday(WednesdayMap);
						} else {
							hours.setWednesday(null);
						}
						if (b.getHours().getThursday() != null) {
							DayString = b.getHours().getThursday().split("-");
							ThursdayMap.put(Status.OPENS, DayString[0]);
							ThursdayMap.put(Status.CLOSES, DayString[1]);
							hours.setThursday(ThursdayMap);
						} else {
							hours.setThursday(null);
						}
						if (b.getHours().getFriday() != null) {
							DayString = b.getHours().getFriday().split("-");
							FridayMap.put(Status.OPENS, DayString[0]);
							FridayMap.put(Status.CLOSES, DayString[1]);
							hours.setFriday(FridayMap);
						} else {
							hours.setFriday(null);
						}
						if (b.getHours().getSaturday() != null) {
							DayString = b.getHours().getSaturday().split("-");
							SaturdayMap.put(Status.OPENS, DayString[0]);
							SaturdayMap.put(Status.CLOSES, DayString[1]);
							hours.setSaturday(SaturdayMap);
						} else {
							hours.setSaturday(null);
						}
						if (b.getHours().getSunday() != null) {
							DayString = b.getHours().getSunday().split("-");
							SundayMap.put(Status.OPENS, DayString[0]);
							SundayMap.put(Status.CLOSES, DayString[1]);
							hours.setSunday(SundayMap);
						} else {
							hours.setSunday(null);
						}
					}

					// Checks if the converted business has bookings and inserts them if so
					// For some reason i had to put this in the ifelse,
					// because otherwise Checkins would´t be declared???
					if (CheckinMap.containsKey(b.getBusiness_id())) {
						List<LocalDateTime> Checkins = CheckinMap.get(b.getBusiness_id());
						PreWrite.add(new Business(b.getBusiness_id(), b.getName(), address, b.getStars(),
								b.getReview_count(), isOpenBool, attributes, categories, hours, Checkins));
					} else {
						List<LocalDateTime> Checkins = null;
						PreWrite.add(new Business(b.getBusiness_id(), b.getName(), address, b.getStars(),
								b.getReview_count(), isOpenBool, attributes, categories, hours, Checkins));
					}
					// Stores the database id´s not the business id´s of the cases causing an error
				} catch (Exception e) {
					IncompleteData.add(list.get(Counter).getId());
				}
			}
			System.out.println("Inserting...");
			// Inserts the restructured businesses into the database
			newRepo.insert(PreWrite);
			System.out.println("\n" + "Objects: " + old);
			System.out.println("Incomplete: " + IncompleteData.size() + "\n");
		};
	}