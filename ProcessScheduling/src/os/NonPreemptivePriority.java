package os;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NonPreemptivePriority extends CPUScheduler {
	
	private List<Process> processList;
	
	public void process() {

		int timeCounter = 0;
		int totalTime = 0;
		
		// sorting according to Arrival Time and Priority
		Collections.sort(this.getProcessInputList(), (Object o1, Object o2) -> {
            if (((Process) o1).getArrivalTime() == ((Process) o2).getArrivalTime()) {
            	// same arrival time, compare priority
            	if (((Process) o1).getPriorityLevel()== ((Process) o2).getPriorityLevel()) {
            		return 0;
            	}
            	else if (((Process) o1).getPriorityLevel() < ((Process) o2).getPriorityLevel()) {
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
		        
        int earliest = processList.get(0).getArrivalTime();  // earliest arrival time
        timeCounter += earliest;
        totalTime += earliest;
        
        do {
        	// to ensure
        	Process p = processList.get(0);
			if (p.getArrivalTime() > timeCounter) {
				this.getProcessOutputList().add(new ProcessOutput("--", timeCounter, p.getArrivalTime()));
				timeCounter = p.getArrivalTime();
			}
			
        	// get current process with highest priority
        	p = getCurrentHighestPriority(timeCounter);	
        	
			int bt = p.getBurstTime();
			this.getProcessOutputList().add(new ProcessOutput(p.getProcessName(), timeCounter, timeCounter+=bt));
			processList.remove(p);
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
	public Process getCurrentHighestPriority(int currentTime) {
 		ArrayList<Process> tempArrivedProcess = new ArrayList<>();
 		
 		// get all arrived processes
 		for (Process p : processList) {
 			if (p.getArrivalTime() <= currentTime) {
 				tempArrivedProcess.add(p);
 			}
 		}
 		
 		// selects the process with highest priority
 		Process top = tempArrivedProcess.get(0);
 		for (Process p : tempArrivedProcess) {
 			if (p.getPriorityLevel() < top.getPriorityLevel()) {
 				top = p;
 			}
 		}
 		
 		return top;
 	}
}
