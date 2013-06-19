
/*************************************************************************
 * 
 * Process contains process information such as process arrival time
 * expected run time, burst time and priority. 
 * 
 * @author Manzoor Ahmed
 * @author Igor Sorokin
 * @author Michael Riha
 * 
 * @version 1.0
 * @data 06/17/2013
 ************************************************************************/

public class Process implements Comparable
{
    /* Implement comparable so that we can put these in a priority queue */
    public int compareTo(Object o)
    {
        Process p = (Process) o;
        return this.arrivalTime < p.arrivalTime ? -1 : 1;
    }

    char name;
    double arrivalTime; //[0, 100]
    int burstTime;     	//[0, 10]
    int priority;   	//[1, 4]

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getBurstTime() {
        return burstTime;
    }
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setName(char name){
        this.name = name;
    }

    public char getName()
    {
        return this.name;
    }

    @Override
    public String toString() {
            return String.format(
                    "Process %c [arrivalTime=%f, expectedRunTime=%d, priority=%d]",
                    name, arrivalTime, burstTime, priority);
    }	
}
