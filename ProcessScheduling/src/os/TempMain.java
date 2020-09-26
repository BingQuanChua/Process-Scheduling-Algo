package os;

import java.util.List;

public class TempMain {
	
	public static void main(String[] args) {
		
		psp();
		
	}

	 public static void psp()
	 {
		 CPUScheduler psp = new PriorityPreemptive();
		 psp.add(new ProcessInput("P1", 8, 1));
		 psp.add(new ProcessInput("P2", 5, 1));
		 psp.add(new ProcessInput("P3", 2, 7));
		 psp.add(new ProcessInput("P4", 4, 3));
		 psp.add(new ProcessInput("P5", 2, 8));
		 psp.add(new ProcessInput("P6", 4, 2));
		 psp.add(new ProcessInput("P7", 3, 5));
		 psp.process();
		 display(psp);
	 }
	 
	 public static void display(CPUScheduler scheduler) {
		 
		 System.out.println("Process\tAT\tBT\tWT\tTAT");

	        for (ProcessInput process : scheduler.getProcessInputList())
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
