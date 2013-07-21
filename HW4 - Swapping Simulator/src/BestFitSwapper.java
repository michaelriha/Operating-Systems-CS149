import java.util.LinkedList;

/**
 * @author Michael Riha
 * 
 * Simulate memory swapping with the Best Fit algorithm
 * Search the entire memory space for the smallest available chunk to allocate
 */
public class BestFitSwapper extends Swapper
{
    @Override
    public int getIndex(LinkedList<Process> memory, int size, int start)
    {
        int smallest = -1;
        int iBest = 0;
        int i = 0;
        for (Process p : memory)
        {
            if (p.name == '.' && p.size >= size && (smallest == -1 || p.size < smallest))
            {
                iBest = i;
                smallest = p.size;
            }
            ++i;
        }
        return iBest;
    }

}
