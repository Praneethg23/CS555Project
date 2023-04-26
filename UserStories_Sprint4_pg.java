public String listMultipleBirths(ArrayList<Families> famList,ArrayList<Children> chList) {
		IndividualController iC=new IndividualController();
		Formatter fmt = new Formatter();
		fmt.format("+----------+--------------------------+--------------------------+-------------+-----------------------------------------\n");
	    fmt.format("|%10s|%26s|%26s|%13s|%15s\n", "FAMILY", "Father_Name","Mother_Name","BIRTH_DATES","CHILDREN_IDS");
	    fmt.format("+----------+--------------------------+--------------------------+-------------+-----------------------------------------\n");
		for(Families fam:famList) {
			ArrayList<Children> famChild=new ArrayList<Children>();
		
			for(Children ch:chList) {
				if(ch.getFamilyId().equals(fam.getFamiliyId())) {
					famChild.add(ch);
				}
			}
			Collections.sort(famChild,new ChildrenBirthComprator());
			boolean multiFlag=false;
			Date ref=new Date();
			ArrayList<String> multi= new ArrayList<String>();
			for (int i = 1; i < famChild.size(); i ++) {
				long difference_In_Time= iC.get(famChild.get(i).getChildId()).getBirthDate().getTime() - iC.get(famChild.get(i-1).getChildId()).getBirthDate().getTime();
				long difference_In_Days = (difference_In_Time/ (1000 * 60 * 60 * 24) )% 365;
				if(difference_In_Days<=1) {
					if(multiFlag==false) {
						ref=iC.get(famChild.get(i).getChildId()).getBirthDate();
						multiFlag=true;
						multi.add(famChild.get(i-1).getChildId());
					}
					multi.add(famChild.get(i).getChildId());
				}else {
					if(multiFlag) {
						multiFlag=false;
						fmt.format("|%10s|%26s|%26s|%13s|%50s\n", fam.getFamiliyId(), iC.get(fam.getHusbandId()).getNameNoNull(),iC.get(fam.getWifeId()).getNameNoNull(),ref.toString(),multi.toString());
						
						multi.clear();
						
					}
				}
				
			}
			if(multiFlag) {
				multiFlag=false;
				fmt.format("|%10s|%26s|%26s|%13s|%50s\n", fam.getFamiliyId(), iC.get(fam.getHusbandId()).getNameNoNull(),iC.get(fam.getWifeId()).getNameNoNull(),ref.toString().substring(0,10),multi.toString());
				
				multi.clear();
				
			}
			
		}
		fmt.format("+----------+--------------------------+--------------------------+-------------+-----------------------------------------\n");
		String res="User Story 32 :Multiple Births \n"+fmt.toString();
        iC.exit();
        fmt.close();
        return res;
	}
public String listRecentBirths(ArrayList<Individuals> indList) {
		Formatter fmt = new Formatter();
		fmt.format("+------------------+--------------------------+-------------+\n");
		fmt.format("|%18s|%26s|%13s|\n", "ID","Name","BIRTH_DATE");
		fmt.format("+------------------+--------------------------+-------------+\n");
		for(Individuals i : indList) {
			if(i.getBirthDate()!=null) {
				Date now=new Date();
				long diff_time = now.getTime()-i.getBirthDate().getTime();
				long diff_days = (diff_time/ (1000l * 60 * 60 * 24));
				if(diff_days<=30 && diff_days>=0) {
					fmt.format("|%18s|%26s|%13s|\n", i.getId(),i.getNameNoNull(),i.getBirthDateNoNull());
				}
			}
		}
		fmt.format("+------------------+--------------------------+-------------+\n");
		String res="User Story 35 :List of Recently Born Individuals \n"+fmt.toString();
	    fmt.close();
		return res;
		
	}
