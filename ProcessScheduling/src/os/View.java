package os;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import java.awt.Color;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JRadioButton algo1RdBtn;
	private JRadioButton algo2RdBtn;
	private JRadioButton algo3RdBtn;
	private ButtonGroup btnGroup;
	private JTextField arrivalTxtField;
	private JTextField burstTxtField;
	private JTextField priorityTQTxtField;
	private JLabel priorityTQPanel;
	private JButton resetButton;
	private JButton calculateButton;
	private JPanel tablePanel;
	private JTable table;
	private JPanel ganttChartPanel;
	private JTextField avgTATTxtField;
	private JTextField totalTATTxtField;
	private JTextField avgWTTxtField;
	private JTextField totalWTTxtField;

	// launch the application, will delete later
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// create the frame
	public View() {
		super("Simulation of CPU Scheduling Algorithm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		initComponents();
	}
	
	private void initComponents() {
		
		// upper part creation
		
		JLabel algoPanel = new JLabel("Algorithm : ");
		algoPanel.setHorizontalAlignment(SwingConstants.CENTER);
		algoPanel.setBounds(40, 40, 195, 40);
		contentPane.add(algoPanel);
		
		algo1RdBtn = new JRadioButton("Round Robin");
		algo1RdBtn.setActionCommand("RR");
		algo1RdBtn.setBounds(247, 40, 160, 40);
		algo1RdBtn.setSelected(true);
		contentPane.add(algo1RdBtn);
		
		algo2RdBtn = new JRadioButton("Non Preemptive SJF");
		algo2RdBtn.setActionCommand("NPSJF");
		algo2RdBtn.setBounds(411, 40, 160, 40);
		contentPane.add(algo2RdBtn);
		
		algo3RdBtn = new JRadioButton("Preemptive Priority");
		algo3RdBtn.setActionCommand("PR");
		algo3RdBtn.setBounds(575, 40, 160, 40);
		contentPane.add(algo3RdBtn);
		setSize(new Dimension(800, 1000));

		btnGroup = new ButtonGroup();
		btnGroup.add(algo1RdBtn);
		btnGroup.add(algo2RdBtn);
		btnGroup.add(algo3RdBtn);
		
		JLabel arrivalPanel = new JLabel("Arrival Time :");
		arrivalPanel.setHorizontalAlignment(SwingConstants.CENTER);
		arrivalPanel.setBounds(40, 88, 195, 40);
		contentPane.add(arrivalPanel);
		
		arrivalTxtField = new JTextField();
		arrivalTxtField.setBounds(235, 88, 500, 40);
		contentPane.add(arrivalTxtField);
		arrivalTxtField.setColumns(10);
		
		JLabel burstPanel = new JLabel("Burst Time :");
		burstPanel.setHorizontalAlignment(SwingConstants.CENTER);
		burstPanel.setBounds(40, 136, 195, 40);
		contentPane.add(burstPanel);
		
		burstTxtField = new JTextField();
		burstTxtField.setColumns(10);
		burstTxtField.setBounds(235, 136, 500, 40);
		contentPane.add(burstTxtField);
		
		priorityTQPanel = new JLabel("Time Quantum :");
		priorityTQPanel.setHorizontalAlignment(SwingConstants.CENTER);
		priorityTQPanel.setBounds(40, 184, 195, 40);
		contentPane.add(priorityTQPanel);
		
		priorityTQTxtField = new JTextField();
		priorityTQTxtField.setColumns(10);
		priorityTQTxtField.setBounds(235, 184, 500, 40);
		contentPane.add(priorityTQTxtField);
		
		resetButton = new JButton("Reset");
		resetButton.setBounds(480, 232, 120, 40);
		contentPane.add(resetButton);
		
		calculateButton = new JButton("Calculate");
		calculateButton.setBounds(615, 232, 120, 40);
		contentPane.add(calculateButton);
		
		// lower part creation
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 290, 705, 3);
		contentPane.add(separator);
		
		JLabel resultLabel = new JLabel("Results");
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setBounds(40, 297, 695, 40);
		contentPane.add(resultLabel);
		
		tablePanel = new JPanel();
		tablePanel.setBackground(Color.WHITE);
		tablePanel.setBounds(40, 340, 695, 300);	
		table = new JTable();
		tablePanel.add(table);
		contentPane.add(tablePanel);
		
		ganttChartPanel = new JPanel();
		ganttChartPanel.setBackground(Color.WHITE);
		ganttChartPanel.setBounds(40, 650, 695, 150);
		contentPane.add(ganttChartPanel);
		
		JLabel avgTATPanel = new JLabel("Average Turn Around Time :");
		avgTATPanel.setHorizontalAlignment(SwingConstants.CENTER);
		avgTATPanel.setBounds(40, 810, 175, 40);
		contentPane.add(avgTATPanel);
		
		JLabel totalTATPanel = new JLabel("Total Turn Around Time :");
		totalTATPanel.setHorizontalAlignment(SwingConstants.CENTER);
		totalTATPanel.setBounds(40, 860, 175, 40);
		contentPane.add(totalTATPanel);
		
		JLabel avgWTPanel = new JLabel("Average Waiting Time :");
		avgWTPanel.setHorizontalAlignment(SwingConstants.CENTER);
		avgWTPanel.setBounds(398, 810, 175, 40);
		contentPane.add(avgWTPanel);
		
		JLabel totalWTPanel = new JLabel("Total Waiting Time :");
		totalWTPanel.setHorizontalAlignment(SwingConstants.CENTER);
		totalWTPanel.setBounds(398, 860, 175, 40);
		contentPane.add(totalWTPanel);
		
		avgTATTxtField = new JTextField();
		avgTATTxtField.setColumns(10);
		avgTATTxtField.setBounds(227, 810, 150, 40);
		avgTATTxtField.setEditable(false);
		contentPane.add(avgTATTxtField);
		
		totalTATTxtField = new JTextField();
		totalTATTxtField.setColumns(10);
		totalTATTxtField.setBounds(227, 860, 150, 40);
		totalTATTxtField.setEditable(false);
		contentPane.add(totalTATTxtField);
		
		avgWTTxtField = new JTextField();
		avgWTTxtField.setColumns(10);
		avgWTTxtField.setBounds(585, 810, 150, 40);
		avgWTTxtField.setEditable(false);
		contentPane.add(avgWTTxtField);
		
		totalWTTxtField = new JTextField();
		totalWTTxtField.setColumns(10);
		totalWTTxtField.setBounds(585, 860, 150, 40);
		totalWTTxtField.setEditable(false);
		contentPane.add(totalWTTxtField);
	}

	public JRadioButton getAlgo1RdBtn() {
		return algo1RdBtn;
	}
	
	public JRadioButton getAlgo2RdBtn() {
		return algo2RdBtn;
	}
	
	public JRadioButton getAlgo3RdBtn() {
		return algo3RdBtn;
	}

	public ButtonGroup getBtnGroup() {
		return btnGroup;
	}
	
	public JTextField getArrivalTxtField() {
		return arrivalTxtField;
	}
	
	public JTextField getBurstTxtField() {
		return burstTxtField;
	}
	
	public JTextField getPriorityTQTxtField() {
		return priorityTQTxtField;
	}

	public JLabel getPriorityTQPanel() {
		return priorityTQPanel;
	}
	
	public JButton getResetButton() {
		return resetButton;
	}
	
	public JButton getCalculateButton() {
		return calculateButton;
	}
	
	public JPanel getTablePanel() {
		return tablePanel;
	}
	
	public JTable getTable() {
		return table;
	}
	
	public JPanel getGanttChartPanel() {
		return ganttChartPanel;
	}
	
	public JTextField getAvgTATTxtField() {
		return avgTATTxtField;
	}
	
	public JTextField getTotalTATTxtField() {
		return totalTATTxtField;
	}
	
	public JTextField getAvgWTTxtField() {
		return avgWTTxtField;
	}
	public JTextField getTotalWTTxtField() {
		return totalWTTxtField;
	}
}
