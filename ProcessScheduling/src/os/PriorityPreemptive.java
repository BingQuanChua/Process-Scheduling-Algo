package os;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityPreemptive extends CPUScheduler {
	
	public void process()
    {
		/**
		 * Section 1
		 * Sort all process according to the arrival time
		 */
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
        
        List<Process> processList = new ArrayList<Process>(); //copy to processList
        
        for (Process process : this.getProcessInputList())
        {
        	processList.add(new Process(process.getProcessName(), process.getArrivalTime(), process.getBurstTime(), process.getPriorityLevel()));
        }
        
        int time = processList.get(0).getArrivalTime(); //get the arrival time of first process
        
        /**
         * Section 2
         * Priority Preemptive  
         */
        while (!processList.isEmpty())
        {
        	
            List<Process> availableProcessList = new ArrayList<Process>();
            
            for (Process process : processList)
            {
            	//if the process have arrived, add them into availableprocessList
                if (process.getArrivalTime() <= time)
                {
                	availableProcessList.add(process);
                }
            }
            
            //sort according priority
            Collections.sort(availableProcessList, (Object o1, Object o2) -> {
                if (((Process) o1).getPriorityLevel()== ((Process) o2).getPriorityLevel())
                {
                    return 0;
                }
                else if (((Process) o1).getPriorityLevel() < ((Process) o2).getPriorityLevel())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            });
            
            //get the first process
            Process process = availableProcessList.get(0);
            
            //add it into timeLine(processName, start time, finish time = (start time + 1))
            this.getProcessOutputList().add(new ProcessOutput(process.getProcessName(), time, ++time));
            
            //change the burst time
            process.setBurstTime(process.getBurstTime() - 1);
            
            //if the process already finished / burst time == 0
            if (process.getBurstTime() == 0)
            {
                for (int i = 0; i < processList.size(); i++)
                {
                    if (processList.get(i).getProcessName().equals(process.getProcessName()))
                    {
                        processList.remove(i);
                        break;
                    }
                }
            }
        }
        
        /**
         * Section 3
         * re-arrange timeline
         */
        for (int i = this.getProcessOutputList().size() - 1; i > 0; i--)
        {
            List<ProcessOutput> processOutputList = this.getProcessOutputList();
            
            if (processOutputList.get(i - 1).getProcessName().equals(processOutputList.get(i).getProcessName()))
            {
                processOutputList.get(i - 1).setFinishTime(processOutputList.get(i).getFinishTime());
                processOutputList.remove(i);
            }
        }
        
        /**
         * Section 4
         * Calculate turnaround time
         *
        
        //HashMap (key, value) a normal map container
        Map map = new HashMap();
        
        //every input processList
        for (ProcessInput process : this.getprocessList())
        {
        	//clear map
            map.clear();
            
            //every event in the timeLine
            for (Event event : this.getTimeline())
            {
            	//if the processList and timeline are the same process
                if (event.getProcessName().equals(process.getProcessName()))
                {
                	//
                    if (map.containsKey(event.getProcessName()))
                    {
                        int w = event.getStartTime() - (int) map.get(event.getProcessName());
                        process.setWaitingTime(process.getWaitingTime() + w);
                    }
                    else
                    {
                        process.setWaitingTime(event.getStartTime() - process.getArrivalTime());
                    }
                    
                    // Add key and value into the map
                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }
            
            //set the turnaround time
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
        }
        /***/
    }

}
