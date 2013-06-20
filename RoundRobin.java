import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**********************************************************************
 * Round Robin scheduling algorithm
 *@author Manzoor Ahmed
 * 
 *@data 06/19/13
 *@version 1.0 
 ************************************************************************/

public class RoundRobin extends Scheduler 
{
    @Override
    public Queue<Process> schedule(PriorityQueue<Process> q) {
        
        int startTime = 0; // the current time slice
        int queueSize = q.size();
        int finishTime = 0;
        Process p;
        Process scheduled;
        Stats stats = this.getStats();
        
        Queue<Process> scheduledQueue = new LinkedList<>();
         
         Iterator iter = q.iterator();
         
         while(iter.hasNext()){
             p = q.peek();
             
             //System.out.println("peeking.."+q.peek());
             if(p.getBurstTime() >=0){
                 p.setBurstTime(p.getBurstTime()-1);
                 
                 //need to fix satistics
                 
                 
                 scheduledQueue.add(p);
             }
             
             
             q.poll();
             //iter.next();
             System.out.println();
             
         }
         
         
         
         
         
         
         return scheduledQueue;
    }
}

