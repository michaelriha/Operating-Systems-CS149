/**
 * A process containing information such as process arrival time
 * expected run time, burst time and priority. 
 * @author Michael Riha
 */
public class Process implements Cloneable, Comparable
{   
    @Override
    /* Implement comparable so that we can put these in a priority queue */
    public int compareTo(Object o)
    {
        Process p = (Process) o;
        return this.arrivalTime < p.arrivalTime ? -1 : 1;
    }
    
    @Override 
    public Object clone() throws CloneNotSupportedException 
    {
        Process cloned = new Process();
        cloned.name = this.name;
        cloned.arrivalTime = this.arrivalTime;
        cloned.priority = this.priority;
        cloned.burstTime = this.burstTime;
        cloned.startTime = this.startTime;
        return cloned;
    }

    private char name;
    private double arrivalTime; //[0, 100] Only applies to unscheduled processes
    private int priority;   	//[1, 4]   Only applies to unscheduled processes
    private int burstTime;     	//[0, 10]
    private int startTime;      //[0, 100] Only applies to already scheduled processes

    public double getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getPriority() { return priority; }
    public int getStartTime() { return startTime; }
    public char getName() { return this.name; }
    
    public void setArrivalTime(double arrivalTime) { this.arrivalTime = arrivalTime; }
    public void setBurstTime(int burstTime) { this.burstTime = burstTime; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setStartTime(int startTime) { this.startTime = startTime; }
    public void setName(char name){ this.name = name; }    

    @Override
    public String toString() 
    {
        return String.format(
                "    Process %c [arrivalTime=%f, expectedRunTime=%d, priority=%d]",
                name, arrivalTime, burstTime, priority);
    }	
}
