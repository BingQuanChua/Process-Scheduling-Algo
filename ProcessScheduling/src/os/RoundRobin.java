package os;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RR extends CPUScheduler{
    
	private Queue<ProcessInput> readyQueue;
	private List<ProcessInput> processList;
	
	private int timeCounter;
	private int quantumCounter;
	private int totalTime;
	
	public void process() {
		
		readyQueue = new LinkedList<>(); // request queue initially empty
		timeCounter = 0; // current time
		quantumCounter = this.getTimeQuantum();
		totalTime = 0; // total time for all process
		
		// sorting according to Arrival Time
		Collections.sort(this.getProcessInputList(), (Object o1, Object o2) -> {
            if (((ProcessInput) o1).getArrivalTime() == ((ProcessInput) o2).getArrivalTime()) {
                return 0;
            }
            else if (((ProcessInput) o1).getArrivalTime() < ((ProcessInput) o2).getArrivalTime()) {
                return -1;
            }
            else {
                return 1;
            }
        });
		
		// copy to new ArrayList
		processList = new ArrayList<ProcessInput>(); 
        
        for (ProcessInput process : this.getProcessInputList()) {
        	processList.add(new ProcessInput(process.getProcessName(), process.getArrivalTime(), process.getBurstTime(), process.getPriorityLevel()));
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
        	
        	ProcessInput p = readyQueue.peek();
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
        			ProcessInput temp = readyQueue.peek();
        			readyQueue.remove(); // remove from the front..
        			readyQueue.add(temp); // ..add to the back
        			quantumCounter = this.getTimeQuantum(); // reset quantumCounter
        		}
        	}
        } while (timeCounter < totalTime);
        
        
        // rearrange the timeline
        for (int i = this.getProcessOutputList().size() - 1; i > 0; i--)
        {
            List<ProcessOutput> processOutputList = this.getProcessOutputList();
            
            if (processOutputList.get(i - 1).getProcessName().equals(processOutputList.get(i).getProcessName()))
            {
                processOutputList.get(i - 1).setFinishTime(processOutputList.get(i).getFinishTime());
                processOutputList.remove(i);
            }
        }
	}
	
	public void checkArrivalTime() {
		// scan through all Arrival Time
		for(ProcessInput process : processList) {
			if(process.getArrivalTime() == timeCounter) {
				readyQueue.add(process);
			}
		}
	}
}