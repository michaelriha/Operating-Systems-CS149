import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/********************************************************************************
 *
 * ProcessFactory generates processes and puts them in a queue
 * 
 * @author Manzoor Ahmed
 * @author Igor Sorokin
 * @author 
 * 
 * @date 06/18/13
 * @version 1.0.0
 *  
 * ******************************************************************************/

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
		
		//TODO-- Igor, we need to change this to random char instead.
		String names ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
		//Queue to hold all newly generated process
		Queue<Process> q = new LinkedList<Process>();
		

		//rand generate process arrival time
		Random randomArrival = new Random(100);
		//randomArrival.setSeed(0);
		
		//rand generate  
		Random randomExpectedTime = new Random(10);
		//randomExpectedTime.setSeed(0);
		
		Random randomPriority = new Random(4);
		
		//how many processes do we want to create
		for(int start =0; start!=numbers; start++){
			
			//create new process
			Process p = new Process();
			
			//randomPriority.setSeed(0);
			
			//set process information, ie name, time, priority
			p.arrivalTime = randomArrival.nextFloat()+1; 
			p.expectedRunTime=randomExpectedTime.nextInt(10)+1;
			p.priority = randomPriority.nextInt(4)+1;
			
			//TODO need to fix process name 
			p.name = names.charAt(start) + Integer.toString(start);
			
			//add newly fresh generated process to the queue
			q.add(new Process());
			
			System.out.println(randomArrival.nextFloat());
		}
		
		//get the entire queue filled with fresh backed processes!
		return q;
	}
}
