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
        }
    };
    
    ActionListener removeButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	view.getTable().clearSelection();
        	if (numberOfProcesses > 3) {
        		numberOfProcesses--;
        		view.getTableModel().removeRow(numberOfProcesses);
        	}
        }
    };

    ActionListener resetButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	view.getTable().clearSelection();
        	// clearing the table
        	for (int i = 0; i < numberOfProcesses; i++) {
        		for (int j = 1; j < 7; j++) {
        			view.getTableModel().setValueAt("", i, j);
        		}
        	}
        
            // remember to clear panel too

            view.getAvgTATTxtField().setText("");
            view.getTotalTATTxtField().setText("");
            view.getAvgWTTxtField().setText("");
            view.getTotalWTTxtField().setText("");
        }
    };
    
    ActionListener calculateButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	int choice = view.getComboBox().getSelectedIndex();
        	System.out.println(choice);
        	switch(choice) {
        		case 0: // not selected algorithm
        				JOptionPane.showMessageDialog(view,"Please select an algorithm!");  
        				System.out.println("1");
        				break;
        		case 1: // RR
        				roundRobinAlgorithm();
        				break;
        		case 2: // NP SJF
        				break;
        		case 3: // PP
        				break;        	
        				
        		// need to read from the table
        	}
        }
    };
    
    public void readDataFromTable(CPUScheduler scheduler) {
    	try {
    		// reading AT, BT and Priority
    		for (int i = 0; i < numberOfProcesses; i++) {
    			int at = Integer.parseInt((String) view.getTableModel().getValueAt(i, 1));
    			int bt = Integer.parseInt((String) view.getTableModel().getValueAt(i, 2));
    			int priority;
    			if (scheduler instanceof RoundRobin) {
    				priority = 1; // in case user did not enter
    			} 
    			else {
    				priority = Integer.parseInt((String) view.getTableModel().getValueAt(i, 3));
    			}
    			scheduler.add(new ProcessInput("P"+(i+1), at, bt, priority));
    			// System.out.println("at: " + at + " bt: " + bt + " pt: " + priority);
	    	}
    	} catch(Exception ex) {
    		JOptionPane.showMessageDialog(view,"Table contains non-integer!");  
    	}
	    	
    }
    
    public void writeDataToTable(CPUScheduler scheduler) {
    	// writing FT, WT, TAT to table
    	for (int i = 0; i < numberOfProcesses; i++) {
    		// view.getTableModel().setValueAt(aValue, row, column);
    	}
    	
    	// drawing gantt chart
    	
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
			readDataFromTable(scheduler);
			scheduler.process();
			writeDataToTable(scheduler);
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(view,"Please enter an integer!");  
		}
    }
}