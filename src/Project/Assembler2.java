package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler2 {
	public static void assemble(File input, File output, ArrayList<String> errors){
		ArrayList<String> code=new ArrayList<>();
		ArrayList<String> data=new ArrayList<>();
		ArrayList<String> inText=new ArrayList<>();
		String temp;
		try (Scanner inp = new Scanner(input)) {
			temp = inp.nextLine();
			while(inp.hasNextLine()){
				inText.add(temp);
				temp = inp.nextLine();}
		} catch (FileNotFoundException e) { 
			errors.add(0, "Error: Unable to open the input file"); 
			return;}

		for(int i=0; i<inText.size(); i++){
			if(inText.get(i).trim().length()==0 && i+1<inText.size() &&
					inText.get(i+1).trim().length() > 0){
				errors.add("Error: line " + i + " is a blank line");
			}
			if(inText.get(i).length()>1 && (inText.get(i).charAt(0)==' ' || 
					inText.get(i).charAt(0)=='\t')){
				errors.add("Error: line " + i + " starts with white space");
			}
			if(inText.get(i).trim().toUpperCase().startsWith("--") 
					&& !(inText.get(i).trim().replace("-","").length()==0)){
				errors.add("Error: line " + i + " has a duplicate data separator");
			}
		}

		for(String line : inText){
			boolean dataSection = false;
			if(!dataSection){
				code.add(line.trim());
				if(line.startsWith("--")) dataSection=true;}
			else{
				data.add(line.trim());}
		}
		
		ArrayList<String>outtext=new ArrayList<>();
		
		//CODE SECTION
		for(int i=0; i<code.size(); i++){ 
			String[] parts = code.get(i).trim().split("\\s+");
			int lineNum = i; //they want us to "get the lineNum from inText... I'd hope that's the same as the index of code...
			if(InstructionMap.sourceCodes.contains(parts[0].toUpperCase())
					&& !InstructionMap.sourceCodes.contains(parts[0])){
				errors.add("Error: line " + lineNum + " does not have the instruction mnemonic in upper case");
			} else if(InstructionMap.noArgument.contains(parts[0]) && !(parts.length==1)){
				errors.add("Error: line " + lineNum + " has an illegal argument");
			} else if(!InstructionMap.noArgument.contains(parts[0])){
				if(parts.length==1){
					errors.add("Error: line " + lineNum + " is missing an argument");
				} else if(parts.length>=3){
					errors.add("Error: line " + lineNum + " has more than one argument");
				} else if(parts.length==2){
					if(parts[1].startsWith("[")){
						if(!(InstructionMap.indirectOK.contains(parts[0]))){
							errors.add("Error: line " + lineNum + " has an illegal argument for given instruction");
						} else if(!parts[1].endsWith("]")){
							errors.add("Error: line " + lineNum + " is missing a closing bracket");
							parts[1]=parts[1].substring(1, parts[1].length());
						}
					}
				}
			}
			int arg; 
			try {
				arg = Integer.parseInt(parts[1],16);
			} catch (NumberFormatException e) {
				errors.add("Error: line " + lineNum 
						+ " does not have a numeric argument");
			} 
			//------------PUT OTHER ERRORS HERE (IF ANY)-----------
			int lvl=0;
			if(parts.length==2){
				lvl=1;
				if(parts[1].startsWith("[")){
					lvl=2;
					parts[1]=parts[1].substring(1, parts[1].length()-1);}}
			//hope this^^ is alright - not entirely sure if they want us
			// to account for when there's only one "[]" instead of both...
			if(parts[0].endsWith("I")){
				lvl=0;}
			else if(parts[0].endsWith("A"))
				lvl=3;

			int opcode = InstructionMap.opcode.get(parts[0]);
			if(parts.length==1)
				outtext.add(Integer.toHexString(opcode).toUpperCase() + " 1 0");
			if(parts.length==2)
				outtext.add(Integer.toHexString(opcode).toUpperCase() + " " + lvl + " " + parts[1]);

		}

		//DATA SECTION
		outtext.add("-1");

		for(int i=0; i<data.size(); i++){
			int lineNum = i+code.size()+1;
			String[] parts = data.get(i).trim().split("\\s+");
			if(parts.length!=2){
				errors.add("Error: line " + i + " has the wrong number of arguments");
			}
			int arg = 0; 
			try {
				arg = Integer.parseInt(parts[0],16);
			} catch (NumberFormatException e) {
				errors.add("Error: line " + lineNum 
						+ " does not have a numeric argument");
			}
			try {
				arg = Integer.parseInt(parts[1],16);
			} catch (NumberFormatException e) {
				errors.add("Error: line " + lineNum 
						+ " does not have a numeric argument");
			}
			outtext.add(data.get(i));
		}

		if(errors.size() == 0){
			try (PrintWriter out = new PrintWriter(output)){
				for(String s : outtext) out.println(s);
			} catch (FileNotFoundException e) {
				errors.add("Cannot create output file");
			}
		}

	}

}
