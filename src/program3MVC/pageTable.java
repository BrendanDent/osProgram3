package program3MVC;

import java.util.ArrayList;

public class pageTable {
	ArrayList<Integer[]> pageTable = new ArrayList<Integer[]>();// page #, frame #

	

	void pageUse(int pageNum, int frameNum) {
		boolean inTable = false;
		for (Integer[] x : pageTable)
			if (x[0] == pageNum && x[2] == frameNum) {
				inTable = true;

			}
		if (!inTable)
			pageTable.add(new Integer[]{pageNum, frameNum});
	}
}
