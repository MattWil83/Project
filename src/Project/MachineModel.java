package Project;

import java.util.Map;
import java.util.TreeMap;

public class MachineModel {

	public final Map<Integer, Instruction> IMAP=new TreeMap<>();
	private CPU cpu=new CPU();
	private Memory memory=new Memory();
	private HaltCallback callback;


	public MachineModel(HaltCallback callback) {
		this.callback = callback;

		Instruction ADD=IMAP.put(0x3, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in ADD instruction");
			}
			if(level > 0) {
				IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} else {
				cpu.setAccum(cpu.getAccum() + arg);
				cpu.incrPC();
			}
		});
		Instruction SUB=IMAP.put(0x4, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in ADD instruction");
			}
			if(level > 0) {
				IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} else {
				cpu.setAccum(cpu.getAccum() - arg);
				cpu.incrPC();
			}
		});
		Instruction MUL=IMAP.put(0x5, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in ADD instruction");
			}
			if(level > 0) {
				IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} else {
				cpu.setAccum(cpu.getAccum() * arg);
				cpu.incrPC();
			}
		});
		Instruction DIV=IMAP.put(0x6, (arg, level) ->{
			if(level < 0 || level > 2) {
				throw new IllegalArgumentException(
						"Illegal indirection level in ADD instruction");
			}
			if(level > 0) {
				IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);
			} 
			if(arg==0)
				throw new DivideByZeroException("Cannot Divide By Zero");
			else {
				cpu.setAccum(cpu.getAccum() / arg);
				cpu.incrPC();
			}
		});


	}



	public MachineModel() {
		this(() -> System.exit(0));

	}






}
