package Comprators;

import java.util.Comparator;

import Controller.IndividualController;
import Entities.Children;
import Entities.Individuals;

public class ChildrenBirthComprator implements Comparator{
	public int compare(Object o1,Object o2){  
		Children ch1=(Children)o1;
		Children ch2=(Children)o2;
		IndividualController iC = new IndividualController();
		Individuals i1=iC.get(ch1.getChildId());
		Individuals i2=iC.get(ch2.getChildId());
		if(i1.getBirthDate().compareTo(i2.getBirthDate())>0) {
			iC.exit();
			return 1;
		}
		else if(i1.getBirthDate().compareTo(i2.getBirthDate())<0) {
			iC.exit();
			return -1;
		}
		else {
			iC.exit();
			return 0;
		}
		
	}

	
	
}
