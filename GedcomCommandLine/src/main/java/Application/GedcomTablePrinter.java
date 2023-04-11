package Application;

import Controller.ChildrenController;
import Controller.FamilyController;
import Controller.IndividualController;
import Entities.Families;
import Entities.Individuals;
import Entities.Children;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

public class GedcomTablePrinter {
	HashMap<String,List<String>> children;
	public GedcomTablePrinter() {
		children = new HashMap<String,List<String>>();
		bringChildren();
	}
	public String printIndividuals(){
		UserStories us=new UserStories();
		IndividualController ic=new IndividualController();
		ArrayList<Individuals>  list=(ArrayList<Individuals>) ic.getAll();
		Formatter fmt = new Formatter();
		fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+-----+---------------+---------------+\n");
	    fmt.format("|%18s|%26s|%4s|%13s|%13s|%5s|%5s|%15s|%15s|\n", "ID", "Name","SEX","BIRTH DATE","DEATH DATE","ALIVE","AGE","FAMILY_SPOUSE","FAMILY_CHILD");
	    fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+-----+---------------+---------------+\n");
		for(Individuals i:list) {
			 fmt.format("|%18s|%26s|%4s|%13s|%13s|%5s|%5s|%15s|%15s|\n",i.getId(),i.getNameNoNull(),i.getSexNoNull(),i.getBirthDateNoNull(),i.getDeathDateNoNull(),(i.getDeathDate()==null),us.calculateAge(i.getBirthDate(), i.getDeathDate()),i.getFamSIdNoNull(),i.getFamCIdNoNull());
			 
		}
		fmt.format("+------------------+--------------------------+----+-------------+-------------+-----+-----+---------------+---------------+\n");
		
        String res=fmt.toString();
        ic.exit();
        fmt.close();
        return res;
	}
	public String printFamilies() {
		FamilyController fC=new FamilyController();
		IndividualController iC=new IndividualController();
		ArrayList<Families> fams=(ArrayList<Families>) fC.getAll();
		Formatter fmt = new Formatter();
		fmt.format("+----------+--------------------------+--------------------------+-------------+-------------+-----------------------------------------+\n");
		fmt.format("|%10s|%26s|%26s|%13s|%13s|%40s|\n","Family_ID","Husband Name","Wife Name","Marriage Date","Divorce Date","Children");
		fmt.format("+----------+--------------------------+--------------------------+-------------+-------------+-----------------------------------------+\n");
		for(Families f:fams) {
			Individuals husb=iC.get(f.getHusbandId());
			Individuals wife=iC.get(f.getWifeId());
			if(children.containsKey(f.getFamiliyId()))
				fmt.format("|%10s|%26s|%26s|%13s|%13s|%40s|\n",f.getFamiliyId(),husb.getNameNoNull(),wife.getNameNoNull(),f.getMarraigeDateNoNull(),f.getDivorceDateNoNull(),children.get(f.getFamiliyId()).toString());
			else{
				fmt.format("|%10s|%26s|%26s|%13s|%13s|%40s|\n",f.getFamiliyId(),husb.getNameNoNull(),wife.getNameNoNull(),f.getMarraigeDateNoNull(),f.getDivorceDateNoNull(),"NA");
			}
		}
		fmt.format("+----------+--------------------------+--------------------------+-------------+-------------+-----------------------------------------+\n");
		System.out.println();
        String res=fmt.toString();
        iC.exit();
        fC.exit();
        fmt.close();
        return res;
        
	}
	private void bringChildren() {
		ChildrenController cC = new ChildrenController();
		List<Children> list=cC.getAll();
		for(Children ch:list){
			if(children.containsKey(ch.getFamilyId())) {
				children.get(ch.getFamilyId()).add(ch.getChildId());
			}
			else {
				children.put(ch.getFamilyId(), new ArrayList<String>());
				children.get(ch.getFamilyId()).add(ch.getChildId());
			}
		}
		cC.exit();
	}
}
