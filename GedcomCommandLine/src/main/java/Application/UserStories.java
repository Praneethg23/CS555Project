package Application;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;

import Comprators.ChildrenBirthComprator;
import Controller.ChildrenController;
import Controller.FamilyController;
import Controller.IndividualController;
import Entities.Children;
import Entities.Families;
import Entities.Individuals;

public class UserStories {
	public boolean isValidDate(Date d) {
		Date dt=new Date();
		return d.before(dt);
	}
	public boolean birthBeforeMarriage(Families f) {
		IndividualController iC=new IndividualController();
		try {
			if(iC.get(f.getHusbandId()).getBirthDate()!=null&&iC.get(f.getWifeId()).getBirthDate()!=null&&f.getMarraigeDate()!=null) {
				if(iC.get(f.getHusbandId()).getBirthDate().before(f.getMarraigeDate()) && iC.get(f.getWifeId()).getBirthDate().before(f.getMarraigeDate())) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return true;
			}
		}finally {
			iC.exit();

		}
	}
	public boolean birthBeforeDeath(Individuals i) {
		return i.getBirthDate().before(i.getDeathDate());
	}
	public boolean marraigeBeforeDivorce(Families f) {
		FamilyController fC=new FamilyController();
		boolean res=true;
		if(f.getDivorceDate()!=null && f.getMarraigeDate()!=null)
			res=f.getMarraigeDate().before(f.getDivorceDate());
		fC.exit();
		return res;
	}
	public boolean marraigeBeforeDeath(Families f) {
		IndividualController iC=new IndividualController();
		try {
			Date d1=iC.get(f.getHusbandId()).getDeathDate();
			Date d2=iC.get(f.getWifeId()).getDeathDate();
			if(d1!=null&&f.getMarraigeDate()!=null&&d1.before(f.getMarraigeDate())) {
				return false;
			}else if(d2!=null&&f.getMarraigeDate()!=null&&d2.before(f.getMarraigeDate())) {
				return false;
			}else {
				return true;
			}
		}finally {
			iC.exit();
		}
	}
	public boolean divorceBeforeDeath(Families f) {
		IndividualController iC=new IndividualController();
		try {
			if(f.getDivorceDate()!=null) {
				Date d1=iC.get(f.getHusbandId()).getDeathDate();
				Date d2=iC.get(f.getWifeId()).getDeathDate();
				if(d1!=null&&d1.before(f.getDivorceDate())) {
					System.out.println("d1");
					return false;
				}else if(d2!=null&&d2.before(f.getDivorceDate())) {
					System.out.println("d2");
					return false;
				}else {
					return true;
				}
			}
			else{
				return true;
			}
		}finally {
			iC.exit();
		}
	}
	public boolean checkAge150(Individuals i) {
		if(i.getBirthDate()!=null) {
			if(i.getDeathDate()!=null) {
				long diff= i.getDeathDate().getTime() - i.getBirthDate().getTime();
				if(((diff/(1000 * 60 * 60))% 24)>=150) {
					return false;
				}
			}
			else {
				long diff= new Date().getTime() - i.getBirthDate().getTime();
				if((diff/(1000l * 60 * 60 * 24 * 365))>=150) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean childBirthValid(String Id,String famId) {
		IndividualController iC=new IndividualController();
		FamilyController fC=new FamilyController();
		try {
			Date d1=iC.get(Id).getBirthDate();
			Date d2=fC.get(famId).getMarraigeDate();
			Date d3=fC.get(famId).getDivorceDate();
			if(d1!=null && d2!=null && d1.before(d2)) {
				return false;
			}
			if(d1!=null && d3!=null) {
				long diff=d1.getTime()-d3.getTime();
				if(((diff/(1000 * 60 * 60 * 24))% 365)>270) {
					return false;
				}
				
			}
		}
		finally {
			iC.exit();
			fC.exit();
		}
		return true;
		
	}
	public boolean childBirthValidDeath(String Id,String famId) {
		IndividualController iC=new IndividualController();
		FamilyController fC=new FamilyController();
		try {
			Date d1=iC.get(Id).getBirthDate();
			Date d2=iC.get(fC.get(famId).getHusbandId()).getDeathDate();
			Date d3=iC.get(fC.get(famId).getWifeId()).getDeathDate();
			if(d1!=null && d2!=null) {
				long diff=d2.getTime()-d1.getTime();
				if(((diff/(1000 * 60 * 60 * 24))% 365) < -270) {
					return false;
				}
			}
			if(d1!=null && d3!=null && d3.before(d1)) {
				return false;
			}
		}finally {
			iC.exit();
			fC.exit();
		}
		return true;
	}
	public boolean marriageAfter14(Families f) {
		IndividualController iC=new IndividualController();
		try {
			Date d1=f.getMarraigeDate();
			Date d2=iC.get(f.getHusbandId()).getBirthDate();
			Date d3=iC.get(f.getWifeId()).getBirthDate();
			if(d1!=null && d2!=null) {
				long diff=d1.getTime()-d2.getTime();
				if((diff/(1000l * 60 * 60 * 24 * 365))<14) {
					return false;
				}
			}
			if(d1!=null && d3!=null) {
				long diff=d1.getTime()-d3.getTime();
				if((diff/(1000l * 60 * 60 * 24 * 365))<14) {
					return false;
				}
			}
			return true;
		}finally {
			iC.exit();
		}
	}
	public boolean parentAgeValid(String childId,String famId) {
		IndividualController iC=new IndividualController();
		FamilyController fC=new FamilyController();
		try{
			Families fam=fC.get(famId);
			Date d1=iC.get(fam.getHusbandId()).getBirthDate();
			Date d2=iC.get(fam.getWifeId()).getBirthDate();
			Date d3=iC.get(childId).getBirthDate();
			if(d1!=null && d3!=null) {
				long diff=d3.getTime()-d1.getTime();
				if((diff/(1000l * 60 * 60 * 24 * 365))>=80) {
					return false;
				}
			}
			if(d2!=null && d3!=null) {
				long diff=d3.getTime()-d2.getTime();
				if((diff/(1000l * 60 * 60 * 24 * 365))>=60) {
					return false;
				}
			}
			return true;
		}finally {
			iC.exit();
			fC.exit();
		}
	}
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
			System.out.print(famChild);
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
	public boolean uniqueIndividuals(HashSet<String> set,String name,String date) {
		if(set.contains(name+date)) {
			return false;
		}
		set.add(name+date);
		return true;
	}
	public boolean uniqueFamily(HashSet<String> set,String husbName,String wifeName,String date) {
		if(set.contains(husbName+wifeName+date)) {
			return false;
		}
		set.add(husbName+wifeName+date);
		return true;
	}
	public boolean uniqueChildren(String famId) {
		ChildrenController cC=new ChildrenController();
		ArrayList<Children> list=(ArrayList<Children>) cC.getAll();
		HashSet<String> set = new HashSet<String>();
		IndividualController iC = new IndividualController();
		cC.exit();
		for(Children ch:list) {
			if(ch.getFamilyId().equals(famId)) {
				Individuals i = iC.get(ch.getChildId());
				if(set.contains(i.getNameNoNull()+i.getBirthDateNoNull())) {
					iC.exit();
					return false;
				}
				set.add(i.getNameNoNull()+i.getBirthDateNoNull());
			}
		}
		iC.exit();
		return true;
		
	}
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
	public String listDeceased(ArrayList<Individuals>  list) {
		Formatter fmt = new Formatter();
		fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+---------------+---------------+\n");
	    fmt.format("|%18s|%26s|%4s|%13s|%13s|%5s|%15s|%15s|\n", "ID", "Name","SEX","BIRTH DATE","DEATH DATE","AGE","FAMILY_SPOUSE","FAMILY_CHILD");
	    fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+---------------+---------------+\n");
		for(Individuals i:list) {
			if(i.getDeathDate()!=null) {
				fmt.format("|%18s|%26s|%4s|%13s|%13s|%5s|%15s|%15s|\n",i.getId(),i.getNameNoNull(),i.getSexNoNull(),i.getBirthDateNoNull(),i.getDeathDateNoNull(),calculateAge(i.getBirthDate(),i.getDeathDate()),i.getFamSIdNoNull(),i.getFamCIdNoNull());
			}
		}
		fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+---------------+---------------+\n");
		
        String res="User Story 29:Deceased Individuals \n"+fmt.toString();
        fmt.close();
        return res;
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
	
	
}
