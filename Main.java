
import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

/******************************************************************************
 * This is main class which runs all the algorithms
 * @author Manzoor Ahmed
 * @version 1.0
 * @date 06/18/13
 * ***************************************************************************/

public class Main{

	public static void main(String[] args) {
		
		//generate 10 process for testing
		ProcessFactory factory = new ProcessFactory(10);
		//to hold returned processes
		Queue<Process> q = new LinkedList<Process>();
		//queue full of process
		q = factory.generateProcesses();
		
		//in progress....
		FirstComeFirstServed fcfs = new FirstComeFirstServed(q);
		//this will run first come first served simulation
		fcfs.simulate();
		
		//other simulation here....
		
		
		
		
		
		
		
		
		//print all process using iterrator
		Iterator<Process> iter= q.iterator();
		
		while(iter.hasNext()){
			//Process current = iter.next();
			//System.out.print("["+current.arrivalTime + "]->");
		}	
	}

}
