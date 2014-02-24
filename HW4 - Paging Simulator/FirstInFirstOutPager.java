/**
 * @author Michael Riha
 * A Pager which evicts pages in the order they were put in memory
 */
public class FirstInFirstOutPager extends Pager
{
    /**@return the index of the page put into memory first 
     -1 if the page is not in the memory table */
    @Override
    public int getEvictionIndex() 
    {
        int processNumber = getMemoryOrdered().poll().number;
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
