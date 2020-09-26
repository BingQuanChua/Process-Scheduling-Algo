package os;

import java.util.List;

public class TempMain {
	
	public static void main(String[] args) {
		
		psp();
		
	}

	 public static void psp()
	 {
		 CPUScheduler psp = new NonPreemptiveSJF();
		 psp.add(new Process("P1", 1, 3));
		 psp.add(new Process("P2", 1, 5));
		 psp.add(new Process("P3", 3, 3));
		 psp.add(new Process("P4", 3, 3));
		 psp.add(new Process("P5", 5, 4));
	
		 psp.process();
		 display(psp);
	 }
	 
	 public static void display(CPUScheduler scheduler) {
		 
		 System.out.println("Process\tAT\tBT\tWT\tTAT");

	        for (Process process : scheduler.getProcessInputList())
	        {
	            System.out.println(process.getProcessName() + "\t" + process.getArrivalTime() + "\t" + process.getBurstTime() + "\t" + process.getWaitingTime() + "\t" + process.getTurnaroundTime());
	        }
	        
	        System.out.println();
	        
	        for (int i = 0; i < scheduler.getProcessOutputList().size(); i++)
	        {
	            List<ProcessOutput> processOutput = scheduler.getProcessOutputList();
	            System.out.print(processOutput.get(i).getStartTime() + "\n| " + processOutput.get(i).getProcessName() + "\n");
	            
	            if (i == scheduler.getProcessOutputList().size() - 1)
	            {
	                System.out.print(processOutput.get(i).getFinishTime());
	            }
	        }
		 
	 }
}
