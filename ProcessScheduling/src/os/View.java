package os;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;

import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.Font;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private final String[] options = 
		{"--please select an algorithm--", 
			"First Come First Serve (FCFS)", 
			"???", //Preemptive Shortest Job First
			"Non-Preemptive Shortest Job First", 
			"Preemptive Priority", 
			"Non-Preemptive Priority",
			"Round Robin"};
	private JButton addButton;
	private JButton removeButton;
	private JButton resetButton;
	private JButton calculateButton;
	private JScrollPane tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private final String[] columnNames = {"Process", "Arrival Time", "Burst Time", "Priority", "Finish Time", "WT", "TAT"};
	private String[][] data = {{"P1", "", "", "", "", "", ""}, {"P2", "", "", "", "", "", ""}, {"P3", "", "", "", "", "", ""}};
	private GanttChart ganttChartPanel;
	private JTextField avgTATTxtField;
	private JTextField totalTATTxtField;
	private JTextField avgWTTxtField;
	private JTextField totalWTTxtField;
	private ImageIcon icon;

	// create the frame
	public View() {
		super("Simulation of CPU Scheduling Algorithm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		initComponents();
		setIcon();
		this.setSize(new Dimension(800, 700));
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void initComponents() {
		
		// algorithm selection creation
		
		JLabel algoPanel = new JLabel("Algorithm : ");
		algoPanel.setFont(new Font("Dialog", Font.PLAIN, 15));
		algoPanel.setHorizontalAlignment(SwingConstants.CENTER);
		algoPanel.setBounds(40, 40, 150, 40);
		contentPane.add(algoPanel);

		comboBox = new JComboBox<String>(options);
		comboBox.setBounds(188, 40, 545, 40);
		contentPane.add(comboBox);
		
		// table panel creation
		
		tableModel = new DefaultTableModel(data, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) { // making Process, FT, WT, TAT not editable
				return column == 1 || column == 2 || column == 3 ? true : false;
			}
		};
		table = new JTable(tableModel);
		tablePanel = new JScrollPane(table);
		tablePanel.setBackground(Color.LIGHT_GRAY);
		tablePanel.setBounds(40, 100, 695, 190);
		contentPane.add(tablePanel);
		
		// controls creation
		addButton = new JButton("Add");
		addButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		addButton.setBackground(new Color(255, 204, 153));
		addButton.setBounds(219, 310, 120, 40);
		contentPane.add(addButton);
		
		removeButton = new JButton("Remove");
		removeButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		removeButton.setForeground(new Color(0, 0, 0));
		removeButton.setBackground(new Color(255, 204, 153));
		removeButton.setBounds(351, 310, 120, 40);
		contentPane.add(removeButton);
		
		resetButton = new JButton("Reset");
		resetButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		resetButton.setBackground(new Color(255, 204, 153));
		resetButton.setBounds(483, 310, 120, 40);
		contentPane.add(resetButton);
		
		calculateButton = new JButton("Calculate");
		calculateButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		calculateButton.setBackground(new Color(255, 204, 153));
		calculateButton.setBounds(615, 310, 120, 40);
		contentPane.add(calculateButton);
		
		// gantt chart panel creation
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 370, 705, 3);
		contentPane.add(separator);
		
		JLabel gcLabel = new JLabel("Gantt Chart :");
		gcLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		gcLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gcLabel.setBounds(45, 390, 107, 20);
		contentPane.add(gcLabel);
		
		ganttChartPanel = new GanttChart();
		ganttChartPanel.setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane(ganttChartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(40, 420, 695, 100); // +=40
		scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
		contentPane.add(scrollPane);
		
		// calculation summary creation

		JLabel totalWTLabel = new JLabel("Total Waiting Time :");
		totalWTLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		totalWTLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalWTLabel.setBounds(40, 540, 175, 40);
		contentPane.add(totalWTLabel);

		JLabel avgWTLabel = new JLabel("Average Waiting Time :");
		avgWTLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		avgWTLabel.setHorizontalAlignment(SwingConstants.CENTER);
		avgWTLabel.setBounds(40, 590, 175, 40);
		contentPane.add(avgWTLabel);

		JLabel totalTATLabel = new JLabel("Total Turnaround Time :");
		totalTATLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		totalTATLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalTATLabel.setBounds(389, 540, 188, 40);
		contentPane.add(totalTATLabel);
		
		JLabel avgTATLabel = new JLabel("Average Turnaround Time :");
		avgTATLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		avgTATLabel.setHorizontalAlignment(SwingConstants.CENTER);
		avgTATLabel.setBounds(389, 590, 188, 40);
		contentPane.add(avgTATLabel);
		
		//textfield for total waiting time
		totalWTTxtField = new JTextField();
		totalWTTxtField.setFont(new Font("Dialog", Font.PLAIN, 15));
		totalWTTxtField.setColumns(10);
		totalWTTxtField.setBounds(219, 540, 150, 40);
		totalWTTxtField.setEditable(false);
		contentPane.add(totalWTTxtField);
		
		//textfield for average waiting time
		avgWTTxtField = new JTextField();
		avgWTTxtField.setFont(new Font("Dialog", Font.PLAIN, 15));
		avgWTTxtField.setColumns(10);
		avgWTTxtField.setBounds(219, 590, 150, 40);
		avgWTTxtField.setEditable(false);
		contentPane.add(avgWTTxtField);
		
		//textfield for total turnaround time
		totalTATTxtField = new JTextField();
		totalTATTxtField.setFont(new Font("Dialog", Font.PLAIN, 15));
		totalTATTxtField.setColumns(10);
		totalTATTxtField.setBounds(585, 540, 150, 40);
		totalTATTxtField.setEditable(false);
		contentPane.add(totalTATTxtField);
		
		//textfield for average turnaround time
		avgTATTxtField = new JTextField();
		avgTATTxtField.setFont(new Font("Dialog", Font.PLAIN, 15));
		avgTATTxtField.setColumns(10);
		avgTATTxtField.setBounds(585, 590, 150, 40);
		avgTATTxtField.setEditable(false);
		contentPane.add(avgTATTxtField);	
	}
	
	// set icon
	private void setIcon() {
		try {
			icon = new ImageIcon(ImageIO.read(getClass().getResource("images/icon.png")));
		} catch (IOException ex) {
			System.out.println("Image not found");
		}
		this.setIconImage(icon.getImage());
	}
	
	public JComboBox<String> getComboBox() {
		return comboBox;
	}
	
	public JButton getAddButton() {
		return addButton;
	}
	
	public JButton getRemoveButton() {
		return removeButton;
	}
	
	
	public JButton getResetButton() {
		return resetButton;
	}
	
	public JButton getCalculateButton() {
		return calculateButton;
	}
	
	public JTable getTable() {
		return table;
	}
	
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	
	public JPanel getGanttChartPanel() {
		return ganttChartPanel;
	}

	public void setGanttChartPanel(GanttChart p) {
		ganttChartPanel = p;
	}
	
	public void setGanttChart(List<ProcessOutput> output) {
		ganttChartPanel.setProcessOutputList(output);
	}
	
	public void clearGanttChartPanel() {
		ganttChartPanel.clearGanttChart();;
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
