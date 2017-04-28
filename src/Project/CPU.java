package Project;

public class CPU {
	 private int accum;
	 private int pCounter;
	 private int memBase;
	public int getAccum() {
		return accum;
	}
	public int getpCounter() {
		return pCounter;
	}
	public int getMemBase() {
		return memBase;
	}
	public void setAccum(int accum) {
		this.accum = accum;
	}
	public void setpCounter(int pCounter) {
		this.pCounter = pCounter;
	}
	public void setMemBase(int memBase) {
		this.memBase = memBase;
	}
	 
	public void incrPC(){
		pCounter++;
	}
	 
}
