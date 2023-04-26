public String listUpcomingBirthDays(ArrayList<Individuals> indList) {
		Formatter fmt = new Formatter();
		fmt.format("+------------------+--------------------------+-------------+\n");
		fmt.format("|%18s|%26s|%13s|\n", "ID","Name","BIRTH_DATE");
		fmt.format("+------------------+--------------------------+-------------+\n");
		for(Individuals i : indList) {
			if(i.getBirthDate()!=null && i.getDeathDate()==null) {
				Date now=new Date();
				long diff_time = now.getTime()-i.getBirthDate().getTime();
				long diff_days = (diff_time/ (1000 * 60 * 60 * 24) )% 365;
				long remain = diff_days%365;
				if(remain>=335) {
					fmt.format("|%18s|%26s|%13s|\n", i.getId(),i.getNameNoNull(),i.getBirthDateNoNull());
				}
			}
		}
		fmt.format("+------------------+--------------------------+-------------+\n");
		String res="User Story 38 :List of Upcoming Birthdays\n"+fmt.toString();
	    fmt.close();
		return res;
	}
	public String listUpcomingAnniversaries(ArrayList<Families> famList) {
		Formatter fmt = new Formatter();
		IndividualController iC = new IndividualController();
		fmt.format("+----------+--------------------------+--------------------------+-------------+\n");
		fmt.format("|%10s|%26s|%26s|%13s|\n", "Family_ID","Husband Name","Wife Name","Marraige_Date");
		fmt.format("+----------+--------------------------+--------------------------+-------------+\n");
		for(Families fam : famList) {
			if(fam.getMarraigeDate()!=null && fam.getDivorceDate()==null) {
				Individuals husb = iC.get(fam.getHusbandId());
				Individuals wife = iC.get(fam.getWifeId());
				if(wife!=null && husb!=null && wife.getDeathDate()==null && husb.getDeathDate()==null) {
					Date now=new Date();
					long diff_time = now.getTime()-fam.getMarraigeDate().getTime();
					long diff_days = (diff_time/ (1000 * 60 * 60 * 24) )% 365;
					if(diff_days>335) {
						fmt.format("|%10s|%26s|%26s|%13s|\n", fam.getFamiliyId(),husb.getNameNoNull(),wife.getNameNoNull(),fam.getMarraigeDateNoNull());
					}
				}
			}
		}
		fmt.format("+----------+--------------------------+--------------------------+-------------+\n");
		String res="User Story 39 :List of Upcoming Anniversaries \n"+fmt.toString();
		iC.exit();
	    fmt.close();
		return res;
		
	}
