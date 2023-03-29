public class UserStories_Sprint2_jv{
public boolean checkFirstCousinMarraige(String husbID,String wifeID) {
		if(husbID==null||wifeID==null) {
			return false;
		}
		IndividualController iC=new IndividualController();
		FamilyController fC=new FamilyController();
		try{
			String husbFam1,husbFam2,wifeFam1,wifeFam2;
			if(iC.get(husbID).getFamCId()!=null && iC.get(wifeID).getFamCId()!=null) {
				husbFam1=iC.get(fC.get(iC.get(husbID).getFamCId()).getHusbandId()).getFamCId();
				husbFam2=iC.get(fC.get(iC.get(husbID).getFamCId()).getWifeId()).getFamCId();
				wifeFam1=iC.get(fC.get(iC.get(wifeID).getFamCId()).getHusbandId()).getFamCId();
				wifeFam2=iC.get(fC.get(iC.get(wifeID).getFamCId()).getWifeId()).getFamCId();
				if((husbFam1!=null && wifeFam1!=null && husbFam1.equals(wifeFam1))||(husbFam1!=null && wifeFam2!=null && husbFam1.equals(wifeFam2))) {
					return true;
				}
				if((husbFam2!=null && wifeFam1!=null && husbFam2.equals(wifeFam1))||(husbFam2!=null && wifeFam2!=null && husbFam2.equals(wifeFam2))) {
					return true;
				}
			}
			return false;
		}finally {
			iC.exit();
			fC.exit();
		}
		
		
	}
	public boolean AuntUncleMarraige(String husbId,String wifeId) {
		if(husbId==null||wifeId==null) {
			return true;
		}
		IndividualController iC=new IndividualController();
		ChildrenController cC=new ChildrenController();
		ArrayList<Children> list=(ArrayList<Children>) cC.getAll();
		String fam1Cid=iC.get(husbId).getFamCId();
		String fam2Cid=iC.get(wifeId).getFamCId();
		if(fam1Cid!=null) {
			for(Children ch:list) {
				if(fam1Cid.equals(ch.getFamilyId())&&!husbId.equals(ch.getChildId())) {
					String fmid1=iC.get(ch.getChildId()).getFamSId();
					if(fmid1!=null&&fam2Cid!=null&&fmid1.equals(fam2Cid)) {
						return false;
					}
				}
			}
		}
		if(fam2Cid!=null) {
			for(Children ch:list) {
				if(fam2Cid.equals(ch.getFamilyId())&&!wifeId.equals(ch.getChildId())) {
					String fmid1=iC.get(ch.getChildId()).getFamSId();
					if(fmid1!=null&&fam1Cid!=null&&fmid1.equals(fam1Cid)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
  }
