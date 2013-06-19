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
    private static final int PROCESSES_PER_RUN = 10;
    
    public static void main(String[] args) 
    {                
        // Create a scheduler for each scheduling algorithm
        Scheduler fcfs = new FirstComeFirstServed();
        Scheduler phpf = new PreemptiveHighestPriorityFirst();
        Scheduler nhpf = new NonpreemptiveHighestPriorityFirst();
        Scheduler sjf = new ShortestJobFirst();
        Scheduler srt = new ShortestRemainingTime();
        Scheduler rr = new RoundRobin();

        // Store results and a randomly generated process queue
        Queue<Process> results;
        PriorityQueue<Process> q;
        
        // Test each scheduling algorithm SIMULATION_RUNS times
        for (int i = 0; i < SIMULATION_RUNS; ++i)
        {
            System.out.format("\nScheduling Process Queue %d\n\n", i + 1);
            
            //generate a new process queue for this testing round
            q = new ProcessFactory(PROCESSES_PER_RUN).generateProcesses();
            
            // BEGIN TESTING CODE
            PriorityQueue<Process> qcopy = new PriorityQueue<>();
            int queueSize = q.size();
            for (int j = 0; j < queueSize; ++j)
            {
                System.out.println(q.peek().toString());
                qcopy.add(q.poll());
            }
            System.out.println();
            q = qcopy;
            // END TESTING CODE
            
            
            // Run each scheduling algorithm and show the results
            System.out.print("FCFS: ");
            results = fcfs.schedule(q);
            printTimeChart(results);

//            System.out.print("SJF: ");
//            results = sjf.schedule(q);
//            printTimeChart(results);
//
//            System.out.print("Nonpreemptive HPF: ");
//            results = nhpf.schedule(q);
//            printTimeChart(results);
//
//            System.out.print("Preemptive HPF: ");
//            results = phpf.schedule(q);
//            printTimeChart(results);
//
//            System.out.print("SRT: ");
//            results = srt.schedule(q);
//            printTimeChart(results);
//
//            System.out.print("RR: ");
//            results = rr.schedule(q);
//            printTimeChart(results);
        }
        
        System.out.println("\nAverage Statistics by Scheduling Algorithm:\n");

        System.out.println("First Come First Served");
        printAvgStats(fcfs);
//
//        System.out.println("Preemptive Highest Priority First");
//        printAvgStats(phpf);
//
//        System.out.println("Nonpreemptive Highest Priority First");
//        printAvgStats(nhpf);
//
//        System.out.println("Shortest Job First");
//        printAvgStats(sjf);
//
//        System.out.println("Shortest Runtime");
//        printAvgStats(srt);
//
//        System.out.println("Round Robin");
//        printAvgStats(rr);
    }

    /**
     * Print a "time chart" of the results, e.g. ABCDABCD...
     * @param results A list of Processes that have been scheduled
     */
    public static void printTimeChart(Queue<Process> q)
    {
        int quanta = 0;
        for (Process p : q)
        {
            while (quanta++ < p.startTime) // show idle time
                System.out.print("_");
            
            for (int i = 0; i < p.burstTime; ++i)
                System.out.print(p.name);
        }
        System.out.println();
    }

    /**
     * Print out the average statistics for the given scheduling algorithm
     * @param s A Scheduler object that has its Stats subclass filled with data
     */
    public static void printAvgStats(Scheduler s)
    {
        Scheduler.Stats stats = s.getStats();
        System.out.format("Average turnaround time: %f\n", stats.getAvgTurnaroundTime());
        System.out.format("Average waiting time: %f\n", stats.getAvgWaitingTime());
        System.out.format("Average response time: %f\n", stats.getAvgResponseTime());
        System.out.format("Average throughput per 100 quanta: %f\n", stats.getAvgThroughput());
    }
}
