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
    /*
     * Create processCount random processes and add to a priority queue
     * @return q A PriorityQueue ordered with lowest arrival time first
     **/
    public static PriorityQueue<Process> generateProcesses(int processCount)
    {
        String names ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Use a priority queue to order processes by arrival time (IMPORTANT!!)
        PriorityQueue<Process> q = new PriorityQueue<>();

        // get random arrival, expected time, and priority
        Random randomArrival = new Random();
        Random randomPriority = new Random();        
        Random randomExpectedTime = new Random();

        // Generate new processes and add to the process queue 
        for(int i = 0; i != processCount; ++i)
        {		
            Process p = new Process();
            p.setArrivalTime(randomArrival.nextFloat() * 100); 
            p.setBurstTime(randomExpectedTime.nextInt(10) + 1);
            p.setPriority(randomPriority.nextInt(4) + 1);
            p.setName(names.charAt(i));
            q.add(p);
        }
        return q;
    }
}
