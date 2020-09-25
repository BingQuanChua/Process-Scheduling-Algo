/*
 	This class is for the process object
 	Might be useful for other scheduling algorithms
*/

public class Process implements Comparable<Process> {
	private int processNumber;
	private int arrivalTime;
	private int burstTime;
	private int priority;
	private boolean finished = false;

	public Process(int process, int at, int bt, int priority) {
		this.processNumber = process;
		this.arrivalTime = at;
		this.burstTime = bt;
		this.priority = priority;
	}
	
	public int getProcessNumber() {
		return processNumber;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public int getPriority() {
		return priority;
	}

	public void decreaseBurstTime() {
		burstTime -= 1;
		if(burstTime == 0) 
			finished = true;
	}

	public boolean getFinished() {
		return finished;
	}

	public int compareTo(Process p) {
		if(this.priority > p.getPriority()) {
			return 1;
		}
		else if(this.priority < p.getPriority()) {
			return -1;
		}
		else {
			return 0;
		}
	}

	public String toString() {
		return "P" + processNumber + " AT: " + arrivalTime + " BT: " + burstTime + " Priority: " + priority;
	}
}