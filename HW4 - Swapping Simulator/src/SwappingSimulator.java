import java.util.LinkedList;

/**
 * Homework 4 - Swap Simulator
 * @author Michael Riha
 * 
 * This program simulates memory swapping with first fit, next fit, and best fit 
 * memory allocation algorithms based on various parameters:
 * 
 *     -SwappingSimulator memory has 100MB swap space
 *     -Processes have randomly, evenly distributed sizes of  4, 8, 16, and 32 MB
 *     -Processes last 1, 2, 3, 4, or 5 seconds    
 *     -Process Scheduler uses FCFS
 *     -Simulation runs for "60 seconds" (sped up 10x)
 * 
 * Each algorithm is ran 5 times to get an average of the number of processes 
 * successfully swapped into memory (but not necessarily completed) and each time
 * a swap occurs the memory map is printed like 
 * AAAA....BBBBBBBB..CCCC with one letter / dot per MB of memory
 */

public class SwappingSimulator 
{
    public static final int SIM_RUNS = 5;
    public static final int SIM_TIME_MAX = 60; 
    public static final int PROCESS_COUNT_MAX = 150;
    
    public static void main(String[] args) throws CloneNotSupportedException
    {
        Swapper bestFit = new BestFitSwapper();
        Swapper nextFit = new NextFitSwapper();
        Swapper firstFit = new FirstFitSwapper();        
        int ffSwappedCount = 0;
        int nfSwappedCount = 0;
        int bfSwappedCount = 0;
        int swapped;
        
        for (int i = 0; i < SIM_RUNS; ++i)
        {
            LinkedList<Process> q = ProcessFactory.generateProcesses(PROCESS_COUNT_MAX);

            System.out.println("Name    Arrival    Duration    Size");
            for (Process p : q)
            {
                System.out.format("  %-8c %-10d %-8d %-8d\n", p.name, p.arrival, p.time, p.size);
            }
            System.out.println("\nFirst Fit:\nTime: Memory Map");
            swapped = firstFit.simulate(q);
            ffSwappedCount += swapped;
            System.out.format("Swapped in a total of %d processes.\n", swapped);
            
            System.out.println("\nNext Fit:");
            swapped = nextFit.simulate(q);
            nfSwappedCount += swapped;
            System.out.format("Swapped in a total of %d processes.\n", swapped);
            
            System.out.println("\nBest Fit:");
            swapped = bestFit.simulate(q);
            bfSwappedCount += swapped;
            System.out.format("Swapped in a total of %d processes.\n", swapped);
            
            System.out.println("-----------------------------------------------");
            System.out.format("First Fit average processes swapped in: %d\n", ffSwappedCount);
            System.out.format("Next Fit average processes swapped in: %d\n", nfSwappedCount);
            System.out.format("Best Fit average processes swapped in: %d\n", bfSwappedCount);            
        }
    }
}
