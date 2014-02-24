/**
 * @author Michael Riha
 * 
 * Represents a Page entry in a Page table.
 * Keeps track of whether it is being referenced and which page number it contains
 */
public class Page 
{
    public boolean inMemory;
    public int number;
    public int lastReferenced;
        
    public Page(int number)
    {
        this.inMemory = false;
        this.number = number;
        this.lastReferenced = -1;
    }
}
