public String listOrphans(ArrayList<Individuals> indList) {
		ChildrenController cC = new ChildrenController();
		IndividualController iC = new IndividualController();
		FamilyController fC = new FamilyController();
		Formatter fmt = new Formatter();
		fmt.format("+------------------+----------+--------------------------+-------------+\n");
	    fmt.format("|%18s|%10s|%26s|%13s|\n", "Individual Id", "Family_Id", "Name", "BIRTH_DATES");
	    fmt.format("+------------------+----------+--------------------------+-------------+\n");
	    for(Individuals i: indList) {
	    	Children ch = cC.get(i.getId());
	    	if(ch==null) {
	    		continue;
	    	}
	    	String famId = ch.getFamilyId();
	    	Individuals child = iC.get(ch.getChildId());
	    	if(famId!=null && fC.get(famId)!=null) {
		    	Individuals husb = iC.get(fC.get(famId).getHusbandId());
		    	Individuals wife = iC.get(fC.get(famId).getWifeId());
		    	if(husb!=null && husb.getDeathDate()!=null && wife!=null && wife.getDeathDate()!=null && child.getDeathDate()==null && Long.parseLong(calculateAge(child.getBirthDate(),child.getDeathDate()))<18 ) {
		    		fmt.format("|%18s|%10s|%26s|%13s|\n",i.getId(),famId,i.getNameNoNull(),i.getBirthDateNoNull());
		    	}
	    	}
	    }
	    fmt.format("+------------------+----------+--------------------------+-------------+\n");
	    String res="User Story 33 :List Orphans \n"+fmt.toString();
	    iC.exit();
	    fC.exit();
	    cC.exit();
	    fmt.close();
		return res;
	}
	public String listCoupleDoubleAges(ArrayList<Families> famList) {
		IndividualController iC = new IndividualController();
		Formatter fmt = new Formatter();
		fmt.format("+----------+--------------------------+-----+--------------------------+-----+\n");
	    fmt.format("|%10s|%26s|%5s|%26s|%5s|\n", "FAMILY", "Husband_Name","AGE","Wife_Name","AGE");
	    fmt.format("+----------+--------------------------+-----+--------------------------+-----+\n");
		for(Families fam : famList) {
			Individuals husb = iC.get(fam.getHusbandId());
			Individuals wife = iC.get(fam.getWifeId());
			if(husb!=null && wife!=null) {
				long age1 = Long.parseLong(calculateAge(husb.getBirthDate(),fam.getMarraigeDate()));
				long age2 = Long.parseLong(calculateAge(wife.getBirthDate(),fam.getMarraigeDate()));
				if(age1>=age2*2 || age2>=age1*2) {
					fmt.format("|%10s|%26s|%5s|%26s|%5s|\n", fam.getFamiliyId(),husb.getNameNoNull(),age1,wife.getNameNoNull(),age2);
				}
			}
		}
		fmt.format("+----------+--------------------------+-----+--------------------------+-----+\n");
		String res="User Story 34 :List of Couples with Double Age Gap \n"+fmt.toString();
	    iC.exit();
	    fmt.close();
		return res;
	}