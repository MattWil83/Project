package Project;

import javax.swing.JFrame;

public class GUIMediator {
	private MachineModel model;
	
	
	public void step(){}
	
	public MachineModel getModel() {
		return model;
	}
	public void setModel(MachineModel model) {
		this.model = model;
	}
	JFrame getFrame(){
		return null;
	}
	void clearJobs(){
		
	}
	void makeReady(String s){
		
	}
	
}
