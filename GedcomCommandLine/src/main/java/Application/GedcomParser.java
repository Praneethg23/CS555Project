package Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Controller.ChildrenController;
import Controller.FamilyController;
import Controller.IndividualController;
import Entities.Children;
import Entities.Families;
import Entities.Individuals;

public class GedcomParser {
	boolean individualReadingFlag=false,familyReadingFlag=false,dateFlag=false;
	String presentIndiviualId=null,presentFamilyId=null,presentDate=null;
	StringBuilder sb;
	public GedcomParser() {
		sb = new StringBuilder("");
	}
	public void case0(String array[]) {
		if(array.length>2) {
			if("INDI".equals(array[2])) {
				individualReadingFlag=familyReadingFlag=false;
				presentIndiviualId=presentFamilyId=null;
				IndividualController iC=new IndividualController();
				if(iC.check(array[1])) {
					sb.append("<-- "+array[0]+"|"+array[2]+"|Y|"+array[1]+"\n");
			        iC.create(new Individuals(array[1]));
			        iC.exit();
					presentIndiviualId=array[1];
			        individualReadingFlag=true;
				}
				else {
					sb.append("Duplicate Individual IDs  are not allowed\n");
				}
				return;
			}
			else if("FAM".equals(array[2])) {
				individualReadingFlag=familyReadingFlag=false;
				presentIndiviualId=presentFamilyId=null;
				FamilyController fC=new FamilyController();
				if(fC.check(array[1])) {
					sb.append("<-- "+array[0]+"|"+array[2]+"|Y|"+array[1]+"\n");
			        fC.create(new Families(array[1]));
			        fC.exit();
					presentFamilyId=array[1];
			        familyReadingFlag=true;
				}
				else {
					sb.append("Duplicate Individual IDs  are not allowed\n");
				}
				return;
			}
		}
			switch(array[1]) {
				case "NOTE":sb.append("<-- "+array[0]+"|"+array[1]+"|Y|"+array[2]+"\n");break;
				case "HEAD":sb.append("<-- "+array[0]+"|"+array[1]+"|Y|\n");break;
				case "TRLR":sb.append("<-- "+array[0]+"|"+array[1]+"|Y|\n");break;
				default:sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|N|\n"));break;
			}
	}
	public void case1(String array[]) {
		if(individualReadingFlag==true) {
			IndividualController iC=new IndividualController();
			Individuals i=iC.get(presentIndiviualId);
			boolean fl=true;
			switch(array[1]) {
			case "NAME":
				i.setName(array[2]);
				iC.update(i);
				break;
			case "SEX":
				i.setSex(array[2].charAt(0));
				iC.update(i);
				break;
			case "BIRT":
				dateFlag=true;
				presentDate="BIRT";
				break;
			case "DEAT":
				dateFlag=true;
				presentDate="DEAT";
				break;
			case "FAMC":
				i.setFamCId(array[2]);
				iC.update(i);
				break;
			case "FAMS":
				i.setFamSId(array[2]);
				iC.update(i);
				break;
			default: fl=false;sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|N|\n"));break;
			}
			if(fl) {
				sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|Y|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|Y|\n"));
			}
			iC.exit();
		}else if(familyReadingFlag==true) {
			FamilyController fC=new FamilyController();
			Families fam=fC.get(presentFamilyId);
			ChildrenController cC=new ChildrenController();
			boolean fl=true;
			switch(array[1]) {
			case "HUSB":
				fam.setHusbandId(array[2]);
				fC.update(fam);
				break;
			case "WIFE":
				fam.setWifeId(array[2]);
				fC.update(fam);
				break;
			case "CHIL":
				cC.create(new Children(presentFamilyId,array[2]));
				break;
			case "MARR":
				dateFlag=true;
				presentDate="MARR";
				break;
			case "DIV":
				dateFlag=true;
				presentDate="DIV";
				break;
			default: fl=false;sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|N|\n"));break;
			}
			if(fl) {
				sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|Y|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|Y|\n"));
			}
			fC.exit();
			cC.exit();
			
		}else {
			sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|N|\n"));
			
		}
		
	}
	public void case2(String array[]) {
		if(dateFlag) {
			SimpleDateFormat formatter=(array[2].length()==10)?(new SimpleDateFormat("MM dd yyyy")):(new SimpleDateFormat("dd MMM yyyy"));
			try {
				Date d=formatter.parse(array[2]);
				switch(presentDate) {
				case "BIRT":
					if(individualReadingFlag==true) {
						IndividualController iC=new IndividualController();
						Individuals i=iC.get(presentIndiviualId);
						i.setBirthDate(d);
						iC.update(i);
						iC.exit();
					}
					else {sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|N|\n"));}
					break;
				case "DEAT":
					if(individualReadingFlag==true) {
						IndividualController iC=new IndividualController();
						Individuals i=iC.get(presentIndiviualId);
						i.setDeathDate(d);
						iC.update(i);
						iC.exit();
					}
					else {sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|N|\n"));}
					break;
				case "MARR":
					if(familyReadingFlag==true) {
						FamilyController fC=new FamilyController();
						Families fam=fC.get(presentFamilyId);
						fam.setMarraigeDate(d);
						fC.update(fam);
						fC.exit();
					}
					else {System.out.println( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|N|\n"));}
					break;
				case "DIV":
					if(familyReadingFlag==true) {
						FamilyController fC=new FamilyController();
						Families fam=fC.get(presentFamilyId);
						fam.setDivorceDate(d);
						fC.update(fam);
						fC.exit();
					}
					else {sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]+"\n") : ("<-- "+array[0]+"|"+array[1]+"|N|"+"\n"));}
					break;
				default: sb.append( (array.length>2) ? ("<-- "+array[0]+"|"+array[1]+"|N|"+array[2]) : ("<-- "+array[0]+"|"+array[1]+"|N|\n"));break;
				}
			} catch (ParseException e) {
				sb.append("Invalid Date Format\n");
			}
			presentDate=null;
			dateFlag=false;
		}else {
			sb.append("Didnt Expected a date tag\n");
		}
		
	}
public String fileParser(String fileName){
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while(line!=null) {
				sb.append("--> "+line+"\n");
				String[] arr = line.split(" ", 3);
				if(arr.length<2) {
					sb.append("Invalid Line\n");
					continue;
				}
				switch(arr[0]) {
				
				case "0":case0(arr);break;
				case "1":case1(arr);break;
				case "2":case2(arr);break;
				default:break;
				}
				line = reader.readLine();
			}
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
