// Rishi Code
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