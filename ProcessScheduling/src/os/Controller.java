package os;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Controller {
    private View view;
    private int numberOfProcesses = 3;
    private CPUScheduler scheduler;

    public Controller(View view) {
        this.view = view;
        setButtonListener();
    }	
    
    // method for action listener
    private void setButtonListener() {
    	view.getAddButton().addActionListener(addButtonListener);
    	view.getRemoveButton().addActionListener(removeButtonListener);
        view.getResetButton().addActionListener(resetButtonListener);
        view.getCalculateButton().addActionListener(calculateButtonListener);
    }
    
    // add new process
    ActionListener addButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	view.getTable().clearSelection();
        	if (numberOfProcesses < 10) {
        		numberOfProcesses++;
				view.getTableModel().addRow(new String[] {"P"+numberOfProcesses, "", "", "", "", "", ""});
				System.out.println("Add Button Pressed: total row " + numberOfProcesses);
			}
			else {
				System.out.println("Maximum number of rows reached!");
			}
        	
        }   
    };
    
    // remove existing process
    ActionListener removeButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	System.out.print("Remove Button Pressed: ");
        	view.getTable().clearSelection();
        	if (numberOfProcesses > 3) {
        		numberOfProcesses--;
        		view.getTableModel().removeRow(numberOfProcesses);
        		System.out.println("row " + numberOfProcesses +" deleted");
			} 
			else {
        		System.out.println("Minimum number of rows reached!");
        	}
        	
        }
    };
    
    // clear all table and result
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
			/*
			 * while (numberOfProcesses > 3) { numberOfProcesses--;
			 * view.getTableModel().removeRow(numberOfProcesses); }
			 */
        	
        	// clear gantt chart panel
        	view.clearGanttChartPanel();
        
            // clearing summary panel
        	view.getAvgTATTxtField().setText("");
            view.getTotalTATTxtField().setText("");
            view.getAvgWTTxtField().setText("");
            view.getTotalWTTxtField().setText("");
        }
    };
    
    // generate the result
    ActionListener calculateButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	System.out.println("Calculate Button Pressed");
        	int choice = view.getComboBox().getSelectedIndex();
        	System.out.println("Choice: " + choice);
        	
        	try {
	        	switch(choice) {
	        		case 0: // no selected algorithm
	        				JOptionPane.showMessageDialog(view,"Please select an algorithm!");  
	        				throw new Exception();
	        				//break;
	        		case 1: scheduler = new RoundRobin();
	        				roundRobinAlgorithm();
	        				break;
	        		case 2: scheduler = new NonPreemptiveSJF();
	        				break;
	        		case 3: scheduler = new PriorityPreemptive();  
	        				break;      
	        	}

	        	// Calculate the process
	        	boolean validData = readDataFromTable(scheduler);
	        	if(!validData)
	        		throw new Exception();
	        	scheduler.process();
	        	writeDataToTable(scheduler);
	        	new DisplayResult(scheduler);
	        	
        	} catch (Exception ex) {
        		System.out.println("Process Fail. Please Try Again");
        	}
        }
    };
    
    // read all data from table
    public boolean readDataFromTable(CPUScheduler scheduler) {
    	String errorMessage = "";
    	try {
    		System.out.println("\n********************\n"
							 + "readDataFromTable"
							 + "\n********************");
    		System.out.println("Process\tAT\tBT\tPriority");
    		// reading AT, BT and Priority
    		for (int i = 0; i < numberOfProcesses; i++) {
    			errorMessage = ("Invalid data input at P" + (i + 1));
    			int at = Integer.parseInt((String) view.getTableModel().getValueAt(i, 1));
    			int bt = Integer.parseInt((String) view.getTableModel().getValueAt(i, 2));
    			int priority;
    			
    			if ((scheduler instanceof NonPreemptiveSJF || scheduler instanceof RoundRobin) && (String) view.getTableModel().getValueAt(i, 3) == "") {
    					priority = 1; // in case user did not enter
    					view.getTableModel().setValueAt("1", i, 3);
    			}
    			else {
	    			priority = Integer.parseInt((String) view.getTableModel().getValueAt(i, 3));
	    		}
    			
    			
    			if(at < 0 || bt < 1 || priority < 0) {
    				System.out.println(errorMessage);
    				throw new Exception();
    			}

    			scheduler.add(new Process("P"+(i+1), at, bt, priority));
    			System.out.println("P"+(i+1) + "\t" + at + "\t" + bt + "\t" + priority);
	    	}
    		return true;
    		
    	} catch(Exception ex) {
    		JOptionPane.showMessageDialog(view, errorMessage);  
    	}
    	return false;	
    }
    
    // write all data to table
    public void writeDataToTable(CPUScheduler scheduler) {
    	// writing FT, WT, TAT to table
    	for (int i = 0; i < numberOfProcesses; i++) {
    		char processNum = scheduler.getProcessInputList().get(i).getProcessName().charAt(1);
    		int index = ( Integer.parseInt(String.valueOf(processNum)) - 1 );
    		view.getTableModel().setValueAt(scheduler.getProcessInputList().get(i).getFinishTime(), index, 4);
    		view.getTableModel().setValueAt(scheduler.getProcessInputList().get(i).getWaitingTime(), index, 5);
    		view.getTableModel().setValueAt(scheduler.getProcessInputList().get(i).getTurnaroundTime(), index, 6);
    	}
    	
    	// Draw gantt chart
    	view.setGanttChart(scheduler.getProcessOutputList());

    	// writing summary
    	view.getAvgTATTxtField().setText(scheduler.getAverageTurnAroundTime()+"");
    	view.getTotalTATTxtField().setText(scheduler.getTotalTurnAroundTime()+"");
    	view.getAvgWTTxtField().setText(scheduler.getAverageWaitingTime()+"");
    	view.getTotalWTTxtField().setText(scheduler.getTotalWaitingTime()+"");
    }
    
    public void roundRobinAlgorithm() {
    	String q = JOptionPane.showInputDialog(view,"Enter Time Quantum\n (default time quantum = 3)");  
		try {
			int quantum = Integer.parseInt(q);
			scheduler = new RoundRobin();
			scheduler.setTimeQuantum(quantum);
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(view,"Invalid Time Quantum!\nProceed with Time Quantum = 3");  
		}
    }
}