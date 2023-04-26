// This method checks if there are any duplicate children in a given family
public boolean uniqueChildren(String famId) {
		ChildrenController cC=new ChildrenController();
		ArrayList<Children> list=(ArrayList<Children>) cC.getAll();
        // Use a HashSet to keep track of unique children
		HashSet<String> set = new HashSet<String>();
		IndividualController iC = new IndividualController();
		cC.exit();
		for(Children ch:list) {
			if(ch.getFamilyId().equals(famId)) {
                // Get the individual associated with the child
				Individuals i = iC.get(ch.getChildId())
				if(set.contains(i.getNameNoNull()+i.getBirthDateNoNull())) {
					iC.exit();
					return false;
				}
				set.add(i.getNameNoNull()+i.getBirthDateNoNull());
			}
		}
		iC.exit();
		return true;
		
	}

// This method generates a formatted list of deceased individuals
public String listDeceased(ArrayList<Individuals>  list) {
		Formatter fmt = new Formatter();
		fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+---------------+---------------+\n");
	    fmt.format("|%18s|%26s|%4s|%13s|%13s|%5s|%15s|%15s|\n", "ID", "Name","SEX","BIRTH DATE","DEATH DATE","AGE","FAMILY_SPOUSE","FAMILY_CHILD");
	    fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+---------------+---------------+\n");
        // Iterate through the list of individuals
		for(Individuals i:list) {
			if(i.getDeathDate()!=null) {
                // Format the row for the individual
				fmt.format("|%18s|%26s|%4s|%13s|%13s|%5s|%15s|%15s|\n",i.getId(),i.getNameNoNull(),i.getSexNoNull(),i.getBirthDateNoNull(),i.getDeathDateNoNull(),calculateAge(i.getBirthDate(),i.getDeathDate()),i.getFamSIdNoNull(),i.getFamCIdNoNull());
			}
		}
		fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+---------------+---------------+\n");
		// Combine the formatted string and return it
        String res="User Story 29:Deceased Individuals \n"+fmt.toString();
        fmt.close();
        return res;
	}


     function pageInit(context) {
    var currentRecord = context.currentRecord;

    if (currentRecord.type === 'email') {

      // Check if the email field is empty
      if (!currentRecord.getValue('email')) {

        // Set the default email value
        currentRecord.setValue('email', 'rishi@example.com');

      }
    }
  }
var currentDate = new Date();
    var dateFormat = 'MM/DD/YYYY';
    var formattedCurrentDate = format.format({
      value: currentDate,
      type: format.Type.DATE,
      timezone: format.Timezone.AMERICA_NEW_YORK,
      format: dateFormat
    });
  var parsedStartDate = format.parse({
        value: startDate,
        type: format.Type.DATE,
        timezone: format.Timezone.AMERICA_NEW_YORK
      });

      var parsedEndDate = format.parse({
        value: endDate,
        type: format.Type.DATE,
        timezone: format.Timezone.AMERICA_NEW_YORK
      });
  if (parsedStartDate <= currentDate || parsedEndDate <= parsedStartDate) {

       

      } else {

       

      }