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
        int iBest = -1;
        int i = 0;
        for (Process avl : memory)
        {
            if (avl.name == '.' && avl.size >= size && (smallest == -1 || avl.size < smallest))
            {
                iBest = i;
                smallest = avl.size;
            }
            ++i;
        }
        return iBest;
    }

}
