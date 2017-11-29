package program3MVC;
//holds process info including process number, number of pages and faults, times referenced, and the page table
public class PCB {
	int procNum;
	int numPages, faults, references;
	pageTable pageTable;
	
	
	PCB(int procNum){
		this.procNum = procNum;
		numPages = 0;
		faults = 0;
		references = 0;
		pageTable = new pageTable();
	}
	public boolean addPage(int pageNum, int frameNum){ //adds page if not in pageTable
		boolean inTable = false;
		references++;
		for(Integer[] x : pageTable.pageTable) {
			if(x[0] == pageNum) {
				inTable = true;
			}
		}
			if(!inTable){
				numPages++;
				pageTable.pageTable.add(new Integer[] {pageNum, frameNum});
			
		}
			return !inTable;
	}
	public void removePage(int pageNum){ //removes frame entry for victim page 
		for(int i = 0; i < pageTable.pageTable.size(); i++) {
			if(pageTable.pageTable.get(i)[0] == pageNum) {
				pageTable.pageTable.get(i)[1] = null;
			}
		}
	
	}
	
}
