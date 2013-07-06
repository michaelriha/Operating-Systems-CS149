import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Controls the scheduling algorithm simulation for each algorithm and their derivatives
 * @author Michael Riha
 */

public class Main
{
    private static final int SIMULATION_RUNS = 5;
    private static final int MAX_PROCESSES_PER_RUN = 20;
    private static final int ALGORITHM_COUNT = 11;
    
    public static void main(String[] args) throws CloneNotSupportedException 
    {                
        // Create a scheduler for each scheduling algorithm
        Scheduler fcfs = new FirstComeFirstServed();
        Scheduler pfcfs = new PreemptiveFirstComeFirstServedNoAging();
        
        // nonpreemptive fcfs with priority is the same as nonpreemptive HPF
        Scheduler nfcfs = new NonpreemptiveHighestPriorityFirstNoAging(); 
        Scheduler nhpf = new NonpreemptiveHighestPriorityFirstNoAging();
        
        // round robin + priority is the same as preemptive HPF
        Scheduler phpf = new PreemptiveHighestPriorityFirstNoAging();
        Scheduler rrna = new PreemptiveHighestPriorityFirstNoAging();
        
        Scheduler sjf = new ShortestJobFirst();
        Scheduler sjfna = new ShortestJobFirstNoAging();
        Scheduler rr = new RoundRobin();
        Scheduler srt = new ShortestRemainingTime();
        Scheduler srtna = new ShortestRemainingTimeNoAging();

        // Hold duplicated process queues for each algorithm to use
        PriorityQueue<Process>[] q = new PriorityQueue[ALGORITHM_COUNT + 1];
        q = (PriorityQueue<Process>[]) q;
        
        // Test each scheduling algorithm SIMULATION_RUNS times
        for (int i = 0; i < SIMULATION_RUNS; ++i)
        {
            System.out.println("---------------------------");
            System.out.format("Scheduling Process Queue %d:\n", i + 1);
            System.out.println("---------------------------");
            
            //generate a new process queue for this testing round then duplicate it
            q[0] = ProcessFactory.generateProcesses(MAX_PROCESSES_PER_RUN);
            for (int j = 1; j < ALGORITHM_COUNT + 1; ++j)
                q[j] = copyQueue(q[0]);
            
            // Print the process list by ascending arrival time   
            while (!q[ALGORITHM_COUNT].isEmpty())
                System.out.println(q[ALGORITHM_COUNT].poll());
                        
            // Run each scheduling algorithm and show the results
            
            System.out.println("\n---------------------------");
            System.out.println("Non-Extra Credit Algorithms");
            System.out.println("---------------------------");
            System.out.println("\nFirst Come First Served");
            fcfs.schedule(q[0]);
            
            System.out.println("\nShortest Job First");
            sjf.schedule(q[1]);
            
            System.out.println("\nShortest Remaining Time");
            srt.schedule(q[2]);
            
            System.out.println("\nNon-Preemptive Highest Priority First");
            nhpf.schedule(q[3]);
            
            System.out.println("\nPreemptive Highest Priority First  (RR switching between same priority processes)");
            phpf.schedule(q[4]);
            
            System.out.println("\nRound Robin");
            rr.schedule(q[5]);            
            
            System.out.println("\n------------------------------------------------------");
            System.out.println("Extra Credit Algorithms (Added Priority Without Aging)");
            System.out.println("------------------------------------------------------");
            
            System.out.println("\nNon-Preemptive First Come First Served (Same algorithm as Non-preemptive HPF)");
            nfcfs.schedule(q[6]);
            
            System.out.println("\nPreemptive First Come First Served (Same algorithm as Preemptive HPF without RR");
            pfcfs.schedule(q[7]);
            
            System.out.println("\nRound Robin (Same algorithm as Preemptive HPF)");
            rrna.schedule(q[8]);
            
            System.out.println("\nShortest Remaining Time");
            srtna.schedule(q[9]);
            
            System.out.println("\nShortest Job First");
            sjfna.schedule(q[10]);                   
            
            System.out.println("\nNon-Preemptive and Preemptive Highest Priority First");
            System.out.println("    See above -- same algorithms as non-extra credit versions\n");
        }
        System.out.println("\n-------------------------------------------");
        System.out.println("Average Statistics by Scheduling Algorithm");
        System.out.println("-------------------------------------------");

        System.out.println("\n---------------------------");
        System.out.println("Non-Extra Credit Algorithms");
        System.out.println("---------------------------");
        System.out.println("\nFirst Come First Served");
        fcfs.printAvgStats();

        System.out.println("\nShortest Job First");
        sjf.printAvgStats();

        System.out.println("\nShortest Remaining Time");
        srt.printAvgStats();

        System.out.println("\nNon-Preemptive Highest Priority First");
        nhpf.printAvgStats();

        System.out.println("\nPreemptive Highest Priority First  (RR switching between same priority processes)");
        phpf.printAvgStats();

        System.out.println("\nRound Robin");
        rr.printAvgStats();            

        System.out.println("\n------------------------------------------------------");
        System.out.println("Extra Credit Algorithms (Added Priority Without Aging)");
        System.out.println("------------------------------------------------------");

        System.out.println("\nNon-Preemptive First Come First Served (Same algorithm as Non-preemptive HPF)");
        nfcfs.printAvgStats();

        System.out.println("\nPreemptive First Come First Served (Same algorithm as Preemptive HPF without RR");
        pfcfs.printAvgStats();

        System.out.println("\nRound Robin (Same algorithm as Preemptive HPF)");
        rrna.printAvgStats();

        System.out.println("\nShortest Remaining Time");
        srtna.printAvgStats();

        System.out.println("\nShortest Job First");
        sjfna.printAvgStats();                   

        System.out.println("\nNon-Preemptive and Preemptive Highest Priority First");
        System.out.println("    See above -- same algorithms as non-extra credit versions\n");
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
