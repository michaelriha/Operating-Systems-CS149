import java.util.Queue;
import java.util.Iterator;

/*******************************************************************
 * FirstComeFirstServed shows FCFS simulation
 * @author Manzoor Ahmed
 * @data 06/18/13
 * @version 1.0
 * *****************************************************************/

public class FirstComeFirstServed extends Scheduler
{
    @Override
    public Queue<Process> schedule(Queue<Process> q) 
    {
        Iterator<Process> iter = q.iterator();
        while(iter.hasNext())
        {
            Process current = iter.next();

            //TODO Something is wrong with random numbers
            System.out.println(current.priority);
        }	
        return null;
    }
}
