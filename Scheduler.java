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
        private int throughput;
        private int processCount;
        
        public double getAvgTurnaroundTime()
        {
            return turnaroundTime / (double) processCount;
        }
        
        public double getAvgWaitingTime()
        {
            return waitingTime / (double) processCount;
        }
        
        public double getAvgResponseTime()
        {
            return responseTime / (double) processCount;
        }
        
        public double getAvgThroughput()
        {
            return throughput / (double) processCount;
        }
    }
    
    public Stats getStats() { return this.stats; }
    
    /**
     * Go through the process queue and create a new process queue using the 
     * selected scheduling algorithm
     * @return A scheduled process queue
     */
    public abstract Queue<Process> schedule(Queue<Process> q);
}
