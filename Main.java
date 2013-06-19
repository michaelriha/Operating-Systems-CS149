import java.util.Queue;
import java.util.Iterator;

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
        Queue<Process> q;

        for (int i = 0; i < SIMULATION_RUNS; ++i)
        {
            System.out.format("Scheduling Process Queue %d\n", i);
            
            //generate a new process queue for this testing round
            q = new ProcessFactory(10).generateProcesses();

            // Run each scheduling algorithm and show the results
            System.out.print("First Come First Served: ");
            results = fcfs.schedule(q);
            printTimeChart(results);

            System.out.print("Shortest Job First: ");
            results = sjf.schedule(q);
            printTimeChart(results);

            System.out.print("Nonpreemptive Highest Priority First: ");
            results = nhpf.schedule(q);
            printTimeChart(results);

            System.out.print("Preemptive Highest Priority First: ");
            results = phpf.schedule(q);
            printTimeChart(results);

            System.out.print("Shortest Runtime: ");
            results = srt.schedule(q);
            printTimeChart(results);

            System.out.print("Round Robin: ");
            results = rr.schedule(q);
            printTimeChart(results);
        }

        System.out.println("Average Statistics by Scheduling Algorithm: ");

        System.out.println("First Come First Served");
        printAvgStats(fcfs);

        System.out.println("Preemptive Highest Priority First");
        printAvgStats(phpf);

        System.out.println("Nonpreemptive Highest Priority First");
        printAvgStats(nhpf);

        System.out.println("Shortest Job First");
        printAvgStats(sjf);

        System.out.println("Shortest Runtime");
        printAvgStats(srt);

        System.out.println("Round Robin");
        printAvgStats(rr);
    }

    /**
     * Print a "time chart" of the results, e.g. ABCDABCD...
     * @param results A list of Processes that have been scheduled
     */
    public static void printTimeChart(Queue<Process> results)
    {
        for (Process p : results)
        {
            for (int i = 0; i < p.getBurstTime(); ++i)
            System.out.print(p.getName());
        }
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
        System.out.format("Average throughput: %f\n", stats.getAvgThroughput());
    }
}
