/**
 * @author Michael Riha
 * A Pager which evicts pages in the order they were put in memory, but if a 
 * process is referenced a second time its time is reset
 */
public class SecondChancePager extends Pager
{
    /**@return the index of the page put into memory first */
    @Override
    public int getEvictionIndex() 
    {
        int processNumber = getMemoryOrdered().peek().number;
        int i = 0;
        for (Page p : getMemoryTable())
        {
            if (p.number == processNumber)
                return i;
            ++i;
        }
        return -1; // this should never happen
    }
}
