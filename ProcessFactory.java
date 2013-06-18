import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/*
 * ProcessFactory generates processes
 * @author Manzoor Ahmed, Igor Sorokin
 * @date 06/18/13
 * @version 1.0.0
 *  
 * **/
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
		//process name
		String names ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		//Queue to hold all newly generated process
		Queue<Process> q = new LinkedList<Process>();
		
		for(int start =0; start!=numbers; start++){
			
			//create new process
			Process p = new Process();
			
			//rand generate process arrival time
			Random randomArrival = new Random(3);
			//randomArrival.setSeed(0);
			
			//rand generate  
			Random randomExpectedTime = new Random(1);
			//randomExpectedTime.setSeed(0);
			
			Random randomPriority = new Random(4);
			//randomPriority.setSeed(0);
			
			//set process information, ie name, time, priority
			p.arrivalTime = randomArrival.nextFloat(); 
			p.expectedRunTime=randomExpectedTime.nextInt();
			p.priority = randomPriority.nextInt();
			
			//TODO need to fix process name 
			p.name = names.charAt(start) + Integer.toString(start);
			//add newly fresh generated process to the queue
			q.add(new Process());
		}
		
		//get the entire queue
		return q;
	}
}
