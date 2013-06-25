import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*******************************************************************
 * Extends Scheduler as a Nonpreemptive highest priority first algorithm w/o aging
 * which schedules based on priority and uses round robin within priorities
 * Reads a PriorityQueue<Process>, schedules it, and returns a new Queue<Process>
 * @author Michael Riha
 * @data 06/21/13
 * @version FINAL
 * *****************************************************************/

public class RoundRobin extends Scheduler 
{    
    @Override
    public Queue<Process> schedule(PriorityQueue<Process> q) 
    {
        int startTime, finishTime = 0;
        Process p, scheduled, remaining;
        Scheduler.Stats stats = this.getStats();
           
        Map<Character, Integer> startTimes = new HashMap<>();        
        Map<Character, Integer> finishTimes = new HashMap<>();
        
        Queue<Process> readyQueue = new LinkedList<>();        
        Queue<Process> waitingQueue = new LinkedList<>();
        Queue<Process> scheduledQueue = new LinkedList<>();     
        
        while (!q.isEmpty() || !readyQueue.isEmpty() || !waitingQueue.isEmpty())
        {
            // add processes that have arrived by now to the ready queue
            while (!q.isEmpty() && q.peek().getArrivalTime() <= finishTime)
                readyQueue.add(q.poll());
            
            // choose which process to schedule next - Ready > Q > Waiting
            if (!readyQueue.isEmpty())
                p = readyQueue.poll();
            else if (!q.isEmpty() && waitingQueue.isEmpty())
                p = q.poll();
            else
                p = waitingQueue.poll(); 
            
            // Assign p one time slice for now
            startTime = Math.max((int) Math.ceil(p.getArrivalTime()), finishTime);
            finishTime = startTime + 1;
            
            // Record some stats if we haven't seen this process before
            if (!startTimes.containsKey(p.getName()))
            {
                if (startTime > 100)
                    break;
                startTimes.put(p.getName(), startTime);
                stats.addWaitTime(startTime - p.getArrivalTime());
                stats.addResponseTime(startTime - p.getArrivalTime() + 1);
            }
            else // add the wait time this process was in waitingQueue
                stats.addWaitTime(startTime - finishTimes.get(p.getName()));
            
            // split p into a second process with n-1 time slices and add to waiting queue
            if (p.getBurstTime() > 1)
            {
                try 
                {
                    remaining = (Process) p.clone();
                    remaining.setBurstTime(remaining.getBurstTime() - 1);
                    waitingQueue.add(remaining);
                    finishTimes.put(remaining.getName(), finishTime);
                } 
                catch (CloneNotSupportedException ex) 
                {
                    Logger.getLogger(NonpreemptiveHighestPriorityFirstNoAging.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else // this process finished so record turnaround time
            {
                stats.addTurnaroundTime(finishTime - startTimes.get(p.getName()));
                stats.addProcess();
            }            
            // Create a new process with the calculated start time and add to a new queue
            scheduled = new Process();
            scheduled.setBurstTime(1);
            scheduled.setStartTime(startTime);
            scheduled.setName(p.getName());
            scheduledQueue.add(scheduled);            
        }        
        stats.addQuanta(finishTime); // Add the total quanta to finish all jobs
        printTimeChart(scheduledQueue);
        printRoundAvgStats();
        stats.nextRound();
        
        return scheduledQueue;
    }
}
