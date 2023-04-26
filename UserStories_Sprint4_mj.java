public String  listRecentDeaths(ArrayList<Individuals> indList) {
		Formatter fmt = new Formatter();
		fmt.format("+------------------+--------------------------+-------------+\n");
		fmt.format("|%18s|%26s|%13s|\n", "ID","Name","DEATH_DATE");
		fmt.format("+------------------+--------------------------+-------------+\n");
		for(Individuals i : indList) {
			if(i.getDeathDate()!=null) {
				Date now=new Date();
				long diff_time = now.getTime()-i.getDeathDate().getTime();
				long diff_days = (diff_time/ (1000 * 60 * 60 * 24) );
				if(diff_days<=30 && diff_days>=0) {
					fmt.format("|%18s|%26s|%13s|\n", i.getId(),i.getNameNoNull(),i.getDeathDateNoNull());
				}
			}
		}
		fmt.format("+------------------+--------------------------+-------------+\n");
		String res="User Story 36 :List of Recently Dead Individuals \n"+fmt.toString();
	    fmt.close();
		return res;
	}
	public String listSurvivors(ArrayList<Individuals> indList,ArrayList<Children> chilist) {
		FamilyController fC = new FamilyController();
		IndividualController iC = new IndividualController();
		Formatter fmt = new Formatter();
		fmt.format("+------------------+--------------------------+-------------+------------------+--------------------------+----------+\n");
		fmt.format("|%18s|%26s|%13s|%18s|%26s|%10s|\n", "ID","Name","DEATH_DATE","SURVIOUR_ID","SURVIVOR_NAME","RELATION");
		fmt.format("+------------------+--------------------------+-------------+------------------+--------------------------+----------+\n");
		for(Individuals i : indList) {
			if(i.getDeathDate()!=null) {
				Individuals sp;
				ArrayList<String> chiNames =new ArrayList<String>();
				Date now=new Date();
				long diff_time = now.getTime()-i.getDeathDate().getTime();
				long diff_days = (diff_time/ (1000 * 60 * 60 * 24) );
				if(diff_days<=30 && diff_days>=0) {
					if(i.getFamSId()!=null) {
						Families famS = fC.get(i.getFamSId());
						if(famS.getHusbandId().equals(i.getId())) {
							sp=iC.get(famS.getWifeId());
						}else {
							sp = iC.get(famS.getHusbandId());
						}
						if(sp!=null && sp.getDeathDate()==null) {
							fmt.format("|%18s|%26s|%13s|%18s|%26s|%10s|\n", i.getId(),i.getNameNoNull(),i.getDeathDateNoNull(),sp.getId(),sp.getNameNoNull(),"SPOUSE");
						}
						for(Children ch: chilist) {
							if(ch.getFamilyId().equals(famS.getFamiliyId())) {
								Individuals c = iC.get(ch.getChildId());
								if(c!=null && c.getDeathDate()==null) {
									fmt.format("|%18s|%26s|%13s|%18s|%26s|%10s|\n", i.getId(),i.getNameNoNull(),i.getDeathDateNoNull(),c.getId(),c.getNameNoNull(),"CHILD");
								}
							}
						}
					}
				}
			}
		}
		fmt.format("+------------------+--------------------------+-------------+------------------+--------------------------+----------+\n");
		String res="User Story 37 :List of Survivors \n"+fmt.toString();
		fC.exit();
		iC.exit();
	    fmt.close();
		return res;
	}