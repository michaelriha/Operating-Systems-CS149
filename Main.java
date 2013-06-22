import java.util.ArrayList;
import java.util.Queue;
import java.util.Iterator;
import java.util.PriorityQueue;

/******************************************************************************
 * This is main class which runs all the algorithms
 * @author Manzoor Ahmed
 * @author Michael Riha
 * @version 1.0
 * @date 06/18/13
 * ***************************************************************************/

public class Main
{
    private static final int SIMULATION_RUNS = 5;
    private static final int PROCESSES_PER_RUN = 20;
    private static final int ALGORITHM_COUNT = 6;
    
    public static void main(String[] args) throws CloneNotSupportedException 
    {                
        // Create a scheduler for each scheduling algorithm
        Scheduler fcfs = new FirstComeFirstServed();
        //Scheduler phpf = new PreemptiveHighestPriorityFirst();
        Scheduler nhpf = new NonpreemptiveHighestPriorityFirst();
        Scheduler sjf = new ShortestJobFirst();
        //Scheduler srt = new ShortestRemainingTime();
        Scheduler rrna = new RoundRobinExtraCreditNoAging();

        // Hold duplicated process queues for each algorithm to use
        PriorityQueue<Process>[] q = new PriorityQueue[ALGORITHM_COUNT + 1];
        q = (PriorityQueue<Process>[]) q;
        
        // Test each scheduling algorithm SIMULATION_RUNS times
        for (int i = 0; i < SIMULATION_RUNS; ++i)
        {
            System.out.format("\nScheduling Process Queue %d\n\n", i + 1);
            
            //generate a new process queue for this testing round then duplicate it
            q[0] = ProcessFactory.generateProcesses(PROCESSES_PER_RUN);
            for (int j = 1; j < ALGORITHM_COUNT + 1; ++j)
                q[j] = copyQueue(q[0]);
            
            // Print the process list by ascending arrival time   
            while (!q[ALGORITHM_COUNT].isEmpty())
                System.out.println(q[ALGORITHM_COUNT].poll());
                        
            // Run each scheduling algorithm and show the results
            System.out.print("\nFCFS: ");
            fcfs.schedule(q[0]);
            
            System.out.print("\nSJF:  ");
            sjf.schedule(q[1]);
            
            System.out.print("\nNHPF: ");
            nhpf.schedule(q[2]);
            
//            System.out.print("\nPHPF: ");
//            phpf.schedule(q);

//            System.out.print("\nSRT:  ");
//            srt.schedule(q);

            System.out.print("\nRRNA:   ");
            rrna.schedule(q[5]);
        }
        
        System.out.println("\nAverage Statistics by Scheduling Algorithm:");

        System.out.println("\nFirst Come First Served");
        fcfs.printAvgStats();
//
//        System.out.println("Preemptive Highest Priority First");
//        printAvgStats(phpf);
//
        System.out.println("\nNonpreemptive Highest Priority First");
        nhpf.printAvgStats();

        System.out.println("\nShortest Job First");
        sjf.printAvgStats();

//        System.out.println("Shortest Runtime");
//        printAvgStats(srt);
//
        System.out.println("\nRound Robin Extra Credit No Aging (Same as NHPF)");
        rrna.printAvgStats();
    }
    
    private static PriorityQueue<Process> copyQueue(PriorityQueue<Process> q) throws CloneNotSupportedException
    {        
        PriorityQueue<Process> qcopy = new PriorityQueue<>();
        ArrayList<Process> qoriginal = new ArrayList<>();
        while (!q.isEmpty())
        {
            Process p = q.poll();
            qcopy.add((Process) p.clone());
            qoriginal.add(p);
        }
        q.addAll(qoriginal);
        return qcopy;
    }
}
