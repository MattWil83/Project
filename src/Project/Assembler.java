package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler {
	public static void assemble(File input, File output, ArrayList<String> errors){
		ArrayList<String> code=new ArrayList<>();
		ArrayList<String> data=new ArrayList<>();
		try (Scanner inp = new Scanner(input)) {
			while(inp.hasNextLine()){
				while(inp.nextLine()!="--"){
					code.add(inp.nextLine());}
				data.add(inp.nextLine());}
		} catch (FileNotFoundException e) { 
			errors.add(0, "Error: Unable to open the input file"); 
			return;}
		ArrayList<String>temp=new ArrayList<>();
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


	}
}
