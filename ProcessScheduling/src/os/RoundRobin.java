package os;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin extends CPUScheduler{
    
	private Queue<Process> readyQueue;
	private List<Process> processList;
	
	private int timeCounter;
	private int quantumCounter;
	private int totalTime;
	
	public void process() {	
		
		readyQueue = new LinkedList<>(); // request queue initially empty
		timeCounter = 0; // current time
		quantumCounter = this.getTimeQuantum();
		totalTime = 0; // total time for all process
		
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
        
        int earliest = processList.get(0).getArrivalTime();  // earliest arrival time
        timeCounter += earliest;
        totalTime += earliest;
        checkArrivalTime();
        
        do {
        	if (readyQueue.size() == 0) {
        		continue; // skips, I doubt will happen but just in case
        	}
        	
        	Process p = readyQueue.peek();
        	p.setBurstTime(p.getBurstTime()-1);
        	quantumCounter--;
        	this.getProcessOutputList().add(new ProcessOutput(p.getProcessName(), timeCounter, ++timeCounter));
        	
        	checkArrivalTime(); // check for new arrivals
        	if(readyQueue.peek().getBurstTime() == 0) {
        		readyQueue.remove(); // remove from queue if process finished
        		quantumCounter = this.getTimeQuantum(); // reset quantumCounter
        	}
        	else {
        		if(quantumCounter == 0) {
        			Process temp = readyQueue.peek();
        			readyQueue.remove(); // remove from the front..
        			readyQueue.add(temp); // ..add to the back
        			quantumCounter = this.getTimeQuantum(); // reset quantumCounter
        		}
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
	
	public void checkArrivalTime() {
		// scan through all Arrival Time
		for(Process process : processList) {
			if(process.getArrivalTime() == timeCounter) {
				readyQueue.add(process);
			}
		}
	}
}