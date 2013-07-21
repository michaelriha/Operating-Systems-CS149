import java.util.LinkedList;

/**
 * @author Michael Riha
 * 
 * Simulate memory swapping with the First Fit algorithm
 * Swaps process memory into the first available chunk in the memory map
 */
public class FirstFitSwapper extends Swapper
{
    @Override
    public int getIndex(LinkedList<Process> memory, int size, int start) 
    {
        boolean found = false;
        int i = 0;
        for (Process p : memory)
        {
            if (p.name == '.' && p.size >= size)
            {
                found = true;
                break;
            }
            ++i;
        }
        return found ? i : -1;
    }
}
