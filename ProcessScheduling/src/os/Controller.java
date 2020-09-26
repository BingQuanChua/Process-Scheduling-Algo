package os;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Controller {
    private View view;
    private int numberOfProcesses = 3;

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
        	if (numberOfProcesses < 10) {
        		numberOfProcesses++;
        		view.getTableModel().addRow(new String[] {"P"+numberOfProcesses, "", "", "", "", "", ""});
        	}
        }
    };
    
    ActionListener removeButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	if (numberOfProcesses > 3) {
        		numberOfProcesses--;
        		view.getTableModel().removeRow(numberOfProcesses);
        	}
        }
    };

    ActionListener resetButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
 
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
        				break;
        		case 2: // NP SJF
        				break;
        		case 3: // PP
        				break;        	
        				
        		// need to read from the table
        	}
        }
    };
}