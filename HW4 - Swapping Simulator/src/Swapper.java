import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Michael Riha
 * 
 * Class representing a Memory Swapping Algorithm. Extended by 
 * BestFit, NextFit, and FirstFit
 */
public abstract class Swapper 
{
    public static final int MEMORY_SIZE_MB = 100;
    
    /**
     * Use a particular swapping algorithm to get the index of the next allocation
     * @param memory LinkedList<Process> representing the memory space
     * @param size int : size of the process to be allocated
     * @param start int: index to start searching (ignored by FirstFit, BestFit)
     * @return int : the index that the next process will be allocated to
     */
    public abstract int getIndex(LinkedList<Process> memory, int size, int start);
    
    /**
     * Simulate swapping using the given Swapping Algorithm and collect statistics
     * @param q Process queue in FCFS order 
     * @return int : number of processes that were successfully swapped in 
     */
    public int simulate(Queue<Process> q) throws CloneNotSupportedException
    {
        // Create memory with an unallocated block covering the whole space
        LinkedList<Process> memory = new LinkedList<>();
        memory.add(new Process(0, MEMORY_SIZE_MB, '.', 0));
        int memIndex = -1;
        int swapped = 0;
        int time = 0;
        Process p = null;
        
        while (!q.isEmpty())
        {
            if ((q.peek().arrival >= time || p == null) && getIndex(memory, q.peek().size, memIndex) != -1)
            {
                p = q.poll();
                p.start = time;
            }
            
//            // Sleep for 100ms  (10x speedup over realtime)
//            try {
//                Thread.sleep(100);
//            } catch(InterruptedException ex) {
//                Thread.currentThread().interrupt();
//            }
//            
            // Advance the time to this process's arrival time
            time = Math.max(time, p.arrival);            
            if (time > SwappingSimulator.SIM_TIME_MAX)
                return swapped;
            
            // Deallocate processes that finished and then merge any freed memory
            deallocateMemory(memory, time);
            memory = mergeAdjacentFragmentedMemory(memory);
            
            // Get next memory index. Wait for memory if there is none available
            memIndex = getIndex(memory, p.size, memIndex);
            while (memIndex == -1)
            {
                if (++time > SwappingSimulator.SIM_TIME_MAX)
                    return swapped;
                deallocateMemory(memory, time);
                memory = mergeAdjacentFragmentedMemory(memory);
                memIndex = getIndex(memory, p.size, memIndex);
            }
            
            // Replace the available memory with p and a proportionally smaller available block
            Process available = memory.remove(memIndex);
            available.size -= p.size;
            memory.add(memIndex, p);
            memory.add(memIndex + 1, available);
            ++swapped;
            
            // Print the memory map every time we swap memory
            printMemoryMap(memory, time);
        }
        return swapped;
    }
    
    /**
     * Deallocate memory for processes that finished before or at 'time' seconds
     * @param memory LinkedList<Process> : the memory to search for deallocation
     * @param time int : deallocate memory that finished before this many seconds
     */
    public static void deallocateMemory(LinkedList<Process> memory, int time) throws CloneNotSupportedException
    {
        ArrayList<Integer> deallocateQueue = new ArrayList<>();
        
        // Find processes that have finished and add to a queue
        int i = 0;
        for (Process p : memory)
        {
            if (p.name != '.' && p.arrival + p.time < time)
                deallocateQueue.add(i);
            ++i;
        }
        
        /* For each process that finished, remove it and add a free '.' memory block
           then print the new memory map.
           NOTE: I could not make the LinkedList access efficient due to 
           ConcurrentModificationExceptions while using iterator to modify, and 
           Java does not give direct access to the list pointers for manual traversal
         */
        i = 0;
        for (Integer d : deallocateQueue)
        {
            Process p = memory.remove((int) d);
            memory.add(d, new Process(0, p.size, '.', 0));
            printMemoryMap(memory, Math.max(p.arrival + p.time, time));
            ++i;
        }
    }
    
    /**
     * Merge adjacent fragmented free memory (processes named '.') into contiguous blocks
     * @param memory Queue / LinkedList<Process> representing the memory space
     * @return LinkedList<Process> :  a new memory space with merged free memory
     */
    public static LinkedList<Process> mergeAdjacentFragmentedMemory(Queue<Process> memory)
    {
        LinkedList<Process> merged = new LinkedList<>();
        while (!memory.isEmpty())
        {
            Process p = memory.poll();
            while (p.name == '.' && memory.peek() != null && memory.peek().name == '.')
                p.size += memory.poll().size;
            merged.add(p);
        }
        return merged;
    }
    
    /**
     * Print a memory map such as "AAAA....BBBBBBBBBB..CCCC" where dots are 
     * empty memory and names are repeated once for each MB allocated
     * @param memory LinkedList<Process> representing allocated memory
     * @throws CloneNotSupportedException 
     */
    public static void printMemoryMap(LinkedList<Process> memory, int time) throws CloneNotSupportedException
    {
        LinkedList<Process> memcopy = copyQueue(memory);
        StringBuilder sb = new StringBuilder().append(time).append(":    ");
        
        for (Process p : memcopy)
            while (p.size-- > 0)
                sb.append(p.name);
        
        System.out.println(sb);
    }        
    
    /**
     * Create a deep copy of q
     * @param q LinkedList<Process> in FCFS scheduling order
     * @return A deep copy of q in the same order
     * @throws CloneNotSupportedException
     */
    public static LinkedList<Process> copyQueue(LinkedList<Process> q) throws CloneNotSupportedException
    {        
        LinkedList<Process> qcopy = new LinkedList<>();
        for (Process p : q)
            qcopy.add((Process) p.clone());
        return qcopy;
    }
}
