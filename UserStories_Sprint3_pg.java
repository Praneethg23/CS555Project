public boolean checkCorrespondingEntries(Individuals i) {
		ChildrenController cC=new ChildrenController();
		FamilyController fC=new FamilyController();
		try {
			if(i.getFamCId()!=null) {
				Children c = cC.get(i.getId());
				if(c==null) {
					return false;
				}
				if(!c.getFamilyId().equals(i.getFamCId())) {
					return false;
				}
			}
			if(i.getFamSId()!=null) {
				Families f=fC.get(i.getFamSId());
				if(f==null) {
					return false;
				}
			}
			return true;
		}finally {
			cC.exit();
			fC.exit();
		}
		
}
//where ever a user is being displayed suing this function one can include the age.
public String calculateAge(Date d1,Date d2) {
		if(d1==null)
			return "N/A";
		else if(d2!=null){
			long diff_time = d2.getTime()-d1.getTime();
			long difference_In_Years= (diff_time/ (1000l * 60 * 60 * 24 * 365));
			return ""+difference_In_Years;
		}
		else {
			Date now=new Date();
			long diff_time = now.getTime()-d1.getTime();
			long difference_In_Years= (diff_time/ (1000l * 60 * 60 * 24 * 365));
			return ""+difference_In_Years;
		}
}
