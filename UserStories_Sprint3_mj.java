public boolean uniqueIndividuals(HashSet<String> set,String name,String date) {
		if(set.contains(name+date)) {
			return false;
		}
		set.add(name+date);
		return true;
	}

public String listMarried(ArrayList<Families>  list) {
		IndividualController ic=new IndividualController();
		Formatter fmt = new Formatter();
		fmt.format("+---------------+------------------+--------------------------+------------------+--------------------------+\n");
	    fmt.format("|%15s|%18s|%26s|%18s|%26s|\n", "FAMILY ID", "HUSBAND_ID","HUSBAND NAME","WIFE_ID","WIFE NAME");
	    fmt.format("+---------------+------------------+--------------------------+------------------+--------------------------+\n");
		for(Families f:list) {
			Individuals husb=ic.get(f.getHusbandId());
			Individuals wife=ic.get(f.getWifeId());
			if(husb!=null && wife!=null) {
				if(husb.getDeathDate()==null && wife.getDeathDate()==null) {
					fmt.format("|%15s|%18s|%26s|%18s|%26s|\n",f.getFamiliyId(),husb.getId(),husb.getNameNoNull(),wife.getId(),wife.getNameNoNull());
				}
				else if(husb.getDeathDate()!=null && wife.getDeathDate()==null) {
					fmt.format("|%15s|%18s|%26s|%18s|%26s|\n",f.getFamiliyId(),"-","Deceased",wife.getId(),wife.getNameNoNull());
				
				}
				else if(husb.getDeathDate()==null && wife.getDeathDate()!=null) {
					fmt.format("|%15s|%18s|%26s|%18s|%26s|\n",f.getFamiliyId(),husb.getId(),husb.getNameNoNull(),"-","Deceased");
				}
				
			}
			else if(husb!=null && wife==null && husb.getDeathDate()==null) {
				fmt.format("|%15s|%18s|%26s|%18s|%26s|\n",f.getFamiliyId(),husb.getId(),husb.getNameNoNull(),"N/A","N/A");
			}
			else if(husb==null && wife!=null && wife.getDeathDate()==null) {
				fmt.format("|%15s|%18s|%26s|%18s|%26s|\n",f.getFamiliyId(),"N/A","N/A",wife.getId(),wife.getNameNoNull());
			}
		}
		fmt.format("+---------------+------------------+--------------------------+------------------+--------------------------+\n");
		
        String res="User Story 30: Married Individuals Alive \n"+fmt.toString();
        ic.exit();
        fmt.close();
        return res;
	}