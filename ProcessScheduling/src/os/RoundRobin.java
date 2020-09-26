package os;

/*
 	Run this class for Round Robin Scheduling 
 	Prompts quantum, AT, BT and priority of processes
 	To-dos: exception handling, restrictions (havent do because this might be useful for midterm :P)
*/

import java.util.*;

public class RoundRobin {
		private static ArrayList<Process> listOfProcesses = new ArrayList<>();
		private static Queue<Process> readyQueue = new LinkedList<>();
		private static int timeCounter = 0;

		public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		int processCounter = 1;
		int quantum;
		int at, bt, priority;

		// prompting user to input, will change to passing agruments laterw
		System.out.print("Quantum => ");
		quantum = input.nextInt();
		int quantumCounter = quantum;
		System.out.println("Enter -1 to quit");

		do {
			System.out.print("Enter AT, BT, Priority for Process " + processCounter + " => ");
			at = input.nextInt();
			if(at < 0) {
				continue;
			}
			bt = input.nextInt();
			priority = input.nextInt();
			listOfProcesses.add(new Process(processCounter, at, bt, priority));
			processCounter++;

		} while(at > -1);

		input.close();

		// sorting according to priority
		Collections.sort(listOfProcesses);

		int totalTime = 0; // shortest arrival time + total burst time
		int shortest = listOfProcesses.get(1).getArrivalTime();
		for(int i = 0; i < listOfProcesses.size(); i++) {
			totalTime += listOfProcesses.get(i).getBurstTime();
			if(listOfProcesses.get(i).getArrivalTime() < shortest) {
				shortest = listOfProcesses.get(i).getArrivalTime();
			}
		}
		totalTime += shortest;

		checkArrivalTime();
		System.out.print(timeCounter + "--P" + readyQueue.peek().getProcessNumber());
		do {
			if(readyQueue.size() == 0) {
				continue;
			}
			else {
				readyQueue.peek().decreaseBurstTime(); // decrease burst time by 1
				quantumCounter--;
				timeCounter++;
				checkArrivalTime(); // check for new arrivals

				if(readyQueue.peek().getBurstTime() == 0) {
					readyQueue.remove(); // remove from queue if process finished
					quantumCounter = quantum; // reset quantumCounter
					
					// check if queue is empty 
					// for printing purpose only
					if(readyQueue.size() == 0)
						System.out.print("-" + timeCounter); // the end of the scheduling
					else
						System.out.print("-" + timeCounter + "--P" + readyQueue.peek().getProcessNumber());
				}
				else {
					if(quantumCounter == 0) {
						// time to let another process begin, put current process to end of queue
						Process p = readyQueue.peek();
						readyQueue.remove();
						readyQueue.add(p);
						quantumCounter = quantum; // reset quantumCounter
						System.out.print("-" + timeCounter + "--P" + readyQueue.peek().getProcessNumber());
					}
					else {
						// the current process continues
						System.out.print("-");
					}
				}
			}

		} while(timeCounter < totalTime);
		System.out.println();
	} 

	public static void checkArrivalTime() {
		// scan through arrival time
		for(int i = 0; i < listOfProcesses.size(); i++) {
			// equals to current time
			if (listOfProcesses.get(i).getArrivalTime() == timeCounter) {
				readyQueue.add(listOfProcesses.get(i));
			}
		}
	}
}