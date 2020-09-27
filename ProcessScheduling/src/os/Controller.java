package os;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Controller {
    private View view;
    private int numberOfProcesses = 3;
    private CPUScheduler scheduler;
    private DisplayResult displayResult;

    public Controller(View view) {
        this.view = view;
        setButtonListener();
    }	

    private void setButtonListener() {
    	view.getAddButton().addActionListener(addButtonListener);
    	view.getRemoveButton().addActionListener(removeButtonListener);
        view.getResetButton().addActionListener(resetButtonListener);
        view.getCalculateButton().addActionListener(calculateButtonListener);
    }

    ActionListener addButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	view.getTable().clearSelection();
        	if (numberOfProcesses < 10) {
        		numberOfProcesses++;
        		view.getTableModel().addRow(new String[] {"P"+numberOfProcesses, "", "", "", "", "", ""});
        	}
        	System.out.println("Add Button Pressed: total row " + numberOfProcesses);
        }   
    };
    
    ActionListener removeButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	System.out.print("Remove Button Pressed: ");
        	view.getTable().clearSelection();
        	if (numberOfProcesses > 3) {
        		numberOfProcesses--;
        		view.getTableModel().removeRow(numberOfProcesses);
        		System.out.println("row " + numberOfProcesses +" deleted");
        	} else {
        		System.out.println("minimum row reached");
        	}
        	
        }
    };

    ActionListener resetButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	System.out.println("Reset Button Pressed");
        	view.getTable().clearSelection();
        	// clearing the table
        	for (int i = 0; i < numberOfProcesses; i++) {
        		for (int j = 1; j < 7; j++) {
        			view.getTableModel().setValueAt("", i, j);
        		}
        	}
        	
        	// clearing the table row
        	while (numberOfProcesses > 3) {
        		numberOfProcesses--;
        		view.getTableModel().removeRow(numberOfProcesses);
        	}
        
            // clearing summary panel
        	view.getAvgTATTxtField().setText("");
            view.getTotalTATTxtField().setText("");
            view.getAvgWTTxtField().setText("");
            view.getTotalWTTxtField().setText("");
        }
    };
    
    ActionListener calculateButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	System.out.println("Calculate Button Pressed");
        	int choice = view.getComboBox().getSelectedIndex();
        	System.out.println("Choice: " + choice);
        	switch(choice) {
        		case 0: // no selected algorithm
        				JOptionPane.showMessageDialog(view,"Please select an algorithm!");  
        				break;
        		case 1: scheduler = new RoundRobin();
        				roundRobinAlgorithm();
        				break;
        		case 2: scheduler = new NonPreemptiveSJF();
        				break;
        		case 3: scheduler = new PriorityPreemptive();        				
        				break;      
        	}
        	// Calculate the process
        	readDataFromTable(scheduler);
			scheduler.process();
			writeDataToTable(scheduler);
			displayResult = new DisplayResult(scheduler);
			view.setGanttChart(scheduler.getProcessOutputList());
        }
    };
    
    public void readDataFromTable(CPUScheduler scheduler) {
    	String errorMessage = "";
    	try {
    		System.out.println("\n********************\n"
							 + "readDataFromTable"
							 + "\n********************");
    		System.out.println("Process\tAT\tBT\tPriority");
    		// reading AT, BT and Priority
    		for (int i = 0; i < numberOfProcesses; i++) {
    			errorMessage = ("Invalid data at P" + (i + 1));
    			int at = Integer.parseInt((String) view.getTableModel().getValueAt(i, 1));
    			int bt = Integer.parseInt((String) view.getTableModel().getValueAt(i, 2));
    			int priority;
    			if (scheduler instanceof RoundRobin) {
					priority = 1; // in case user did not enter
					view.getTableModel().setValueAt("1", i, 3);
    			} 
    			else {
    				priority = Integer.parseInt((String) view.getTableModel().getValueAt(i, 3));
    			}

    			scheduler.add(new Process("P"+(i+1), at, bt, priority));
    			System.out.println("P"+(i+1) + "\t" + at + "\t" + bt + "\t" + priority);
	    	}
    	} catch(Exception ex) {
    		JOptionPane.showMessageDialog(view, errorMessage);  
    	}
	    	
    }
    
    public void writeDataToTable(CPUScheduler scheduler) {
    	// writing FT, WT, TAT to table
    	for (int i = 0; i < numberOfProcesses; i++) {
			view.getTableModel().setValueAt(scheduler.getProcessInputList().get(i).getFinishTime(), i, 4);
			view.getTableModel().setValueAt(scheduler.getProcessInputList().get(i).getWaitingTime(), i, 5);
			view.getTableModel().setValueAt(scheduler.getProcessInputList().get(i).getTurnaroundTime(), i, 6);
    	}
    	
    	// writing summary
    	view.getAvgTATTxtField().setText(scheduler.getAverageTurnAroundTime()+"");
    	view.getTotalTATTxtField().setText(scheduler.getTotalTurnAroundTime()+"");
    	view.getAvgWTTxtField().setText(scheduler.getAverageWaitingTime()+"");
    	view.getTotalWTTxtField().setText(scheduler.getTotalWaitingTime()+"");
    	
    }
    
    public void roundRobinAlgorithm() {
    	String q = JOptionPane.showInputDialog(view,"Enter Time Quantum");  
		try {
			int quantum = Integer.parseInt(q);
			scheduler = new RoundRobin();
			scheduler.setTimeQuantum(quantum);
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(view,"Invalid Time Quantum\nProceed with Default Quantum: 1");  
		}
    }
}