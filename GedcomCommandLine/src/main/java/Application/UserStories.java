package Application;

import java.util.Date;
import Controller.FamilyController;
import Controller.IndividualController;
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
}
