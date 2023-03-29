public class UserStories_Sprint2_pg{
public boolean invalidMultipleBirths(String famId) {
		ChildrenController cC=new ChildrenController();
		IndividualController iC=new IndividualController();
		ArrayList<Children> list=(ArrayList<Children>) cC.getAll();
		ArrayList<Children> famChild=new ArrayList<Children>();
		int count=0;
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
				if(difference_In_Days<=1) {
					if(count==0)
						count++;
					count++;
					if(count>5)
						return false;
				}else {
					count=0;
				}
			}
			return true;
		}finally {
			iC.exit();
			cC.exit();
		}
	}
	public boolean checkValidSiblingCount(String famId) {
		ChildrenController cC=new ChildrenController();
		ArrayList<Children> list=(ArrayList<Children>) cC.getAll();
		int count=0;
		cC.exit();
		for(Children ch:list) {
			if(ch.getFamilyId().equals(famId)) {
				count++;
			}
		}
		if(count<15) {
			return true;
		}else {
			return false;
		}
		
	}
}
