public boolean uniqueFamily(HashSet<String> set,String husbName,String wifeName,String date) {
		if(set.contains(husbName+wifeName+date)) {
			return false;
		}
		set.add(husbName+wifeName+date);
		return true;
	}
public String listUnMarried(ArrayList<Families> list) {
		HashSet<String> set = new HashSet<String>();
		IndividualController iC=new IndividualController();
		ArrayList<Individuals>  inds=(ArrayList<Individuals>) iC.getAll();
		for(Families f:list) {
			if(f.getHusbandId()!=null)
				set.add(f.getHusbandId());
			if(f.getWifeId()!=null)
				set.add(f.getWifeId());
		}
		Formatter fmt = new Formatter();
		fmt.format("+------------------+--------------------------+----+-------------+-----+\n");
	    fmt.format("|%18s|%26s|%4s|%13s|%5s|\n", "ID", "Name","SEX","BIRTH DATE","AGE");
	    fmt.format("+------------------+--------------------------+----+-------------+-----+\n");
		for(Individuals i:inds) {
			if(!set.contains(i.getId())&&i.getDeathDate()==null) {
				fmt.format("|%18s|%25s|%4s|%13s|%5s|\n",i.getId(),i.getNameNoNull(),i.getSexNoNull(),i.getBirthDateNoNull(),calculateAge(i.getBirthDate(),i.getDeathDate()));
			}
		}
		fmt.format("+------------------+--------------------------+----+-------------+-----+\n");
        String res="User Story 31 :UnMarried Living Individuals\n"+fmt.toString();
        iC.exit();
        fmt.close();
        return res;
	}
