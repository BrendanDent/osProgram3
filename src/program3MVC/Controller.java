package program3MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
	private final int ADDRESS_SPACE = 64;
	private final int PYSICAL_MEMORY = 16;
	private final int FRAME = 1;
	View v;
	Model m;
	public Controller(Model m, View v){
		this.v = v;
		this.m = m;
		
	}
	
	class listener implements ActionListener{
		listener(){
		
		}
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand() == v.next.getActionCommand()){
				m.next();
			}
			if(e.getActionCommand() == v.fault.getActionCommand()){
				//m.fault();
			}
			if(e.getActionCommand() == v.completion.getActionCommand()){
				//m.completion;
			}
		}
	}
}
