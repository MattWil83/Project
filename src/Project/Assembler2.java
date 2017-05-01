package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
			inText.add(temp); //hope this line doesn't screw anything up...
		} catch (FileNotFoundException e) { 
			errors.add(0, "Error: Unable to open the input file"); 
			return;}

		//to remove blank lines at the end of a file:
		int ix = inText.size()-1;
		while(ix>=0 && inText.get(ix).trim().length()==0){
			inText.remove(ix);
			ix--;
		}
		
		int idx=0;
		while(idx<inText.size()){
			if(inText.get(idx).trim().length()==0){
				errors.add("Error on line " + (idx+1) + ". Illegal blank line in the source file");
				while(idx+1<inText.size() && inText.get(idx+1).trim().length()==0){
					idx++;
				}
			}
			if(inText.get(idx).length()>1 && (inText.get(idx).charAt(0)==' ' || 
					inText.get(idx).charAt(0)=='\t')){
				errors.add("Error on line " + (idx+1) + ". Line starts with illegal white space");
			}
			if(inText.get(idx).trim().toUpperCase().startsWith("--") 
					&& !(inText.get(idx).trim().replace("-","").length()==0)){
				errors.add("Error on line " + (idx+1) + ". dashed line has non-dashes");
			}
			idx++;
		}

		boolean dataSection = false;

		for(String line : inText){
			if(!dataSection){
				if(line.startsWith("--")){
					dataSection=true;}
				else{
					code.add(line.trim());}}
			else{
				data.add(line.trim());}
		}

		ArrayList<String>outtext=new ArrayList<>();

		//CODE SECTION
		for(int i=0; i<code.size(); i++){ 
			String[] parts = code.get(i).trim().split("\\s+");
			int lineNum = i+1; //they want us to "get the lineNum from inText... I'd hope that's the same as the index of code...
			if(!InstructionMap.sourceCodes.contains(parts[0].toUpperCase()) && parts[0].length()>0){
				errors.add("Error on line " + lineNum + ": illegal mnemonic");
			} else if(InstructionMap.sourceCodes.contains(parts[0].toUpperCase())){
				if(InstructionMap.sourceCodes.contains(parts[0].toUpperCase())
						&& !InstructionMap.sourceCodes.contains(parts[0])){
					errors.add("Error on line " + lineNum + ": mnemonic must be upper case");
				} else if(InstructionMap.noArgument.contains(parts[0]) && !(parts.length==1)){
					errors.add("Error on line " + lineNum + ": this mnemonic cannot take arguments");
				} else if(!InstructionMap.noArgument.contains(parts[0])){
					if(parts.length==1){
						errors.add("Error on line " + lineNum + ": this mnemonic is missing an argument");
					} else if(parts.length>=3){
						errors.add("Error on line " + lineNum + ": this mnemonic has too many arguments");
					} else if(parts.length==2){
						if(parts[1].startsWith("[")){
							if(!(InstructionMap.indirectOK.contains(parts[0]))){
								errors.add("Error on line " + lineNum + ": has an illegal argument for given instruction");
							} else if(!parts[1].endsWith("]")){
								errors.add("Error on line " + lineNum + ": this argument is missing closing \"]\"");
							}
						}
					}
				}
			}
			if(parts.length>0){
				//get level
				int lvl=0;
				if(parts.length==2){
					lvl=1;
					if(parts[1].startsWith("[")){
						lvl=2;
						parts[1]=parts[1].substring(1, parts[1].length()-1);}
					}
				if(parts[0].endsWith("I")){
					lvl=0;}
				else if(parts[0].endsWith("A")){
					lvl=3;}
				//------------PUT OTHER ERRORS HERE (IF ANY)-----------
				try {
					if(parts.length>1){
						int arg = Integer.parseInt(parts[1],16);}
				} catch (NumberFormatException e) {
					errors.add("Error on line " + lineNum + ": argument is not a hex number");
				} 
				//System.out.println(Arrays.toString(parts));
				if(InstructionMap.sourceCodes.contains(parts[0])){
					int opcode = InstructionMap.opcode.get(parts[0]);
					if(parts.length==1)
						outtext.add(Integer.toHexString(opcode).toUpperCase() + " 1 0");
					if(parts.length==2)
						outtext.add(Integer.toHexString(opcode).toUpperCase() + " " + lvl + " " + parts[1]);
				}
			}
		}

		//DATA SECTION
		outtext.add("-1");

		for(int i=0; i<data.size(); i++){
			int lineNum = i+code.size()+2;
			String[] parts = data.get(i).trim().split("\\s+");
			if(parts.length!=2 && data.get(i).trim().length()>0){
				errors.add("Error on line " + lineNum + ": data format does not consist of two numbers");
			} 
			try {
				if(parts.length>1){
					int arg = Integer.parseInt(parts[0],16);}
			} catch (NumberFormatException e) {
				errors.add("Error on line " + lineNum + ": data address is not a hex number");
			}
			try {
				if(parts.length>1){
					int arg = Integer.parseInt(parts[1],16);}
			} catch (NumberFormatException e) {
				errors.add("Error on line " + lineNum + ": data value is not a hex number");
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
