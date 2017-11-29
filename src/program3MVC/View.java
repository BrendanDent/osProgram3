package program3MVC;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//displays data for the user
public class View {
	int physMem, virtMem, frameSize;
	ArrayList<PCB> processes;
	frameTable frameTable;
	pageTable pageTable;
	BufferedReader reader;
	JButton next, fault, completion;
	JLabel process, page, victim;
	JFrame frame;
	JPanel tablePanel, infoPanel, buttonPanel, mainPanel;
	JTable frameJTable, pageJTable;
	String[] frameColNames = { "Frame #", "Process #", "Page #" };
	String[] pageColNames = { "Page #", "Frame #" };
	Object[][] frameData, pageData;

	View(int physMem, int virtMem, int frameSize) { //sets up GUI
		this.physMem = physMem;
		this.virtMem = virtMem;
		this.frameSize = frameSize;
		frame = new JFrame();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		tablePanel = new JPanel();
		infoPanel = new JPanel();
		buttonPanel = new JPanel();

		Object[][] frameData = new Object[physMem][3];
		for (int i = 0; i < physMem; i++)
			frameData[i][0] = i;
		frameJTable = new JTable(frameData, frameColNames);
		JScrollPane frameTablePane = new JScrollPane(frameJTable);
		Object[][] pageData = new Object[physMem][3];
		pageJTable = new JTable(pageData, pageColNames);
		JScrollPane pageTablePane = new JScrollPane(pageJTable);

		tablePanel.setLayout(new GridLayout());
		tablePanel.add(frameTablePane);
		tablePanel.add(pageTablePane);

		process = new JLabel("Current Process: ");
		page = new JLabel("Page Referenced: ");
		victim = new JLabel("Evicted Page: ");
		infoPanel.setLayout(new GridLayout());
		infoPanel.add(process);
		infoPanel.add(page);
		infoPanel.add(victim);

		next = new JButton("Next");
		fault = new JButton("Run To Next Fault");
		completion = new JButton("Run To Completion");

		buttonPanel.setLayout(new GridLayout());
		buttonPanel.add(next);
		buttonPanel.add(fault);
		buttonPanel.add(completion);

		mainPanel.add(tablePanel);
		mainPanel.add(infoPanel);
		mainPanel.add(buttonPanel);
		frame.add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void update(frameTable frames, pageTable pages, int proc, int addr, int victProc, int victAddr) { //updates tables and info
		frameData = new Object[physMem][3];
		for (int i = 0; i < physMem; i++)	//adds frame numbers
			frameData[i][0] = i;
		for (Integer[] x : frames.frameTable) { //adds frame contents
			frameData[x[0]][1] = x[1];
			frameData[x[0]][2] = x[2];
		}
		frameJTable.setModel(new DefaultTableModel(frameData, frameColNames));
		pageData = new Object[physMem][3];
		for (Integer[] x : pages.pageTable) {	//populates corresponding page table
			pageData[x[0]][0] = x[0];
			pageData[x[0]][1] = x[1];
		}

		pageJTable.setModel(new DefaultTableModel(pageData, pageColNames));
		process.setText("Current Process: " + proc);
		page.setText("Page Referenced: " + addr);
		if (victProc != -1)
			victim.setText("Evicted Page: P" + victProc + " " + victAddr);
		else
			victim.setText("Evicted Page: ");
	}

	public void addActionListeners(ActionListener a) {
		next.addActionListener(a);
		fault.addActionListener(a);
		completion.addActionListener(a);
	}

	public void memExceeded() {
		JOptionPane.showMessageDialog(frame, "Virtual Memory Exceeded. Please Wait For A Process To Finish.");
	}
	
	public void endOfFile(ArrayList<PCB> processes) { //lets user know they have reached the end of the file and prints out info
		JOptionPane.showMessageDialog(frame, "Reached End of File");
		next.setEnabled(false);
		fault.setEnabled(false);
		completion.setEnabled(false);
		System.out.println("Process Statistics\n");
		for(PCB process:processes) {
			System.out.println("Process: "  + process.procNum);
			System.out.println("Pages: " + process.numPages);
			System.out.println("Memeory References: " + process.references);
			System.out.println("Page Faults: " +process.faults);
			System.out.println();
		}
		
	}

}
