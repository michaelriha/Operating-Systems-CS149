import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/********************************************************************************
 *
 * ProcessFactory generates processes and puts them in a queue
 * 
 * @author Manzoor Ahmed
 * @author Igor Sorokin
 * @author Michael Riha
 * 
 * @date 06/18/13
 * @version 1.0.0
 *  
 * ******************************************************************************/

public class ProcessFactory
{
    private int processCount;
    
    public ProcessFactory(int processCount)
    {
        this.processCount = processCount;
    }

    /*
     * Create processCount random processes and add to a priority queue
     * @return q A PriorityQueue ordered with lowest arrival time first
     **/
    public Queue<Process> generateProcesses()
    {
        String names ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Use a priority queue to order processes by arrival time (IMPORTANT!!)
        Queue<Process> q = new PriorityQueue<>();

        // get random arrival, expected time, and priority
        Random randomArrival = new Random();
        Random randomPriority = new Random();        
        Random randomExpectedTime = new Random();

        // Generate new processes and add to the process queue 
        for(int i = 0; i != processCount; ++i)
        {		
            Process p = new Process();
            p.arrivalTime = randomArrival.nextFloat() * 100; 
            p.burstTime = randomExpectedTime.nextInt(10) + 1;
            p.priority = randomPriority.nextInt(4) + 1;
            p.name = names.charAt(i);
            q.add(p);
        }
        return q;
    }
}
