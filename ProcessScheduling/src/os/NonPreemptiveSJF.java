package os;

import java.util.*;
  
public class NonPreemptiveSJF extends CPUScheduler{ 
  
	private List<Process> processList;
	
	public void process() {
		//new ArrayList
		processList = new ArrayList<Process>();
		//declare the variables
		int starting = 0, total = 0;
	    
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

		for (Process process : this.getProcessInputList()) {
				processList.add(new Process(process.getProcessName(),process.getArrivalTime(),process.getBurstTime()));
		}
			int flag[] = new int[processList.size()];//checks if process is completed or not
			int finishTime[] = new int[processList.size()];
			
			while(total != processList.size()) {
				int current = processList.size(); //current process
				int min = 500; 
		
				try {
					for (int j = 0; j < processList.size(); j++) {
						if((processList.get(j).getArrivalTime() <= starting) && (processList.get(j).getBurstTime() < min) && (flag[j]==0)) {
							min = processList.get(j).getBurstTime();
							current = j;
						}
					}
				}
				catch(IndexOutOfBoundsException e) {
					System.out.println("ERROR");
				}
				
				//current 
				if(current == processList.size())
					starting++;
				
				else {
					finishTime[current] = starting + processList.get(current).getBurstTime();//calculate finishTime
					flag[current] = 1;
					total++;
					Process p = processList.get(current);
					this.getProcessOutputList().add(new ProcessOutput(p.getProcessName(), starting, finishTime[current]));
					starting += processList.get(current).getBurstTime();
				}
			}
			
			//calculate turnaround time and waiting time
			for (Process process : this.getProcessInputList()) {
				for (int i = this.getProcessOutputList().size()-1; i >= 0; i--) { // looping from the back
					if (process.getProcessName().equals(this.getProcessOutputList().get(i).getProcessName())) {
						int ft = this.getProcessOutputList().get(i).getFinishTime();
						process.setFinishTime(ft);
						//set turnaround time and waiting time 
						process.setTurnaroundTime(ft-process.getArrivalTime());
						process.setWaitingTime(process.getTurnaroundTime()-process.getBurstTime());
						break;
					}
				}
			} 	
	}
} 