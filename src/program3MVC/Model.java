package program3MVC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
//holds and interacts with data from file
public class Model {
	frameTable frameTable;
	pageTable pageTable;
	ArrayList<PCB> processes;
	BufferedReader reader;
	View v;
	int virtMem, pages = 0;

	Model(int physMem, int virtMem, int frameSize, View v) {
		frameTable = new frameTable(physMem / frameSize);
		this.v = v;
		processes = new ArrayList<PCB>();
		this.virtMem = virtMem;
		File input = new File("./input3a.data");
		try {
			reader = new BufferedReader(new FileReader(input));
		} catch (FileNotFoundException e2) {
			System.out.println("File Not Found");
			System.exit(1);
		}
	}

	public void next() { //reads next line from file and processes it
		int proc, addr;
		String line, split[];
		boolean found = false, fault;
		PCB match = null;
		try {
			if ((line = reader.readLine()) != null) { //reads a line
				fault = false;
				split = line.split(":");
				proc = Integer.parseInt(split[0].substring(1));
				addr = Integer.parseInt(split[1].trim(), 2);
				for (PCB p : processes) {	//checks for process in PCB arraylist
					if (p.procNum == proc) {
						found = true;
						match = p;
					}
				}
				if (!found) {	//makes new PCB if not found
					processes.add(new PCB(proc));
					match = processes.get(processes.size() - 1);

				}
				if (frameTable.freeFrameList.size() != 0) { //fault if freeframelist is not empty
					fault = true;
				}
				if(match.addPage(addr, frameTable.useFrame(proc, addr))) { //adds page to frame and page tables if needed. 
					pages++;
					if(pages > virtMem)
						v.memExceeded();
				}
				if(frameTable.victProc != -1) { 	//checks if process was evicted
					fault = true;
					processes.get(frameTable.victProc-1).removePage(frameTable.victAddr);
				}
				if (fault) {
					match.faults++;
				}
				v.update(frameTable, match.pageTable, proc, addr, frameTable.victProc, frameTable.victAddr);

			} else {
				v.endOfFile(processes);
			}
		} catch (IOException e1) {
			System.out.println("File not found or not in correct format");
			System.exit(1);
		}
	}

	public void fault() {	//reads lines from file until a fault occurs and processes them
		int proc, addr;
		String line = null, split[];
		boolean found = false, fault = false;
		PCB match = null;
		try {
			while (fault == false && (line = reader.readLine()) != null) { //checks if more lines should be read
				found = false;
				split = line.split(":");
				proc = Integer.parseInt(split[0].substring(1));
				addr = Integer.parseInt(split[1].trim(), 2);
				for (PCB p : processes) {	//finds process in PCB arraylist
					if (p.procNum == proc) {
						found = true;
						match = p;
					}
				}
				if (!found) {	//makes new PCB if not found
					processes.add(new PCB(proc));
					match = processes.get(processes.size() - 1);

				}
				if (frameTable.freeFrameList.size() != 0) { //fault if freeframelist is not empty
					fault = true;
				}
				if(match.addPage(addr, frameTable.useFrame(proc, addr))) { //adds page to frame and page tables if needed. 
					pages++;
					if(pages > virtMem)
						v.memExceeded();
				}
				if(frameTable.victProc != -1) {	//checks if process was evicted
					fault = true;
					processes.get(frameTable.victProc-1).removePage(frameTable.victAddr);
				}
				if (fault) {
					match.faults++;
				}
				v.update(frameTable, match.pageTable, proc, addr, frameTable.victProc, frameTable.victAddr);

			}
			if (line == null) {	
				v.endOfFile(processes);
			}
		} catch (IOException e1) {
			System.out.println("File not found or not in correct format");
			System.exit(1);
		}
	}

	public void completion() { //reads lines from file until end of file and processes them
		int proc, addr;
		String line, split[];
		boolean found = false, fault;
		PCB match = null;
		try {
			while ((line = reader.readLine()) != null) { //reads all lines
				fault = false;
				found = false;
				split = line.split(":");
				proc = Integer.parseInt(split[0].substring(1));
				addr = Integer.parseInt(split[1].trim(), 2);
				for (PCB p : processes) {	//looks for process in PCB arraylist
					if (p.procNum == proc) {
						found = true;
						match = p;
					}
				}
				if (!found) {	//makes new PCB if not found
					processes.add(new PCB(proc));
					match = processes.get(processes.size() - 1);

				}
				if (frameTable.freeFrameList.size() != 0) //fault if freeframelist is not empty
					fault = true;
				if(match.addPage(addr, frameTable.useFrame(proc, addr))) { //adds page to frame and page tables if needed. 
					pages++;
					if(pages > virtMem)
						v.memExceeded();
				}
				if(frameTable.victProc != -1) { 	//checks if process was evicted
					fault = true;
					processes.get(frameTable.victProc-1).removePage(frameTable.victAddr);
				}
				if (fault) {
					match.faults++;
				}
				v.update(frameTable, match.pageTable, proc, addr, frameTable.victProc, frameTable.victAddr);
				
			}
			v.endOfFile(processes);
		} catch (IOException e1) {
			System.out.println("File not found or not in correct format");
			System.exit(1);
		}
	}

}
