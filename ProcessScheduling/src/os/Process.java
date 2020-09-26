package os;


public class Process
{
    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int priorityLevel;
    private int waitingTime;
    private int turnaroundTime;
    private int finishTime;
    
    /********** **********
     * Constructor
     ********** **********/
    private Process(String processName, int arrivalTime, int burstTime, int priorityLevel, int waitingTime, int turnaroundTime)
    {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityLevel = priorityLevel;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
    }
    
    // Priority non-preemptive or priority preemptive
    public Process(String processName, int arrivalTime, int burstTime, int priorityLevel)
    {
        this(processName, arrivalTime, burstTime, priorityLevel, 0, 0);
    }
    
    // FCFS or SJF
    public Process(String processName, int arrivalTime, int burstTime)
    {
        this(processName, arrivalTime, burstTime, 0, 0, 0);
    }
    
    
    /********** **********
     * Setter
     ********** **********/
    public void setArrivalTime(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }
    
    
    public void setBurstTime(int burstTime)
    {
        this.burstTime = burstTime;
    }
    
    public void setPriorityLevel(int priorityLevel)
    {
        this.priorityLevel = priorityLevel;
    }
    
    public void setWaitingTime(int waitingTime)
    {
        this.waitingTime = waitingTime;
    }
    
    public void setTurnaroundTime(int turnaroundTime)
    {
        this.turnaroundTime = turnaroundTime;
    }
    
    public void setFinishTime(int finishTime) 
    {
    	this.finishTime = finishTime;
    }
    
    /********** **********
     * Getter
     ********** **********/
    public String getProcessName()
    {
        return this.processName;
    }
    
    public int getArrivalTime()
    {
        return this.arrivalTime;
    }
    
    public int getBurstTime()
    {
        return this.burstTime;
    }
    
    public int getPriorityLevel()
    {
        return this.priorityLevel;
    }
    
    public int getWaitingTime()
    {
        return this.waitingTime;
    }
    
    public int getTurnaroundTime()
    {
        return this.turnaroundTime;
    }
    
    public int getFinishTime() 
    {
    	return this.finishTime;
    }
}
