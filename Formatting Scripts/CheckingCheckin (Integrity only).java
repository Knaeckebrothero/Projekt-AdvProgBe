@Bean
	CommandLineRunner checkBookings(CheckinRepositoryOld oldRepo, BusinessRepositoryOld oldBrepo) {
		return args -> {

			List<yelp_academic_dataset_checkin> listC = oldRepo.findAll();
			List<yelp_academic_dataset_business> listB = oldBrepo.findAll();

			ArrayList CheckinIDs = new ArrayList<String>();
			ArrayList BusinessIDs = new ArrayList<String>();

			for (int i = 0; i < listC.size(); i++) {
				CheckinIDs.add(listC.get(i).getBusiness_id());
				System.out.println("filling " + i);
			}

			for (int i = 0; i < listB.size(); i++) {
				BusinessIDs.add(listB.get(i).getBusiness_id());
				System.out.println("filling " + i);
			}

			int old = listC.size();
			ArrayList IncompleteData = new ArrayList<String>();

			for (int i = 0; i < old; i++) {
				// try {

				if (BusinessIDs.contains(CheckinIDs.get(i))) {
					System.out.println(i + " of " + old);
				} else {
					IncompleteData.add(CheckinIDs.get(i));
				}
			}
			System.out.println("\n" + "Objects: " + old);
			System.out.println("Incompletet: " + IncompleteData.size());
		};
	}