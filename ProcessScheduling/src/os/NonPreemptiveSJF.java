package os;

import java.util.*; 
  
public class NonPreemptiveSJF extends CPUScheduler{ 
  
	private List<Process> processList;
	
	public void process() {
		//new ArrayList
		processList = new ArrayList<Process>();
		//declare the variables
		int starting = 0, total = 0;
	    double totalWT = 0, totalTT = 0, avgWT = 0, avgTT = 0;
	    
	    // sorting according to Arrival Time
	    Collections.sort(this.getProcessInputList(), (Object o1, Object o2) -> {
            if (((Process) o1).getArrivalTime() == ((Process) o2).getArrivalTime())
            {
                return 0;
            }
            else if (((Process) o1).getArrivalTime() < ((Process) o2).getArrivalTime())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        });

		for (Process process : this.getProcessInputList()) {
				processList.add(new Process(process.getProcessName(),process.getArrivalTime(),process.getBurstTime()));
		}
			int flag[] = new int[processList.size()];//checks if process is completed or not
			int finishTime[] = new int[processList.size()];
			int waitingTime[] = new int[processList.size()];
			int turnaroundTime[] = new int[processList.size()];
			
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
					turnaroundTime[current] = finishTime[current] - processList.get(current).getArrivalTime();//calculate Turnaround Time
					waitingTime[current] = turnaroundTime[current] - processList.get(current).getBurstTime();//calculate Waiting Time
					flag[current] = 1;
					total++;
					Process p = processList.get(current);
					this.getProcessOutputList().add(new ProcessOutput(p.getProcessName(), starting, finishTime[current]));
					starting += processList.get(current).getBurstTime();
				}
			}
			// rearrange the timeline
			for (int i = 0; i < processList.size(); i++)
	        {
				Process p = processList.get(i);
	            if (processList.get(i).getProcessName().equals(p.getProcessName()))
	            {
	            	processList.remove(i);
	                break;
	            }
	        }
			//display summary
			/*System.out.println("PROCESS ARRIVAL BURST FINISHING TURNAROUND WAITING ");
			for (int k = 0;k < processList.size();k++) {
				System.out.print("P"+k+"\t"+processList.get(k).getArrivalTime()+"\t"+processList.get(k).getBurstTime()
						+ "\t" + finishTime[k] + "\t" + turnaroundTime[k] + "\t   " + waitingTime[k]);
				System.out.print("\n");
			
				totalWT += waitingTime[k];
				totalTT += turnaroundTime[k];
			}
			
			//Calculate Average Waiting Time
			avgWT = totalWT/processList.size();
			//Calculate Average Turnaround Time
			avgTT = totalTT/processList.size();
			System.out.println("\nAverage Waiting Time : " + avgWT + "\nAverage Turnaround Time: " + avgTT);*/
	}
} 