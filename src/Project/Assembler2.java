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
			if(inText.get(i).charAt(0)==' ' || inText.get(i).charAt(0)=='\t'){
				errors.add("Error: line " + i + " starts with white space");
			}
			//if(line.trim().toUpperCase()){
				
			//}
		}

		for(String line : inText){
			if(!line.startsWith("--")){
				code.add(line);}
			else{
				data.add(line);}
		}
		ArrayList<String>outtext=new ArrayList<>();

		for(String line:code){
			String[] parts = line.trim().split("\\s+");
			int lvl=0;
			if(parts.length==2){
				lvl=1;
				if(parts[1].startsWith("[")){
					lvl=2;
					parts[1]=parts[1].substring(1, parts[1].length()-1);}}
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
		outtext.add("-1");
		outtext.addAll(data);

		try (PrintWriter out = new PrintWriter(output)){
			for(String s : outtext) out.println(s);
		} catch (FileNotFoundException e) {
			errors.add("Cannot create output file");
		}

	}

}
