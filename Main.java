
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Iterator;

public class Main{

	public static void main(String[] args) {
		
		ProcessFactory factory = new ProcessFactory(10);
		Queue<Process> q = new LinkedList<Process>();
		q = factory.generateProcesses();
		
		Iterator<Process> iter= q.iterator();
		
		
		
	}

}
