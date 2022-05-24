@Bean
	CommandLineRunner convertingTips(TipRepository newRepo, TipRepositoryOld oldRepo) {
		return args -> {

			// Sends a request to the DB which removes all existing business documents
			System.out.println("Preparing...");
			newRepo.deleteAll();

			// Puts all old tips into a List
			List<yelp_academic_dataset_tip> list = oldRepo.findAll();
			// List to store converted tips
			ArrayList<Tip> PreWrite = new ArrayList<Tip>();

			// Declares some data storages which will be used later
			ArrayList<String> IncompleteData = new ArrayList<String>();
			DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			// Initializing control variables
			int old = list.size();

			// Main Loop
			for (int Counter = 0; Counter < old; Counter++) {
				System.out.println("Converting Tips: " + old + "/" + Counter);
				// Gets the review that will be converted
				yelp_academic_dataset_tip t = list.get(Counter);

				try {
					// Converts the old review to the new format
					PreWrite.add(new Tip(t.getUser_id(), t.getBusiness_id(), t.getText(),
							LocalDateTime.parse(t.getDate(), Formatter), t.getCompliment_count()));

					// Stores the database id´s not the review id´s of the cases causing an error
				} catch (Exception e) {
					IncompleteData.add(list.get(Counter).getId());
				}
			}
			System.out.println("\n" + "Inserting...\n");
			newRepo.insert(PreWrite);
			System.out.println("\n" + "Objects: " + old);
			System.out.println("Incomplete: " + IncompleteData.size() + "\n");
		};
	}