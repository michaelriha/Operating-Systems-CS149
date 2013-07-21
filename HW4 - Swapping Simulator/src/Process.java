/**
 * @author Michael Riha
 * 
 * An object representing a Process which has a runtime, memory size,
 * name, and arrival time
 */
public class Process implements Cloneable
{
    public int time;
    public int size;
    public int start;
    public int arrival;
    public char name;
    
    public Process(int time, int size, char name, int arrival, int start)
    {
        this.time = time;
        this.size = size;
        this.name = name;
        this.arrival = arrival;
        this.start = start;
    }
        
    @Override 
    public Object clone() throws CloneNotSupportedException 
    {
        return new Process(this.time, this.size, this.name, this.arrival, this.start);
    }
}
