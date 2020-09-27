package os;

import java.util.ArrayList;
import java.util.List;

public abstract class CPUScheduler {
	
	private final List<Process> processInputList; // Process Input = data collected in every processInput
	private final List<ProcessOutput> processOutputList; // Process Output = result after running scheduler
	private int timeQuantum; // for round robin

	public CPUScheduler()
	{
		processInputList = new ArrayList<>();
		processOutputList = new ArrayList<>();
		timeQuantum = 3;
	}

	public boolean add(Process inputprocessInput)
	{
		return processInputList.add(inputprocessInput);
	}

	public void setTimeQuantum(int timeQuantum)
	{
		this.timeQuantum = timeQuantum;
	}
	
	
	/********** **********
	 * Getter
	 ********** **********/
	public int getTimeQuantum()
	{
		return timeQuantum;
	}
	
	public int getTotalWaitingTime() 
	{
		int total = 0;
		
		for (Process processInput : processInputList) 
		{
			total += processInput.getWaitingTime();
		}
		
		return total;
	}
	
	public int getTotalTurnAroundTime() 
	{
		int total = 0;
		
		for (Process processInput : processInputList) 
		{
			total += processInput.getTurnaroundTime();
		}
		
		return total;
	}

	public double getAverageWaitingTime()
	{
		double avg = 0.0;

		for (Process processInput : processInputList)
		{
			avg += processInput.getWaitingTime();
		}

		return avg / processInputList.size();
	}

	public double getAverageTurnAroundTime()
	{
		double avg = 0.0;

		for (Process inptprocessInput : processInputList)
		{
			avg += inptprocessInput.getTurnaroundTime();
		}

		return avg / processInputList.size();
	}

	public ProcessOutput getProcessOutput(Process processInput)
	{
		for (ProcessOutput processOutput : processOutputList)
		{
			if (processInput.getProcessName().equals(processOutput.getProcessName()))
			{
				return processOutput;
			}
		}

		return null;
	}

	public Process getProcessInput(String processName)
	{
		for (Process processInput : processInputList)
		{
			if (processInput.getProcessName().equals(processName))
			{
				return processInput;
			}
		}

		return null;
	}

	
	/********** **********
	 * Get List
	 ********** **********/
	public List<Process> getProcessInputList()
	{
		return processInputList;
	}

	public List<ProcessOutput> getProcessOutputList()
	{
		return processOutputList;
	}

	/********** **********
	 * Process algorithm
	 ********** **********/
	//Depends on the process name
	public abstract void process();
}


