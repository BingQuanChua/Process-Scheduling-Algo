package os;

import java.util.List;

//Only display result in console
public class DisplayResult {
	
	DisplayResult(CPUScheduler scheduler){
		display(scheduler);
	}

	public void display(CPUScheduler scheduler) {
		
		System.out.println("\n***************\n"
						 + "DisplayResult"
						 + "\n***************");
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
		
		System.out.println("\nProcess Ended\n");

	}
}
