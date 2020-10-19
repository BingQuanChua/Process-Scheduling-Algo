package os;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PreemptiveSJF extends CPUScheduler{
	
	private List<Process> processList;
	private List<Process> arrivedList;
	
	public void process() {
		
		int timeCounter = 0;
		int totalTime = 0;
		
		// sorting according to Arrival Time and Burst Time
		Collections.sort(this.getProcessInputList(), (Object o1, Object o2) -> {
            if (((Process) o1).getArrivalTime() == ((Process) o2).getArrivalTime()) {
            	// same arrival time, compare priority
            	if (((Process) o1).getPriorityLevel()== ((Process) o2).getPriorityLevel()) {
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
		processList = new ArrayList<>();
		
		for (Process process : this.getProcessInputList()) {
        	processList.add(new Process(process.getProcessName(), process.getArrivalTime(), process.getBurstTime(), process.getPriorityLevel()));
        	totalTime += process.getBurstTime();
        }
		
		arrivedList = new ArrayList<>();
		
		int earliest = processList.get(0).getArrivalTime();
		timeCounter += earliest;
		totalTime += earliest;
		
		
		do {
			getArrivedProcess(timeCounter);
			if (arrivedList.size() == 0) {
				this.getProcessOutputList().add(new ProcessOutput("--", timeCounter, ++timeCounter));
				continue;
			}
			
			Process p = getArrivedProcessWithLowestBT();
			p.setBurstTime(p.getBurstTime()-1);
			this.getProcessOutputList().add(new ProcessOutput(p.getProcessName(), timeCounter, ++timeCounter));
			if (p.getBurstTime() == 0) {
				arrivedList.remove(p);
			}
			
		} while (timeCounter < totalTime);
        
        // rearrange the timeline
        for (int i = this.getProcessOutputList().size() - 1; i > 0; i--) {
            List<ProcessOutput> processOutputList = this.getProcessOutputList();
            
            if (processOutputList.get(i - 1).getProcessName().equals(processOutputList.get(i).getProcessName())) {
                processOutputList.get(i - 1).setFinishTime(processOutputList.get(i).getFinishTime());
                processOutputList.remove(i);
            }
		}
		
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
	
	public void getArrivedProcess(int currentTime) {
		for (Process p : processList) {
			if (p.getArrivalTime() == currentTime) {
				arrivedList.add(p);
			}
		}
	}
	
	public Process getArrivedProcessWithLowestBT() {
		Process low = arrivedList.get(0);
		for (Process p : arrivedList) {
			if (p.getBurstTime() < low.getBurstTime()) {
				low = p;
			}
		}
		return low;
	}
}
