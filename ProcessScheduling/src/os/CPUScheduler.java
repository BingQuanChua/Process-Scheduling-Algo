package os;

import java.util.ArrayList;
import java.util.List;

public abstract class CPUScheduler {
	
	private final List<ProcessInput> processInputList; // Process Input = data collected in every processInput
	private final List<ProcessOutput> processOutputList; // Process Output = result after running scheduler
	private int timeQuantum; // for round robin

	public CPUScheduler()
	{
		processInputList = new ArrayList();
		processOutputList = new ArrayList();
		timeQuantum = 1;
	}

	public boolean add(ProcessInput inputprocessInput)
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

	public double getAverageWaitingTime()
	{
		double avg = 0.0;

		for (ProcessInput processInput : processInputList)
		{
			avg += processInput.getWaitingTime();
		}

		return avg / processInputList.size();
	}

	public double getAverageTurnAroundTime()
	{
		double avg = 0.0;

		for (ProcessInput inptprocessInput : processInputList)
		{
			avg += inptprocessInput.getTurnaroundTime();
		}

		return avg / processInputList.size();
	}

	public ProcessOutput getProcessOutput(ProcessInput processInput)
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

	public ProcessInput getProcessInput(String processName)
	{
		for (ProcessInput processInput : processInputList)
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
	public List<ProcessInput> getProcessInputList()
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


