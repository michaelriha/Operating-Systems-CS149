import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Michael Riha
 * 
 * Class representing a Memory Swapping Algorithm. Extended by 
 * BestFit, NextFit, and FirstFit
 * 
 * Simulates the algorithm and prints a memory map every time memory is 
 * allocated or deallocated.
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
     * @param processQueue Queue<Process> in FCFS order 
     * @return int : number of processes that were successfully swapped in 
     */
    public int simulate(Queue<Process> processQueue) throws CloneNotSupportedException
    {
        // Create memory with an unallocated block covering the whole space
        Queue<Process> q = copyQueue(processQueue);
        Queue<Process>  waitingQueue = new LinkedList<>();
        LinkedList<Process> memory = new LinkedList<>();
        memory.add(new Process(0, MEMORY_SIZE_MB, '.', 0, 0));
        // memory index (-1 = unavailiable), number of processes swapped, and current time
        int memIndex = -1;
        int swapped = 0;
        int time = 0;
        // p = temporary Process to allocate memory to, running is running
        Process p = null;
        Process running = null;
        
        while (!q.isEmpty() || !waitingQueue.isEmpty())
        {            
            // Allow the running process to execute for 1 second
            if (running != null && --running.time <= 0)
            {
                deallocateProcess(memory, time);
                memory = mergeAdjacentFragmentedMemory(memory);
                running = null;
            }
            if (running == null && waitingQueue.peek() != null)
            {
                running = waitingQueue.poll();
                --running.time;
            }
            
            // Allocate processes that have arrived and have memory available for them
            while (q.peek() != null && q.peek().arrival <= time && getIndex(memory, q.peek().size, memIndex) != -1)
            {
                p = q.poll();
                p.start = time;
                
                if (running == null)
                    running = (waitingQueue.peek() != null) ? waitingQueue.poll() : p;
                else
                    waitingQueue.add(p);
                
                // Replace the available memory with p and a proportionally smaller available block
                memIndex = getIndex(memory, p.size, memIndex);
                Process available = memory.remove(memIndex);
                memory.add(memIndex, p);
                available.size -= p.size;
                if (available.size > 0)
                    memory.add(memIndex + 1, available);
                ++swapped;
            
                // Print the memory map every time we swap memory
                printMemoryMap(memory, time);
            }            
            if(++time > SwappingSimulator.SIM_TIME_MAX) break;
            
            // Sleep for 100ms  (10x speedup over realtime)
            try {
                Thread.sleep(100);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        return swapped;
    }    
    /**
     * Find the memory for the process that finished and deallocate it
     * @param memory LinkedList<Process> : the memory to search for deallocation
     * @param time int : the current time for use in printing the memory map
     */
    public static void deallocateProcess(LinkedList<Process> memory, int time) throws CloneNotSupportedException
    {
        int i = 0;
        for (Process p : memory)
        {   /* Remove the finished process and replace with available space ('.' process)
               NOTE: I could not make the LinkedList access efficient due to 
               ConcurrentModificationExceptions while using iterator to modify, and 
               Java does not give direct access to the list pointers for manual traversal
             */
            if (p.name != '.' && p.time <= 0)
            {
                memory.remove(i);
                memory.add(i, new Process(0, p.size, '.', 0, 0));
                printMemoryMap(memory, Math.max(p.arrival + p.time, time));
                break;
            }
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
     * @param memory Queue<Process> representing allocated memory
     * @throws CloneNotSupportedException 
     */
    public static void printMemoryMap(Queue<Process> memory, int time) throws CloneNotSupportedException
    {
        Queue<Process> memcopy = copyQueue(memory);
        StringBuilder sb = new StringBuilder().append(String.format("%-2d:    ", time));
        
        for (Process p : memcopy)
            while (p.size-- > 0)
                sb.append(p.name);
        
        System.out.println(sb);
    }    
    /**
     * Create a deep copy of q
     * @param q Queue<Process> in FCFS scheduling order
     * @return A deep copy of q in the same order
     * @throws CloneNotSupportedException
     */
    public static Queue<Process> copyQueue(Queue<Process> q) throws CloneNotSupportedException
    {        
        LinkedList<Process> qcopy = new LinkedList<>();
        for (Process p : q)
            qcopy.add((Process) p.clone());
        return qcopy;
    }
}
