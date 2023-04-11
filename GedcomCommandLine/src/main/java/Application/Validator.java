package Application;

import java.util.ArrayList;
import java.util.HashSet;


import Controller.ChildrenController;
import Controller.FamilyController;
import Controller.IndividualController;
import Entities.Children;
import Entities.Families;
import Entities.Individuals;

public class Validator {
	StringBuilder sb;
	UserStories us;
	public Validator() {
		sb = new StringBuilder("");
		us = new UserStories();
	}
	public String validate() {
		validateIndividuals();
		validateFamilies();
		validateChildren();
		return sb.toString();
	}
	public void validateIndividuals() {
		IndividualController iC = new IndividualController();
		ArrayList<Individuals>  list=(ArrayList<Individuals>) iC.getAll();
		HashSet<String> indlist =new HashSet<String>();
		for(Individuals i:list) {
			if(i.getBirthDate()!=null && !us.isValidDate(i.getBirthDate())) {
				sb.append("Error:User Story 1:Individual: "+i.getId()+" Birthday is in future "+i.getBirthDate()+"\n");
			}
			if(i.getDeathDate()!=null && !us.isValidDate(i.getDeathDate())) {
				sb.append("Error:User Story 1:Individual: "+i.getId()+" Death Date given in future "+i.getDeathDate()+"\n");
			}
			if(i.getBirthDate()!=null && i.getDeathDate()!=null && !us.birthBeforeDeath(i)) {
				sb.append("Error:User Story 3:Individual: "+i.getId()+"Death Before Birth\n");
			}
			if(!us.checkAge150(i)) {
				sb.append("Error:User Story 7:Individual : "+i.getId()+"Age greater than 150\n");
			}
			if(!us.uniqueIndividuals(indlist, i.getNameNoNull(), i.getBirthDateNoNull())) {
				sb.append("Error:User Story 23:Individual : "+i.getId()+" Already other indivudal with same name and date of birth exist\n");
			}
			if(!us.checkCorrespondingEntries(i)) {
				sb.append("Error : User Story 26:Indiivdual :"+i.getId()+" is missing corresponding entries or having invalid entries in families description\n");
			}
		}
		iC.exit();
		
	}
	public void validateFamilies() {
		FamilyController fC = new FamilyController();
		ArrayList<Families> fams=(ArrayList<Families>) fC.getAll();
		HashSet<String> famlist =new HashSet<String>();
		IndividualController iC=new IndividualController();
		for(Families fam:fams) {
			if(fam.getMarraigeDate()!=null && !us.isValidDate(fam.getMarraigeDate())) {
				sb.append("Error:User Story 1: Family : "+fam.getFamiliyId()+" Marriage Date given in future\n");
			}
			if(fam.getDivorceDate()!=null && !us.isValidDate(fam.getDivorceDate())) {
				sb.append("Error:User Story 1: Family : "+fam.getFamiliyId()+" Divorce Date given in future\n");
			}
			if(!us.birthBeforeMarriage(fam)) {
				sb.append("Error : User Story 2 : Family :"+fam.getFamiliyId()+" Marriage Should be after thebirth of husband and wife\n");
			}
			if(!us.marraigeBeforeDivorce(fam)) {
				sb.append("Error : User Story 4 : Family :"+fam.getFamiliyId()+" Marriage Should be before the divorce\n");
			}
			if(!us.marraigeBeforeDeath(fam)) {
				sb.append("Error : User Story 5 : Family :"+fam.getFamiliyId()+" Marriage Should be before the death\n");
			}
			if(!us.divorceBeforeDeath(fam)) {
				sb.append("Error : User Story 6 : Family :"+fam.getFamiliyId()+" Divorce Should be before the death\n");
			}
			if(!us.marriageAfter14(fam)) {
				sb.append("Error : User Story 10 : Family :"+fam.getFamiliyId()+" Individuals should be atleast 14 years old at the time of their marriage\n");
			}
			if(!us.validSiblingSpacing(fam.getFamiliyId())) {
				sb.append("Anamoly: User Story 13 : Family :"+fam.getFamiliyId()+" There are invalid birth spacing for children\n");
				
			}
			if(!us.invalidMultipleBirths(fam.getFamiliyId())) {
				sb.append("Anamoly: User Story 14 : Family :"+fam.getFamiliyId()+" More than 5 births at same time\n");
			}
			if(!us.checkValidSiblingCount(fam.getFamiliyId())) {
				sb.append("Anamoly: User Story 15: Family :"+fam.getFamiliyId()+" More than 15 children in the family\n");
			}
			if(!us.checkLastNames(fam.getFamiliyId(),fam.getHusbandId())) {
				sb.append("Error: User Story 16: Family :"+fam.getFamiliyId()+" All male last names are not same\n");
			}
			if(us.checkSiblingMarraige(fam.getHusbandId(), fam.getWifeId())) {
				sb.append("Anamoly: User Story 18: Family :"+fam.getFamiliyId()+" Siblings marraige is not allowed\n");
			}
			if(us.checkFirstCousinMarraige(fam.getHusbandId(), fam.getWifeId())) {
				sb.append("Anamoly: User Story 19: Family :"+fam.getFamiliyId()+" First Cousins cant marry\n");
			}
			if(!us.AuntUncleMarraige(fam.getHusbandId(), fam.getWifeId())) {
				sb.append("Anamoly: User Story 20: Family :"+fam.getFamiliyId()+" Aunt-nephew or Uncle-Neice Marraige are not allowed\n");
			}
			if(!us.validGender(fam.getHusbandId(), fam.getWifeId())) {
				sb.append("Error: User Story 21: Family :"+fam.getFamiliyId()+" Genders of the HUsband and wife are marked wrong\n");
			}
			Individuals husb=iC.get(fam.getHusbandId());
			Individuals wife=iC.get(fam.getWifeId());
			if(!us.uniqueFamily(famlist, husb.getNameNoNull(),wife.getNameNoNull(),fam.getMarraigeDateNoNull())) {
				sb.append("Error: User Strory 24: Family :"+fam.getFamiliyId()+" Already another family exist with same spouse details\n");
			}
			if(!us.uniqueChildren(fam.getFamiliyId())) {
				sb.append("Error: User Story 25: Family : "+fam.getFamiliyId()+" It have duplicate child enteries\n");
			}
			
		}
		iC.exit();
	}
	public void validateChildren() {
		ChildrenController cC=new ChildrenController();
		ArrayList<Children> list=(ArrayList<Children>) cC.getAll();
		for(Children ch:list) {
			if(!us.childBirthValid(ch.getChildId(),ch.getFamilyId() )) {
				sb.append("Anamoly :User Story 8: Child Id : "+ch.getChildId()+"of Family : "+ch.getFamilyId()+" Birth of child Before Marraige\n");
			}
			if(!us.childBirthValidDeath(ch.getChildId(), ch.getFamilyId())) {
				sb.append("Anamoly :User Story 9: Child Id : "+ch.getChildId()+"of Family : "+ch.getFamilyId()+" Birth of child After death of parents\n");
			}
			if(!us.parentAgeValid(ch.getChildId(),ch.getFamilyId())) {
				sb.append("Anamoly :User Story 12: Child Id:"+ch.getChildId()+"of Family : "+ch.getFamilyId()+" Parents were too old at the child's birth\n");
				
			}
			
		}
		IndividualController iC = new IndividualController();
		FamilyController fC = new FamilyController();
		ArrayList<Individuals> list1=(ArrayList<Individuals>) iC.getAll();
		ArrayList<Families> list2=(ArrayList<Families>) fC.getAll();
		iC.exit();
		fC.exit();
		cC.exit();
		sb.append(us.listDeceased(list1));
		sb.append(us.listMarried(list2));
		sb.append(us.listUnMarried(list2));
	}

}
