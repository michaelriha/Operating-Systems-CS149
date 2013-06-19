import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

/*******************************************************************
 * FirstComeFirstServed shows FCFS simulation
 * @author Manzoor Ahmed
 * @data 06/18/13
 * @version 1.0
 * *****************************************************************/

public class FirstComeFirstServed {

	Queue<Process> q;

	//needs a queue full of fresh processes
	public FirstComeFirstServed(Queue<Process> process){
		this.q= process;
	}
	
	/*
	 * simulate, shows firstcomefirstserved algorithm simulation
	 * **/
	public void simulate(){
		
		Iterator<Process> iter = q.iterator();
		while(iter.hasNext()){
			Process current = iter.next();
			
			//TODO Something is wrong with random numbers
			System.out.println(current.priority);
		}	
	}
}
