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
            return 100 * processCount / (double) quanta;
        }
        
        public void addWaitTime(double time)
        {
            waitingTime += time;
        }
        
        public void addResponseTime(double time)
        {
            responseTime += time;
        }
        
        public void addTurnaroundTime(double time)
        {
            turnaroundTime += time;
        }
        
        public void addProcess()
        {
            ++processCount;
        }
        
        public void addQuanta(int quantaCount)
        {
            quanta += quantaCount;
        }
    }
    
    public Stats getStats() { return this.stats; }
    
    /**
     * Go through the process queue and create a new process queue using the 
     * selected scheduling algorithm
     * @return A scheduled process queue
     */
    public abstract Queue<Process> schedule(PriorityQueue<Process> q);
}
