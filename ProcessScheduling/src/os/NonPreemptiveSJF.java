import java.util.*; 
  
public class NonPreemptiveSJF{ 
  
	private static ArrayList<Process> listOfProcesses = new ArrayList<>();

	
	public static void main(String[] args) {

		int process,at, bt, priority;
		int processCounter=1;
		int starting = 0, total = 0;
	    double totalWT = 0, totalTT = 0, avgWT = 0, avgTT = 0;

		Scanner input = new Scanner(System.in);

		do {
		// prompt user input for number of process
		System.out.print("Please enter the number of process(range from 3 to 10) => ");
		process = input.nextInt();
		System.out.println("");
		} while(process < 3 || process > 10);
		
		int flag[] = new int[process];
		
			do {
				System.out.print("Enter Arrival Time for Process" + processCounter + " => ");
				at = input.nextInt();
				if(at < 0) {
					continue;
				}
				System.out.print("Enter Burst Time for Process " + processCounter + " => ");
				bt = input.nextInt();
				System.out.print("Enter Priority for Process " + processCounter + " => ");
				priority = input.nextInt();
				System.out.println("");
				listOfProcesses.add(new Process(processCounter,at, bt, priority));
				processCounter++;
			} while(at > -1 && processCounter <= process);
			
			input.close();

			int finishTime[] = new int[process];
			int waitingTime[] = new int[process];
			int turnaroundTime[] = new int[process];
			
			while(total != process) {
				int current = process; //current process
				int min = 500; 
		
				try {
				for (int j = 0; j < listOfProcesses.size(); j++) {
					if((listOfProcesses.get(j).getArrivalTime() <= starting) && (listOfProcesses.get(j).getBurstTime() < min) && (flag[j]==0)) {
						min = listOfProcesses.get(j).getBurstTime();
						current = j;
					}
				}
				}
				catch(IndexOutOfBoundsException e) {
				System.out.println("ERROR");
			}
				
				//current 
				if(current == process)
					starting++;
				
				else {
					finishTime[current] = starting + listOfProcesses.get(current).getBurstTime();
					starting += listOfProcesses.get(current).getBurstTime();
					turnaroundTime[current] = finishTime[current] - listOfProcesses.get(current).getArrivalTime();
					waitingTime[current] = turnaroundTime[current] - listOfProcesses.get(current).getBurstTime();
					flag[current] = 1;
					total++;
				}
			}

			//display summary
			System.out.println("PROCESS ARRIVAL BURST FINISHING TURNAROUND WAITING ");
			for (int k = 0;k < listOfProcesses.size();k++) {
				System.out.print("P"+k+"\t"+listOfProcesses.get(k).getArrivalTime()+"\t"+listOfProcesses.get(k).getBurstTime()
						+ "\t" + finishTime[k] + "\t" + turnaroundTime[k] + "\t   " + waitingTime[k]);
				System.out.print("\n");
			
				totalWT += waitingTime[k];
				totalTT += turnaroundTime[k];
			}
			
			//Calculate Average Waiting Time
			avgWT = totalWT/process;
			//Calculate Average Turnaround Time
			avgTT = totalTT/process;
			System.out.println("\nAverage Waiting Time : " + avgWT + "\nAverage Turnaround Time: " + avgTT);
	}
} 