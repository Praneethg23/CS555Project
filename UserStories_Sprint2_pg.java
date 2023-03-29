public class UserStories_Sprint2_pg{
  public boolean validSiblingSpacing(String famId) {
		ChildrenController cC=new ChildrenController();
		IndividualController iC=new IndividualController();
		ArrayList<Children> list=(ArrayList<Children>) cC.getAll();
		ArrayList<Children> famChild=new ArrayList<Children>();
		try {
			for(Children ch:list) {
				if(ch.getFamilyId().equals(famId)) {
					famChild.add(ch);
				}
			}
			Collections.sort(famChild,new ChildrenBirthComprator());
			for (int i = 1; i < famChild.size(); i ++) {
				long difference_In_Time= iC.get(famChild.get(i).getChildId()).getBirthDate().getTime() - iC.get(famChild.get(i-1).getChildId()).getBirthDate().getTime();
				long difference_In_Days = (difference_In_Time/ (1000 * 60 * 60 * 24) )% 365;
				if(difference_In_Days<240 && difference_In_Days>=2) {
					return false;
				}
			}
			return true;
		}finally {
			iC.exit();
			cC.exit();
		}
	}
public boolean validGender(String husbId,String wifeId) {
		if(husbId==null||wifeId==null) {
			return true;
		}
		IndividualController iC=new IndividualController();
		if(iC.get(wifeId).getSex()!='F'||iC.get(husbId).getSex()!='M') {
			iC.exit();
			return false;
		}
		iC.exit();
		return true;
		
	}
}
