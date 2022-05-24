@Bean
	CommandLineRunner formattingBusiness(BusinessRepository newRepo, BusinessRepositoryOld oldRepo) {
		return args -> {
			
			List<yelp_academic_dataset_business> list = oldRepo.findAll();
			int old = list.size();
			HashMap<String, String> attributes = new HashMap<String, String>();
			ArrayList IncompleteData = new ArrayList<String>();
			
			for (int i = 0; i < old; i++) {
				try {
				yelp_academic_dataset_business b = list.get(i);
				
				System.out.println(b.getBusiness_id());
				
				Coordinates coordinates = new Coordinates(b.getLatitude(), b.getLongitude());
				BusinessAddress address = new BusinessAddress(b.getAddress(), b.getCity(), b.getState(),
						Integer.parseInt(b.getPostal_code()), coordinates);
				
				boolean isOpenBool = false;
				if (b.getIs_open() == 1) {
					isOpenBool = true;
				} else {
					isOpenBool = false;
				}
				
				String[] CategoriesString = b.getCategories().split(", ");
				List<String> categories = List.of(CategoriesString);
				
				String[] DayString;
				HashMap MondayMap = new HashMap<String, LocalTime>();
				HashMap TuesdayMap = new HashMap<String, LocalTime>();
				HashMap WednesdayMap = new HashMap<String, LocalTime>();
				HashMap ThursdayMap = new HashMap<String, LocalTime>();
				HashMap FridayMap = new HashMap<String, LocalTime>();
				HashMap SaturdayMap = new HashMap<String, LocalTime>();
				HashMap SundayMap = new HashMap<String, LocalTime>();
				DateFormat formatter = new SimpleDateFormat("HH:mm");
				java.sql.Time SQLTime;
				

				if (b.getHours() != null) {
					if (b.getHours().getMonday() != null && b.getHours().getMonday() != "0:0-0:0") {
						DayString = b.getHours().getMonday().split("-");
						SQLTime = new java.sql.Time(formatter.parse(DayString[0]).getTime());
						MondayMap.put("Opens", SQLTime.toLocalTime());
						SQLTime = new java.sql.Time(formatter.parse(DayString[1]).getTime());
						MondayMap.put("Closes", SQLTime.toLocalTime());
						
					}

					if (b.getHours().getTuesday() != null && b.getHours().getTuesday() != "0:0-0:0") {
						DayString = b.getHours().getTuesday().split("-");
						SQLTime = new java.sql.Time(formatter.parse(DayString[0]).getTime());
						TuesdayMap.put("Opens", SQLTime.toLocalTime());
						SQLTime = new java.sql.Time(formatter.parse(DayString[1]).getTime());
						TuesdayMap.put("Closes", SQLTime.toLocalTime());
					}

					if (b.getHours().getWednesday() != null && b.getHours().getWednesday() != "0:0-0:0") {
						DayString = b.getHours().getWednesday().split("-");
						SQLTime = new java.sql.Time(formatter.parse(DayString[0]).getTime());
						WednesdayMap.put("Opens", SQLTime.toLocalTime());
						SQLTime = new java.sql.Time(formatter.parse(DayString[1]).getTime());
						WednesdayMap.put("Closes", SQLTime.toLocalTime());
					}

					if (b.getHours().getThursday() != null && b.getHours().getThursday() != "0:0-0:0") {
						DayString = b.getHours().getThursday().split("-");
						SQLTime = new java.sql.Time(formatter.parse(DayString[0]).getTime());
						ThursdayMap.put("Opens", SQLTime.toLocalTime());
						SQLTime = new java.sql.Time(formatter.parse(DayString[1]).getTime());
						ThursdayMap.put("Closes", SQLTime.toLocalTime());
					}

					if (b.getHours().getFriday() != null && b.getHours().getFriday() != "0:0-0:0") {
						DayString = b.getHours().getFriday().split("-");
						SQLTime = new java.sql.Time(formatter.parse(DayString[0]).getTime());
						FridayMap.put("Opens", SQLTime.toLocalTime());
						SQLTime = new java.sql.Time(formatter.parse(DayString[1]).getTime());
						FridayMap.put("Closes", SQLTime.toLocalTime());
					}

					if (b.getHours().getSaturday() != null && b.getHours().getSaturday() != "0:0-0:0") {
						DayString = b.getHours().getSaturday().split("-");
						SQLTime = new java.sql.Time(formatter.parse(DayString[0]).getTime());
						SaturdayMap.put("Opens", SQLTime.toLocalTime());
						SQLTime = new java.sql.Time(formatter.parse(DayString[1]).getTime());
						SaturdayMap.put("Closes", SQLTime.toLocalTime());
					}

					if (b.getHours().getSunday() != null && b.getHours().getSunday() != "0:0-0:0") {
						DayString = b.getHours().getSunday().split("-");
						SQLTime = new java.sql.Time(formatter.parse(DayString[0]).getTime());
						SundayMap.put("Opens", SQLTime.toLocalTime());
						SQLTime = new java.sql.Time(formatter.parse(DayString[0]).getTime());
						SundayMap.put("Closes", SQLTime.toLocalTime());
					}
				}

				OpeningHours hours = new OpeningHours(MondayMap, TuesdayMap, WednesdayMap, ThursdayMap, FridayMap,
						SaturdayMap, SundayMap);
				
				Business business = new Business(b.getBusiness_id(), b.getName(), address, b.getStars(),
						b.getReview_count(), isOpenBool, attributes, categories, hours);
				newRepo.insert(business);

				} catch (Exception e) {IncompleteData.add(list.get(i).getId());}
				}
			System.out.println("\n" + "Objects: " + old);
			System.out.println("Incompletet: " + IncompleteData.size());
		};
	}