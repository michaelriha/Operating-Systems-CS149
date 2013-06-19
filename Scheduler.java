import java.util.PriorityQueue;
import java.util.Queue;

/**
 * An object representing a scheduling algorithm which schedules a process queue
 * @author Michael Riha
 */
public abstract class Scheduler 
{
    private Stats stats = new Stats();
    
    /* Keep track of various time and throughput statistics */ 
    public class Stats 
    {
        private int turnaroundTime;
        private int waitingTime;
        private int responseTime;
        private int processCount;
        private int quanta;
        
        private int roundTurnaroundTime;
        private int roundWaitingTime;
        private int roundResponseTime;
        private int roundProcessCount;
        private int roundQuanta;
        
        public double getAvgTurnaroundTime()
        {
            return turnaroundTime / (double) processCount;
        }
        
        public double getRoundAvgTurnaroundTime()
        {
            return roundTurnaroundTime / (double) roundProcessCount;
        }
        
        public double getAvgWaitingTime()
        {
            return waitingTime / (double) processCount;
        }
        
        public double getRoundAvgWaitingTime()
        {
            return roundWaitingTime / (double) roundProcessCount;
        }
        
        public double getAvgResponseTime()
        {
            return responseTime / (double) processCount;
        }
        
        public double getRoundAvgResponseTime()
        {
            return roundResponseTime / (double) roundProcessCount;
        }
        
        public double getAvgThroughput()
        {
            return 100 * processCount / (double) quanta;
        }
        
        public double getRoundAvgThroughput()
        {
            return 100 * roundProcessCount / (double) roundQuanta;
        }
        
        public void addWaitTime(double time)
        {
            waitingTime += time;
            roundWaitingTime += time;
        }
        
        public void addResponseTime(double time)
        {
            responseTime += time;
            roundResponseTime += time;
        }
        
        public void addTurnaroundTime(double time)
        {
            turnaroundTime += time;
            roundTurnaroundTime += time;
        }
        
        public void addProcess()
        {
            ++processCount;
            ++roundProcessCount;
        }
        
        public void addQuanta(int quantaCount)
        {
            quanta += quantaCount;
            roundQuanta += quantaCount;
        }
        
        public void nextRound()
        {
            roundTurnaroundTime = 0;
            roundWaitingTime = 0;
            roundResponseTime = 0;
            roundProcessCount = 0;
            roundQuanta = 0;
        }
    }
    
    public Stats getStats() { return this.stats; }
    
    /**
     * Print out the average statistics for the given scheduling algorithm
     */
    public void printAvgStats()
    {
        System.out.format("\nAverage turnaround time: %f\n", stats.getAvgTurnaroundTime());
        System.out.format("Average waiting time: %f\n", stats.getAvgWaitingTime());
        System.out.format("Average response time: %f\n", stats.getAvgResponseTime());
        System.out.format("Average throughput per 100 quanta: %f\n", stats.getAvgThroughput());
    }
    
    /**
     * Print out the average statistics for the current round  only
     */
    public void printRoundAvgStats()
    {
        System.out.format("\nAverage turnaround time: %f\n", stats.getRoundAvgTurnaroundTime());
        System.out.format("Average waiting time: %f\n", stats.getRoundAvgWaitingTime());
        System.out.format("Average response time: %f\n", stats.getRoundAvgResponseTime());
        System.out.format("Average throughput per 100 quanta: %f\n", stats.getRoundAvgThroughput());
    }
    
    /**
     * Go through the process queue and create a new process queue using the 
     * selected scheduling algorithm
     * @return A scheduled process queue
     */
    public abstract Queue<Process> schedule(PriorityQueue<Process> q);
}
