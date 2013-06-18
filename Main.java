
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Iterator;

/*
 * 
 * **/

public class Main{

	public static void main(String[] args) {
		
		//generate 10 process for testing
		ProcessFactory factory = new ProcessFactory(10);
		//to hold returned processes
		Queue<Process> q = new LinkedList<Process>();
		//queue full of process
		q = factory.generateProcesses();
		
		//print all process using iterrator
		Iterator<Process> iter= q.iterator();
		
		
		
	}

}
