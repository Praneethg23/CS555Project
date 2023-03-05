package Application;

import java.io.FileWriter;
import java.io.IOException;

public class Application {

	public static void main(String[] args) {
		if (args.length > 0){
		    GedcomParser parser = new GedcomParser();
		    String st1=parser.fileParser(args[0]);
		    GedcomTablePrinter gTP=new GedcomTablePrinter();
		    Validator v=new Validator();
		    String st2=gTP.printIndividuals();
		    String st3=gTP.printFamilies();
		    String st4=v.validate();
		    System.out.println(st1+"\nPeople\n"+st2+"\nFamilies\n"+st3+"\nErrors\n"+st4);
		    try {
		        FileWriter myWriter = new FileWriter("output.txt");
		        myWriter.write(st1+"\nPeople\n"+st2+"\nFamilies\n"+st3+"\nErrors\n"+st4);
		        myWriter.close();
		      } catch (IOException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }
	    }
	    else{
	        System.out.println("Error:No file given");
	    }
	}

}
