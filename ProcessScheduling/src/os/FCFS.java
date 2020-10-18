package os;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FCFS extends CPUScheduler {
	
	private List<Process> processList;
	
	public void process() {
		
		int timeCounter = 0;
		int totalTime = 0;
		
		// sorting according to Arrival Time and Burst Time
		Collections.sort(this.getProcessInputList(), (Object o1, Object o2) -> {
            if (((Process) o1).getArrivalTime() == ((Process) o2).getArrivalTime()) {
            	// same arrival time, compare burst time
            	if (((Process) o1).getBurstTime() == ((Process) o2).getBurstTime()) {
            		return 0;
            	}
            	else if (((Process) o1).getBurstTime() < ((Process) o2).getBurstTime()) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
            else if (((Process) o1).getArrivalTime() < ((Process) o2).getArrivalTime()) {
                return -1;
            }
            else {
                return 1;
            }
            
        });
		
		// copy to new ArrayList
		processList = new ArrayList<Process>();
		
		for (Process process : this.getProcessInputList()) {
        	processList.add(new Process(process.getProcessName(), process.getArrivalTime(), process.getBurstTime(), process.getPriorityLevel()));
        	totalTime += process.getBurstTime();
        }
		
		int earliest = processList.get(0).getArrivalTime();
		timeCounter += earliest;
		totalTime += earliest;
		
		do {
			Process p = processList.get(0);
			int bt = p.getBurstTime();
			this.getProcessOutputList().add(new ProcessOutput(p.getProcessName(), timeCounter, timeCounter+=bt));
			// timeCounter += bt;
			processList.remove(0);
		} while (timeCounter < totalTime);
		
		// setting FT, TAT and WT for all the Process(es) in processInputList
		for (Process process : this.getProcessInputList()) {
			for (int i = this.getProcessOutputList().size()-1; i >= 0; i--) { // looping from the back
				if (process.getProcessName().equals(this.getProcessOutputList().get(i).getProcessName())) {
					process.setFinishTime(this.getProcessOutputList().get(i).getFinishTime());
					// setting TAT and WT 
					process.setTurnaroundTime(process.getFinishTime()-process.getArrivalTime());
					process.setWaitingTime(process.getTurnaroundTime()-process.getBurstTime());
					break;
				}
			}
		} 
	}
}
