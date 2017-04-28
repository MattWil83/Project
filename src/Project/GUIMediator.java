package Project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUIMediator extends Observable{
	private MachineModel model;
	private FilesMgr filesMgr;
	private StepControl stepControl;
	private JFrame frame;
	JMenuBar bar = new JMenuBar();

	private CodeViewPanel codeViewPanel;
	private MemoryViewPanel memoryViewPanel1;
	private MemoryViewPanel memoryViewPanel2;
	private MemoryViewPanel memoryViewPanel3;
	private ControlPanel controlPanel;
	//private ProcessorViewPanel processorPanel; // Project Part 1?
	private MenuBarBuilder menuBuilder; // Project Part 12

	private void createAndShowGUI(){
		setStepControl(new StepControl(this));
		setFilesMgr(new FilesMgr(this));
		filesMgr.initialize();
		setCodeViewPanel(new CodeViewPanel(this, model));
		setMemoryViewPanel1(new MemoryViewPanel(this, model, 0, 240));
		setMemoryViewPanel2(new MemoryViewPanel(this, model, 240, Memory.DATA_SIZE/2));
		setMemoryViewPanel3(new MemoryViewPanel(this, model, Memory.DATA_SIZE/2, Memory.DATA_SIZE));
		controlPanel=new ControlPanel(this);
		//setProcessorPanel(new ProcessorViewPanel(this, model));
		menuBuilder=new MenuBarBuilder(this);
		setFrame(new JFrame("Simulator"));

		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout(1,1));
		content.setBackground(Color.BLACK);
		content.setSize(new Dimension(1200, 600));

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1,3));
		frame.add(codeViewPanel.createCodeDisplay(), BorderLayout.LINE_START);
		center.add(memoryViewPanel1.createMemoryDisplay());
		center.add(memoryViewPanel2.createMemoryDisplay());
		center.add(memoryViewPanel3.createMemoryDisplay());
		frame.add(center, BorderLayout.CENTER);
		
		frame.add(controlPanel.createControlDisplay(),BorderLayout.PAGE_END);

		//return HERE for other GUI components
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// return HERE for other setup details
		frame.setVisible(true);

	}


	public void step(){
		if(model.getCurrentState()!=States.PROGRAM_HALTED &&
				model.getCurrentState()!=States.NOTHING_LOADED){
			try{
				model.step();
			} catch (CodeAccessException e) {
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to code from line " + model.getpCounter() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
			} catch (ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to data" + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
			} catch (NullPointerException e){
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to code " + model.getpCounter() + "\n"
								+ "Exception message: " + e.getMessage(),
								"NullPointerException",
								JOptionPane.OK_OPTION);
			} catch (IllegalArgumentException e){
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to code " + model.getpCounter() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Program error",
								JOptionPane.OK_OPTION);
			} catch (DivideByZeroException e){
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to code " + model.getpCounter() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Divide by zero error",
								JOptionPane.OK_OPTION);
			}
			setChanged();
			notifyObservers();
		}
	}

	public void execute(){
		while(model.getCurrentState()!=States.PROGRAM_HALTED &&
				model.getCurrentState()!=States.NOTHING_LOADED){
			try{
				model.step();
			} catch (CodeAccessException e) {
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to code from line " + model.getpCounter() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
			} catch (ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to data" + "\n"
								+ "Exception message: " + e.getMessage(),
								"Run time error",
								JOptionPane.OK_OPTION);
			} catch (NullPointerException e){
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to code " + model.getpCounter() + "\n"
								+ "Exception message: " + e.getMessage(),
								"NullPointerException",
								JOptionPane.OK_OPTION);
			} catch (IllegalArgumentException e){
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to code " + model.getpCounter() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Program error",
								JOptionPane.OK_OPTION);
			} catch (DivideByZeroException e){
				JOptionPane.showMessageDialog(
						frame, 
						"Illegal access to code " + model.getpCounter() + "\n"
								+ "Exception message: " + e.getMessage(),
								"Divide by zero error",
								JOptionPane.OK_OPTION);
			}
		}
		setChanged();
		notifyObservers();
	}

	public MachineModel getModel() {
		return model;
	}
	public void setModel(MachineModel model) {
		this.model = model;
	}
	public JFrame getFrame(){
		return frame;
	}

	public States getCurrentState(){
		return model.getCurrentState();
	}

	public void setStepControl(StepControl stepControl) {
		this.stepControl = stepControl;
	}



	private void setFrame(JFrame jFrame) {
		this.frame = jFrame;
		frame.setJMenuBar(bar);
		bar.add(menuBuilder.createFileMenu());
		bar.add(menuBuilder.createExecuteMenu());
		bar.add(menuBuilder.createJobsMenu());

	}


	public void setMemoryViewPanel1(MemoryViewPanel memoryViewPanel1) {
		this.memoryViewPanel1 = memoryViewPanel1;
	}


	public void setMemoryViewPanel2(MemoryViewPanel memoryViewPanel2) {
		this.memoryViewPanel2 = memoryViewPanel2;
	}


	public void setMemoryViewPanel3(MemoryViewPanel memoryViewPanel3) {
		this.memoryViewPanel3 = memoryViewPanel3;
	}


	public void setCodeViewPanel(CodeViewPanel codeViewPanel) {
		this.codeViewPanel = codeViewPanel;
	}


	public void setFilesMgr(FilesMgr filesMgr) {
		this.filesMgr = filesMgr;
	}


	public void setCurrentState(States s){
		if(s==States.PROGRAM_HALTED){
			stepControl.setAutoStepOn(false);
		}
		model.setCurrentState(s);
		//THE FOLLOWING 3 LINES ARE CALLED WHENEVER THE STATE IS CHANGED
		model.getCurrentState().enter();
		setChanged();
		notifyObservers();
	}

	public void exit() { // method executed when user exits the program
		int decision = JOptionPane.showConfirmDialog(
				frame, "Do you really wish to exit?",
				"Confirmation", JOptionPane.YES_NO_OPTION);
		if (decision == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public void clearJob(){
		model.clearJob();
		model.setCurrentState(States.NOTHING_LOADED);
		model.getCurrentState().enter();
		setChanged();
		notifyObservers("Clear");
	}

	public void makeReady(String s){
		stepControl.setAutoStepOn(false);
		model.setCurrentState(States.PROGRAM_LOADED_NOT_AUTOSTEPPING);
		model.getCurrentState().enter();
		setChanged();
		notifyObservers(s);
	}

	public void toggleAutoStep(){
		stepControl.toggleAutoStep();
		if(stepControl.isAutoStepOn()){ //There were <tt> before the StepControl in the code description - not sure why
			model.setCurrentState(States.AUTO_STEPPING);
		}else{
			model.setCurrentState(States.PROGRAM_LOADED_NOT_AUTOSTEPPING);
		}
		model.getCurrentState().enter();
		setChanged();
		notifyObservers();
	}

	public void reload() {
		stepControl.setAutoStepOn(false); //here too, there was a <tt><tt> before stepControl
		clearJob();
		filesMgr.finalLoad_ReloadStep(model.getCurrentJob());
	}

	public void changeToJob(int i) {
		model.changeToJob(i);
		if(model.getCurrentState()!=null){
			model.getCurrentState().enter();
			setChanged();
			notifyObservers();
		}
	}

	public void setPeriod(int period) {
		stepControl.setPeriod(period);
	}

	public void assembleFile() {
		filesMgr.assembleFile();
	}

	public void loadFile() {
		filesMgr.loadFile(model.getCurrentJob());
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUIMediator organizer = new GUIMediator();
				MachineModel model = new MachineModel(
						() 
						-> organizer.setCurrentState(States.PROGRAM_HALTED)
						);
				organizer.setModel(model);
				organizer.createAndShowGUI();
			}
		});
	}


}