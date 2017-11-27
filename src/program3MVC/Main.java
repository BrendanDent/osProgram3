package program3MVC;

public class Main {

	private static final int ADDRESS_SPACE = 64;
	private static final int PYSICAL_MEMORY = 16;
	private static final int FRAME = 1;
	public static void main(String[] args){
		View v = new View(PYSICAL_MEMORY, ADDRESS_SPACE,FRAME);
		 
		 
		 Model m = new Model(PYSICAL_MEMORY, ADDRESS_SPACE,FRAME, v);
		 Controller c = new Controller(m, v);
		 v.addActionListeners(c.new listener());
		while(true);
	}

}
