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
    
    // SJF
    public Process(String processName, int arrivalTime, int burstTime)
    {
        this(processName, arrivalTime, burstTime, 0, 0, 0);
    }
    
    
    /********** **********
     * Setter
     ********** **********/
    
    //set arrival time
    public void setArrivalTime(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }
    
    //set burst time
    public void setBurstTime(int burstTime)
    {
        this.burstTime = burstTime;
    }
    
    //set priority
    public void setPriorityLevel(int priorityLevel)
    {
        this.priorityLevel = priorityLevel;
    }
    
    //set waiting time
    public void setWaitingTime(int waitingTime)
    {
        this.waitingTime = waitingTime;
    }
    
    //set turnaround time
    public void setTurnaroundTime(int turnaroundTime)
    {
        this.turnaroundTime = turnaroundTime;
    }
    
    //set finish time
    public void setFinishTime(int finishTime) 
    {
    	this.finishTime = finishTime;
    }
    
    /********** **********
     * Getter
     ********** **********/
    
    //return process name
    public String getProcessName()
    {
        return this.processName;
    }
    
    //return arrival time
    public int getArrivalTime()
    {
        return this.arrivalTime;
    }
    
    //return burst time
    public int getBurstTime()
    {
        return this.burstTime;
    }
    
    //return priority
    public int getPriorityLevel()
    {
        return this.priorityLevel;
    }
    
    //return waiting time
    public int getWaitingTime()
    {
        return this.waitingTime;
    }
    
    //return turnaround time
    public int getTurnaroundTime()
    {
        return this.turnaroundTime;
    }
    
    //return finish time
    public int getFinishTime() 
    {
    	return this.finishTime;
    }
}
