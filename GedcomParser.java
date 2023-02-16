import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Formatter; 
public class GedcomParser {
	
public void fileParser(String fileName){
		
		BufferedReader reader;
		HashSet<String> set1 = new HashSet<String>(Arrays.asList("NAME","SEX","FAMC","FAMS","HUSB","WIFE","CHIL"));
		HashSet<String> set2 =new HashSet<String>(Arrays.asList("BIRT","DEAT","MARR","DIV"));
		HashSet<String> set01=new HashSet<String>(Arrays.asList("NOTE"));
		HashSet<String> set02=new HashSet<String>(Arrays.asList("HEAD","TRLR"));
		Map<String,String> individuals= new HashMap<String,String>(5000);
		Map<String,List<String>> families=new HashMap<String,List<String>>(1000);
		boolean indiFlag=false,famFlag=false;
		String presentInd=null,presentFam=null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				System.out.println("--> "+line);
				String[] arr = line.split(" ", 3);
				if(arr.length<2) {
					System.out.println("Invalid Line");
					continue;
				}
				if(indiFlag==true){
				    if("1".equals(arr[0]) && "NAME".equals(arr[1])){
		                System.out.println("<-- "+arr[0]+"|"+arr[1]+"|Y|"+arr[2]);
		                individuals.put(presentInd,arr[2]);
		                indiFlag=false;
		            }else{
		                System.out.println("Error:Expected a line with name tag entry");
		            }
		            line = reader.readLine();
		            continue;
		        }if(famFlag==true && ("HUSB".equals(arr[1]) || "WIFE".equals(arr[1]))){
		            if("1".equals(arr[0]) && "HUSB".equals(arr[1])){
		                System.out.println("<-- "+arr[0]+"|"+arr[1]+"|Y|"+arr[2]);
		                families.get(presentFam).add(0,arr[2]);
		            }
		            else if("1".equals(arr[0]) && "WIFE".equals(arr[1])){
		                System.out.println("<-- "+arr[0]+"|"+arr[1]+"|Y|"+arr[2]);
		                families.get(presentFam).add(1,arr[2]);
		            }
		            line = reader.readLine();
		            continue;
		        }
				if("0".equals(arr[0])&&(arr.length>2)&&("INDI".equals(arr[2])||"FAM".equals(arr[2]))) {
				    indiFlag=famFlag=false;
				    if(("INDI".equals(arr[2])) && !individuals.containsKey(arr[1])){
				        System.out.println("<-- "+arr[0]+"|"+arr[2]+"|Y|"+arr[1]);
				        individuals.put(arr[1],null);
				        presentInd=arr[1];
				        indiFlag=true;
				    }else if(("FAM".equals(arr[2])) && !families.containsKey(arr[1])){
					    System.out.println("<-- "+arr[0]+"|"+arr[2]+"|Y|"+arr[1]);
					    families.put(arr[1],new ArrayList<String>(2));
					    families.get(arr[1]).add(0,null);
					    families.get(arr[1]).add(1,null);
					    presentFam=arr[1];
					    famFlag=true;
				    }
				    else{
				        System.out.println("Duplicate ID's are not allowed");
				    }
				}else if("1".equals(arr[0])) {
					if(set1.contains(arr[1])) {
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|Y|"+arr[2]);
					}
					else if(set2.contains(arr[1])) {
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|Y|");
					}
					else if(arr.length>2){
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|N|"+arr[2]);
					}
					else {
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|N|");
					}
				}else if("0".equals(arr[0])){
					if(set01.contains(arr[1])) {
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|Y|"+arr[2]);
					}
					else if(set02.contains(arr[1])) {
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|Y|");
					}
					else if(arr.length>2){
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|N|"+arr[2]);
					}
					else {
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|N|");
					}
					
				}else if("2".equals(arr[0])) {
					if("DATE".equals(arr[1])) {
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|Y|"+arr[2]);
					}
					else if(arr.length>2){
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|N|"+arr[2]);
					}
					else {
						System.out.println("<-- "+arr[0]+"|"+arr[1]+"|N|");
					}
				}else {
					System.out.println("<-- "+arr[0]+"|"+arr[1]+"|N|"+arr[2]);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		    Formatter fmt = new Formatter();
		    fmt.format("+--------------------+--------------------------+\n");
		    fmt.format("|%20s| %25s|\n", "ID", "Name");  
		    fmt.format("+--------------------+--------------------------+\n");
		    individuals.forEach((key, value) -> {
		         fmt.format("|%20s| %25s|\n",key,value);
            });
            fmt.format("+--------------------+--------------------------+\n");
            System.out.println("\n");
            System.out.println(fmt);
            Formatter fmt1 = new Formatter();
            fmt1.format("+----------+---------------------+--------------------------+---------------------+--------------------------+\n");
            fmt1.format("|%10s| %20s| %25s| %20s| %25s|\n","Family_ID","Husband_ID","Husband_Name","Wife_ID","Wife_Name");
            fmt1.format("+----------+---------------------+--------------------------+---------------------+--------------------------+\n");
		    families.forEach((key, value) -> {
		         fmt1.format("|%10s| %20s| %25s| %20s| %25s|\n",key,value.get(0),individuals.get(value.get(0)),value.get(1),individuals.get(value.get(1)));
            });
            fmt1.format("+----------+---------------------+--------------------------+---------------------+--------------------------+\n");
            System.out.println(fmt1);
		}
	}
}