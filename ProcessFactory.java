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
    private int numbers; // numbers of process we need to generate

    public ProcessFactory(int numbers)
    {
        this.numbers = numbers;
    }

    /*
     * generateProcesses creates process with name, arrival time,
     * expected run time, priority and shoves them in queue.
     * @return q, queue full of processes
     * **/

    public Queue<Process> generateProcesses()
    {
        //TODO-- Igor, we need to change this to random char instead.
        // ^^ The process names do not have to be random. - Michael
        String names ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Use a priority queue to order processes by arrival time (IMPORTANT!)
        Queue<Process> q = new PriorityQueue<>();

        // get random arrival, expected time, and priority
        Random randomArrival = new Random(100);
        Random randomExpectedTime = new Random(10);
        Random randomPriority = new Random(4);
        //randomArrival.setSeed(0);
        //randomExpectedTime.setSeed(0);		

        for(int start = 0; start != numbers; ++start)
        {		
            Process p = new Process();

            p.arrivalTime = randomArrival.nextFloat()+1; 
            p.burstTime = randomExpectedTime.nextInt(10)+1;
            p.priority = randomPriority.nextInt(4)+1;

            //TODO need to fix process name -- Should just be a letter 
            p.name = names.charAt(start);
            q.add(p);

            System.out.println(randomArrival.nextFloat());
        }
        return q;
    }
}
