package program3MVC;

public class PCB {
	int procNum;
	int numPages;
	pageTable pageTable;
	
	PCB(int procNum){
		this.procNum = procNum;
		numPages = 0;
		pageTable = new pageTable();
	}
	public void addPage(int pageNum, int frameNum){
		boolean inTable = false;
		for(Integer[] x : pageTable.pageTable) {
			if(x[0] == pageNum) {
				inTable = true;
			}
		}
			if(!inTable){
				numPages++;
				pageTable.pageUse(pageNum, frameNum);
			
		}
	}
	
}
