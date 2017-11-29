package program3MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//allows communication from view to model via actionListener
public class Controller {
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
				m.fault();
			}
			if(e.getActionCommand() == v.completion.getActionCommand()){
				m.completion();
			}
		}
	}
}
