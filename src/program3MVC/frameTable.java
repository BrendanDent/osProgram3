package program3MVC;

import java.util.ArrayList;

public class frameTable {
	int size;
	ArrayList<Integer[]> frameTable = new ArrayList<Integer[]>();// frame #,
																	// proc #,
																	// logical
																	// addr
	ArrayList<Integer> freeFrameList = new ArrayList<Integer>();
	int victAddr, victProc;

	frameTable(int size) {
		this.size = size;
		for (int i = 0; i < size; i++)
			freeFrameList.add(i);
	}

	int useFrame(int proc, int addr) {
		victAddr = -1;
		victProc = -1;
		boolean inFrame = false, fault = false;
		for (int i = 0; i < frameTable.size(); i++)
			if (frameTable.get(i)[1] == proc && frameTable.get(i)[2] == addr) {
				inFrame = true;
				frameTable.add(frameTable.remove(i));
			}
		if (!inFrame) {
			fault = true;
			if (freeFrameList.isEmpty()){
				victProc = frameTable.get(0)[1];
				victAddr = frameTable.get(0)[2];
				frameTable.add(new Integer[] { frameTable.remove(0)[0], proc, addr });
			}else
				frameTable.add(new Integer[] { freeFrameList.remove(0), proc, addr });
		}
		return frameTable.get(frameTable.size()-1)[0];
	}
}
