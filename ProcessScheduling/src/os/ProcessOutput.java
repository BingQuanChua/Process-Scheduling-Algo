package os;


public class ProcessOutput {
	
	private final String processName;
    private final int startTime;
    private int finishTime;
    
    public ProcessOutput(String processName, int startTime, int finishTime)
    {
        this.processName = processName;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
    
    //return process name
    public String getProcessName()
    {
        return processName;
    }
    
    //return starting time
    public int getStartTime()
    {
        return startTime;
    }
    
    //return finish time
    public int getFinishTime()
    {
        return finishTime;
    }
    
    //set finish time
    public void setFinishTime(int finishTime)
    {
        this.finishTime = finishTime;
    }

}

