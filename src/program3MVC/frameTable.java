package program3MVC;

import java.util.ArrayList;
//makes a table of given size to hold frame number process number and logical address
public class frameTable {
	int size;
	ArrayList<Integer[]> frameTable = new ArrayList<Integer[]>();// frame #,proc #,logical addr
	Boolean fault;														 
																	 
																	 
	ArrayList<Integer> freeFrameList = new ArrayList<Integer>();
	int victAddr, victProc;

	frameTable(int size) {
		this.size = size;
		for (int i = 0; i < size; i++) //sets up free frame list
			freeFrameList.add(i);
	}

	int useFrame(int proc, int addr) { //puts page in a frame if it is not already
		victAddr = -1;
		victProc = -1;
		boolean inFrame = false;
		for (int i = 0; i < frameTable.size(); i++)	//checks if page is in frame table
			if (frameTable.get(i)[1] == proc && frameTable.get(i)[2] == addr) {
				inFrame = true;
				frameTable.add(frameTable.remove(i));	//puts accessed page on bottom of arraylist to indicate use
			}
		if (!inFrame) {		//if page is not in frame table, add and remove victim if necessary
			fault = true;
			if (freeFrameList.isEmpty()){
				victProc = frameTable.get(0)[1];
				victAddr = frameTable.get(0)[2];
				frameTable.add(new Integer[] { frameTable.remove(0)[0], proc, addr }); //removes page from top of arraylist since it was least recently used
			}else
				frameTable.add(new Integer[] { freeFrameList.remove(0), proc, addr });
		}
		return frameTable.get(frameTable.size()-1)[0];
	}
}
