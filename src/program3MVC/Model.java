package program3MVC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Model {
	frameTable frameTable;
	pageTable pageTable;
	ArrayList<PCB> processes;
	BufferedReader reader;
	View v;
	Model(int physMem, int virtMem, int frameSize, View v){
		frameTable = new frameTable(physMem/frameSize);
		this.v = v;
		processes = new ArrayList<PCB>();
		
		File input = new File("./input3a.data");
		try {
			reader = new BufferedReader(new FileReader(input));
		} catch (FileNotFoundException e2) {
			System.out.println("File Not Found");
			System.exit(1);
		}
	}
	
	public void next(){
		int proc, addr;
		String line, split[];
		boolean found = false;
		PCB match = null;
				try {
					if((line = reader.readLine()) != null) {
					split = line.split(":");
					proc = Integer.parseInt(split[0].substring(1));
					addr =  Integer.parseInt(split[1].trim(), 2);
					for(PCB p : processes) {
						if(p.procNum == proc) {
							found = true;
							match = p;
						}
					}
					if(!found){
						processes.add(new PCB(proc));
						match = processes.get(processes.size()-1);
						
					}
					
					match.addPage(addr, frameTable.useFrame(proc, addr));
					v.update(frameTable, match.pageTable, proc, addr, frameTable.victProc, frameTable.victAddr);
					
					}
					else {
						v.endOfFile();
					}
				} catch (IOException e1) {
					System.out.println("File not found or not in correct format");
					System.exit(1);
				}
	}
	
	public void fault(){
		int proc, addr;
		String line = null, split[];
		boolean found = false, fault = false;
		PCB match = null;
				try {
					while(fault == false && (line = reader.readLine()) != null) {
					split = line.split(":");
					proc = Integer.parseInt(split[0].substring(1));
					addr =  Integer.parseInt(split[1].trim(), 2);
					for(PCB p : processes) {
						if(p.procNum == proc) {
							found = true;
							match = p;
						}
					}
					if(!found){
						processes.add(new PCB(proc));
						match = processes.get(processes.size()-1);
						
					}
					
					match.addPage(addr, frameTable.useFrame(proc, addr));
					if(frameTable.freeFrameList.size() != 0 || frameTable.victProc != -1){
						fault = true;
					}
					v.update(frameTable, match.pageTable, proc, addr, frameTable.victProc, frameTable.victAddr);
					
					}
					if(line == null) {
						v.endOfFile();
					}
				} catch (IOException e1) {
					System.out.println("File not found or not in correct format");
					System.exit(1);
				}
	}
	
	public void completion(){
		int proc, addr;
		String line, split[];
		boolean found = false;
		PCB match = null;
				try {
					while((line = reader.readLine()) != null) {
					split = line.split(":");
					proc = Integer.parseInt(split[0].substring(1));
					addr =  Integer.parseInt(split[1].trim(), 2);
					for(PCB p : processes) {
						if(p.procNum == proc) {
							found = true;
							match = p;
						}
					}
					if(!found){
						processes.add(new PCB(proc));
						match = processes.get(processes.size()-1);
						
					}
					
					match.addPage(addr, frameTable.useFrame(proc, addr));
					if(frameTable.frameTable.size() != 0){
			
					}
					v.update(frameTable, match.pageTable, proc, addr, frameTable.victProc, frameTable.victAddr);
					
					}
					v.endOfFile();
					
				} catch (IOException e1) {
					System.out.println("File not found or not in correct format");
					System.exit(1);
				}
	}
	
	
}
