import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProcessFactory {

	private int numbers; // numbers of process we need to generate

	public ProcessFactory(int numbers){
		this.numbers = numbers;
	}
	
	/*
	 * generateProcesses creates process with name, arrival time,
	 * expected run time, priority and shoves them in queue.
	 * @return q, queue full of processes
	 * **/
	
	public Queue<Process> generateProcesses(){
		
		String names ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		//Queue to hold all newly generated process
		Queue<Process> q = new LinkedList<Process>();
		
		for(int start =0; start!=numbers; start++){
			
			Process p = new Process();
			
			Random randomArrival = new Random(3);
			randomArrival.setSeed(0);
			Random randomExpectedTime = new Random(1);
			randomExpectedTime.setSeed(0);
			Random randomPriority = new Random(4);
			randomPriority.setSeed(0);
			
			p.arrivalTime = randomArrival.nextFloat(); 
			p.expectedRunTime=randomExpectedTime.nextInt();
			p.priority = randomPriority.nextInt();
			p.name = names.charAt(start) + Integer.toString(start);
			
			q.add(new Process());
		}
		return q;
	}
}
