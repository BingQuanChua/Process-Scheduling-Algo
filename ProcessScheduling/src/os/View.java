package os;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComboBox;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private final String[] options = {"--please select an algorithm--", "Round Robin", "Non Preemptive SJF", "Preemptive Priority"};
	private JButton addButton;
	private JButton removeButton;
	private JButton resetButton;
	private JButton calculateButton;
	private JScrollPane tablePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private final String[] columnNames = {"Process", "Arrival Time", "Burst Time", "Priority", "Finish Time", "WT", "TAT"};
	private String[][] data = {{"P1", "", "", "", "", "", ""}, {"P2", "", "", "", "", "", ""}, {"P3", "", "", "", "", "", ""}};
	private JPanel ganttChartRootPanel;
	private GanttChart ganttChartPanel;
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
		
		// algorithm selection creation
		
		JLabel algoPanel = new JLabel("Algorithm : ");
		algoPanel.setHorizontalAlignment(SwingConstants.CENTER);
		algoPanel.setBounds(40, 40, 150, 40);
		contentPane.add(algoPanel);
		setSize(new Dimension(800, 800));

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
		tablePanel.setBackground(Color.WHITE);
		tablePanel.setBounds(40, 100, 695, 190);	 //-60
		contentPane.add(tablePanel);
		
		// controls creation
		
		addButton = new JButton("Add");
		addButton.setBounds(219, 310, 120, 40);
		contentPane.add(addButton);
		
		removeButton = new JButton("Remove");
		removeButton.setBounds(351, 310, 120, 40);
		contentPane.add(removeButton);
		
		resetButton = new JButton("Reset");
		resetButton.setBounds(483, 310, 120, 40);
		contentPane.add(resetButton);
		
		calculateButton = new JButton("Calculate");
		calculateButton.setBounds(615, 310, 120, 40);
		contentPane.add(calculateButton);
		
		// gantt chart creation
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 370, 705, 3);
		contentPane.add(separator);
		
		ganttChartRootPanel = new JPanel(new BorderLayout());
		ganttChartRootPanel.setBackground(Color.WHITE);
		ganttChartRootPanel.setBounds(40, 380, 695, 150);
		ganttChartPanel = new GanttChart();
		ganttChartPanel.setBackground(Color.WHITE);
		ganttChartRootPanel.add(ganttChartPanel, BorderLayout.CENTER);
		contentPane.add(ganttChartRootPanel);
		
		// calculation summary creation
		
		JLabel avgTATPanel = new JLabel("Average Turn Around Time :");
		avgTATPanel.setHorizontalAlignment(SwingConstants.CENTER);
		avgTATPanel.setBounds(40, 550, 175, 40);
		contentPane.add(avgTATPanel);
		
		JLabel totalTATPanel = new JLabel("Total Turn Around Time :");
		totalTATPanel.setHorizontalAlignment(SwingConstants.CENTER);
		totalTATPanel.setBounds(40, 600, 175, 40);
		contentPane.add(totalTATPanel);
		
		JLabel avgWTPanel = new JLabel("Average Waiting Time :");
		avgWTPanel.setHorizontalAlignment(SwingConstants.CENTER);
		avgWTPanel.setBounds(398, 550, 175, 40);
		contentPane.add(avgWTPanel);
		
		JLabel totalWTPanel = new JLabel("Total Waiting Time :");
		totalWTPanel.setHorizontalAlignment(SwingConstants.CENTER);
		totalWTPanel.setBounds(398, 600, 175, 40);
		contentPane.add(totalWTPanel);
		
		avgTATTxtField = new JTextField();
		avgTATTxtField.setColumns(10);
		avgTATTxtField.setBounds(227, 550, 150, 40);
		avgTATTxtField.setEditable(false);
		contentPane.add(avgTATTxtField);
		
		totalTATTxtField = new JTextField();
		totalTATTxtField.setColumns(10);
		totalTATTxtField.setBounds(227, 600, 150, 40);
		totalTATTxtField.setEditable(false);
		contentPane.add(totalTATTxtField);
		
		avgWTTxtField = new JTextField();
		avgWTTxtField.setColumns(10);
		avgWTTxtField.setBounds(585, 550, 150, 40);
		avgWTTxtField.setEditable(false);
		contentPane.add(avgWTTxtField);
		
		totalWTTxtField = new JTextField();
		totalWTTxtField.setColumns(10);
		totalWTTxtField.setBounds(585, 600, 150, 40);
		totalWTTxtField.setEditable(false);
		contentPane.add(totalWTTxtField);

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
		return ganttChartRootPanel;
	}
	
	public GanttChart getGanttChart() {
		return ganttChartPanel;
	}
	
	public void setGanttChart(List<ProcessOutput> output) {
		ganttChartPanel.setTimeline(output);;
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
