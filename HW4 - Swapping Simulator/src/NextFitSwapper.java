import java.util.LinkedList;

/**
 * @author Michael Riha
 * 
 * Simulate memory swapping with the Next Fit algorithm
 * Searches from the last allocated location ('start') and finds the first available
 * chunk to allocate in the memory map.
 */
public class NextFitSwapper extends Swapper
{
    @Override
    public int getIndex(LinkedList<Process> memory, int size, int start) 
    {
        int before = -1;
        int i = 0;
        for (Process p : memory)
        {
            if (p.name == '.' && p.size >= size)
            {
                if (i < start)
                    before = i;
                else
                    return i;
            }
            ++i;
        }
        return before;
    }
}
