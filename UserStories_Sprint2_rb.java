// Rishi Code
// A method to check if the last names of all male children in a family are the same as that of their father.
// Parameters:
// - famId: a String representing the ID of the family to check
// - husbID: a String representing the ID of the father of the family to check
// Returns:
// - a boolean value representing whether or not all male children in the family have the same last name as their father

public boolean checkLastNames(String famId,String husbID) {
		if(husbID==null) {
			return false;
		}
		ChildrenController cC=new ChildrenController();
		IndividualController iC=new IndividualController();
		ArrayList<Children> list=(ArrayList<Children>) cC.getAll();
		System.out.print(husbID);
		System.out.println(iC.get(husbID)==null);
		if(iC.get(husbID)==null) {
			return true;
		}
		String lastName=iC.get(husbID).getName().split("/",3)[1].trim();
		cC.exit();
		for(Children ch:list) {
			if(ch.getFamilyId().equals(famId) && iC.get(ch.getChildId()).getSex()=='M') {
				if(!(lastName.equals(iC.get(ch.getChildId()).getName().split("/",3)[1].trim()))){
					iC.exit();
					return false;
				}
			}
		}
		cC.exit();
		return true;
	}


// A method to check if a husband and wife are married to each other as siblings.
// Parameters:
// - husbID: a String representing the ID of the husband to check
// - wifeId: a String representing the ID of the wife to check
// Returns:
// - a boolean value representing whether or not the husband and wife are married to each other as siblings

	public boolean checkSiblingMarraige(String husbID,String wifeId) {
		if(husbID==null||wifeId==null) {
			return false;
		}
		ChildrenController cC=new ChildrenController();
		try {
			if(cC.get(wifeId)!=null&&cC.get(husbID)!=null&& cC.get(wifeId).getFamilyId().equals(cC.get(husbID).getFamilyId())) {
				return true;
			}else {
				return false;
			}
		}finally {
			cC.exit();
		}
	}