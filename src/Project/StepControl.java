package Project;

import javax.swing.Timer;

public class StepControl {
	private static final int TICK = 500;
	private boolean autoStepOn = false;
	private Timer timer;
	private GUIMediator gui;
	
	public StepControl (GUIMediator gui){
		this.gui=gui;
	}

	public boolean isAutoStepOn() {
		return autoStepOn;
	}

	public Timer getTimer() {
		return timer;
	}

	public GUIMediator getGui() {
		return gui;
	}

	public void setAutoStepOn(boolean autoStepOn) {
		this.autoStepOn = autoStepOn;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void setGui(GUIMediator gui) {
		this.gui = gui;
	}
	
	public void toggleAutoStep(){
		if(autoStepOn==false){
			this.autoStepOn=true;}
		else{
			this.autoStepOn=false;}
	}
	
	public void setPeriod(int period){
		timer.setDelay(period);
	}
	
	public void start() {
		timer = new Timer(TICK, e -> {if(autoStepOn) gui.step();});
		timer.start();
	}
	
	
	
	
}




