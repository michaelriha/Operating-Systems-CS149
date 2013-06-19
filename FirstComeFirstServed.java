import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*******************************************************************
 * Extends Scheduler as a First Come First Served algorithm
 * Reads a PriorityQueue<Process>, schedules it, and returns a new Queue<Process>
 * @author Michael Riha
 * @data 06/19/13
 * @version FINAL
 * *****************************************************************/

public class FirstComeFirstServed extends Scheduler
{
    @Override
    public Queue<Process> schedule(PriorityQueue<Process> q) 
    {
        int quanta = 0; // the current time slice
        int queueSize = q.size();
        int finishTime = 0;
        Process p;
        Process scheduled;
        Stats stats = this.getStats();
        Queue<Process> scheduledQueue = new LinkedList<>();
        
        for (int i = 0; i < queueSize; ++i)
        {
            p = q.poll(); // MUST BE POLLED SEPARATELY for PriorityQueue to update
                          // for (Process p : q) will give the wrong order!
            
            // If not waiting for another process
            if (quanta < p.arrivalTime)
                quanta = (int) Math.ceil(p.arrivalTime);
            else
                quanta = finishTime;
            
            // Record the statistics for this process
            stats.addWaitTime(quanta - p.arrivalTime);
            stats.addTurnaroundTime(quanta - p.arrivalTime + p.burstTime);
            stats.addResponseTime(quanta - p.arrivalTime + p.burstTime);
            stats.addProcess();
            finishTime = quanta + p.burstTime;
            
            // Create a new process with the calculated start time and add to a new queue
            scheduled = new Process();
            scheduled.burstTime = p.burstTime;
            scheduled.startTime = quanta;
            scheduled.name = p.name;
            scheduledQueue.add(scheduled);
        }
        stats.addQuanta(finishTime); // Add the total quanta to finish all jobs
        return scheduledQueue;
    }
}
