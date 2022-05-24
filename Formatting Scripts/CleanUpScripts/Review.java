@Bean
	CommandLineRunner convertingReview(ReviewRepository newRepo, ReviewRepositoryOld oldRepo) {
		return args -> {

			// Sends a request to the DB which removes all existing business documents
			System.out.println("Preparing, this could take a long time...");
			newRepo.deleteAll();

			// Puts all old reviews into a List
			List<yelp_academic_dataset_review> list = oldRepo.findAll();
			// List to store converted reviews
			ArrayList<Review> PreWrite = new ArrayList<Review>();

			// Declares some data storages which will be used later
			ArrayList<String> IncompleteData = new ArrayList<String>();
			DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			// Initializing control variables
			int old = list.size();
			int InsertCounter = 500000;

			// Main Loop
			for (int Counter = 0; Counter < old; Counter++) {
				System.out.println("Converting Reviews: " + old + "/" + Counter);
				// Gets the review that will be converted
				yelp_academic_dataset_review r = list.get(Counter);

				try {
					// Converts the old review to the new format
					PreWrite.add(new Review(r.getReview_id(), r.getUser_id(), r.getBusiness_id(), r.getStars(),
							new Tags(r.getUseful(), r.getFunny(), r.getCool()), r.getText(),
							LocalDateTime.parse(r.getDate(), Formatter)));

					// This will trigger when 500k reviews are converted
					// It is necessary cause the DB cannot handle a array of 7m
					if (InsertCounter < Counter) {
						System.out.println("\n" + "Inserting...\n");
						// Inserts the restructured review into the database
						newRepo.insert(PreWrite);
						PreWrite = new ArrayList<Review>();
						InsertCounter += 500000;
					}
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