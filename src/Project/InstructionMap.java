package Project;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class InstructionMap {
	public static Set<String> sourceCodes = new TreeSet<>();
	public static Map<String, Integer> opcode = new TreeMap<>();
	public static Map<Integer, String> mnemonics = new TreeMap<>();
	public static Set<String> noArgument = new TreeSet<>();
	public static Set<String> indirectOK= new TreeSet<>();

	static {
		sourceCodes.add("NOP");
		sourceCodes.add("LOD");
		sourceCodes.add("LODI");
		sourceCodes.add("STO");
		sourceCodes.add("ADD");
		sourceCodes.add("ADDI");
		sourceCodes.add("SUB");
		sourceCodes.add("SUBI");
		sourceCodes.add("MUL");
		sourceCodes.add("MULI");
		sourceCodes.add("DIV");
		sourceCodes.add("DIVI");
		sourceCodes.add("AND");
		sourceCodes.add("ANDI");
		sourceCodes.add("NOT");
		sourceCodes.add("CMPL");
		sourceCodes.add("CMPZ");
		sourceCodes.add("JUMP");
		sourceCodes.add("JUMPI");
		sourceCodes.add("JUMPA");
		sourceCodes.add("JMPZ");
		sourceCodes.add("JMPZI");
		sourceCodes.add("JMPZA");
		sourceCodes.add("HALT");
		//add the other source code names listed above (24 including NOP)

		indirectOK.add("LOD");
		indirectOK.add("STO");
		indirectOK.add("ADD");
		indirectOK.add("SUB");
		indirectOK.add("MUL");
		indirectOK.add("DIV");
		indirectOK.add("AND");
		indirectOK.add("CMPL");
		indirectOK.add("CMPZ");
		indirectOK.add("JUMP");
		indirectOK.add("JMPZ");

        //add the other source code names that allow indirect forms (11 including LOD)
		
		noArgument.add("HALT");
		noArgument.add("NOP");
		noArgument.add("NOT");
		//add the other source code names that do not take arguments
		//(3 including HALT)

		opcode.put("NOP", 0x0);
		opcode.put("LOD", 0x1);
		opcode.put("LODI", 0x1);
		opcode.put("STO", 0x2);
		opcode.put("ADD", 0x3);
		opcode.put("ADDI", 0x3);
		opcode.put("SUB", 0x4);
		opcode.put("SUBI", 0x4);
		opcode.put("MUL", 0x5);
		opcode.put("MULI", 0x5);
		opcode.put("DIV", 0x6);
		opcode.put("DIVI", 0x6);
		opcode.put("AND", 0x7);
		opcode.put("ANDI", 0x7);
		opcode.put("NOT", 0x8);
		opcode.put("CMPL", 0x9);
		opcode.put("CMPZ", 0xA);
		opcode.put("JUMP", 0xB);
		opcode.put("JUMPI", 0xB);
		opcode.put("JUMPA", 0xB);
		opcode.put("JMPZ", 0xC);
		opcode.put("JMPZI", 0xC);
		opcode.put("JMPZA", 0xC);
		opcode.put("HALT", 0xF);
		//add all the other instructions given in Project Part 1 and the mapping to their
		//opcode, which is the number of the instruction. Note ADDI maps to 0x3, the same
		//as ADD--similarly for the other instructions ending in I

//		for(String str : opcode.keySet()) {
//			mnemonics.put(opcode.get(str), str);
//		}
		mnemonics.put(0,"NOP");
		mnemonics.put(1,"LOD");
		mnemonics.put(2,"STO");
		mnemonics.put(3,"ADD");
		mnemonics.put(4,"SUB");
		mnemonics.put(5,"MUL");
		mnemonics.put(6,"DIV");
		mnemonics.put(7,"AND");
		mnemonics.put(8,"NOT");
		mnemonics.put(9,"CMPL");
		mnemonics.put(0xA,"CMPZ");
		mnemonics.put(0xB,"JUMP");
		mnemonics.put(0xC,"JMPZ");
		mnemonics.put(0xF,"HALT");
	}
}
