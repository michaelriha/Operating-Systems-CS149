import java.util.LinkedList;
import java.util.Random;

/**
 * @author Michael Riha
 * 
 * A ProcessFactory that generates processes such that there are one or more processes
 * that are unable to finish in SIM_TIME_MAX
 * 
 * Processes last int[1,5] seconds
 * Processes use [4, 8, 16, 32] MB of memory and are EVENLY and RANDOMLY distributed
 */
public class ProcessFactory 
{    
    public static final int PROCESS_TIME_MAX = 5;
    public static final int[] PROCESS_SIZES_MB = {4, 8, 16, 32};
    public static final String NAMES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    
    /** Generate processes randomly until the total task duration is greater
     * than SIM_TIME_MAX by a bit so that there is at least one process that 
     * doesn't finish executing
     * @param n int : maximum number of processes to generate
     */
    public static LinkedList<Process> generateProcesses(int n)
    {
        LinkedList<Process> processQueue = new LinkedList<>();
        
        // used for evenly distributing memory sizes
        boolean[] process_size_used = {false, false, false, false};
        int sizes_used = 0;
        
        Random r = new Random();
        int arrival = 0;
        int runtime = 0;
        int processNumber = 0;
        while (processNumber < n && runtime < SwappingSimulator.SIM_TIME_MAX * 2)
        {
            // make the sizes "evenly distributed" by picking one of each
            // size before picking a second one, etc.
            int size_idx = r.nextInt(PROCESS_SIZES_MB.length);
            while (process_size_used[size_idx])
                size_idx = r.nextInt(PROCESS_SIZES_MB.length); 
            
            int size = PROCESS_SIZES_MB[size_idx];
            process_size_used[size_idx] = true;
            
            // If we used one of each size then reset variables
            if (++sizes_used == PROCESS_SIZES_MB.length)
            {
                sizes_used = 0;
                for (int i = 0; i < process_size_used.length; ++i) 
                    process_size_used[i] = false;
            }            
            int time = r.nextInt(PROCESS_TIME_MAX) + 1;
            runtime += time;
            
            processQueue.addLast(new Process(time, size, 
                    NAMES.charAt(processNumber % NAMES.length()), arrival, arrival));
            ++processNumber;
            arrival += (r.nextInt(PROCESS_TIME_MAX) + 1) / 2;
        }
        return processQueue;
    }
}
