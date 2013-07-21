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
    public static final int PROCESS_COUNT_MAX = 100;
    
    /** A simulator corresponding to a single swapping algorithm that tracks 
     how many processes have been swapped in */
    private static class Simulator 
    {
        private Swapper swap;
        private String swapStr;
        private int swappedCount;
        
        public Simulator(Swapper swap, String swapStr)
        {
            this.swap = swap;
            this.swapStr = swapStr;
            this.swappedCount = 0;
        }
    }    
    /** Simulate each algorithm SIM_RUNS times. Print out memory maps 
     * each time memory is changed, and also print overall statistics 
     */
    public static void main(String[] args) throws CloneNotSupportedException
    {
        Simulator[] sims = new Simulator[] 
            {
                new Simulator(new FirstFitSwapper(), "First Fit"),
                new Simulator(new NextFitSwapper(), "Next Fit"),
                new Simulator(new BestFitSwapper(), "Best Fit")        
            };
        int swapped;
        int runs;
        System.out.format("Beginning simulation of %d algorithms for  %d runs each\n", sims.length, SIM_RUNS);
        System.out.println("------------------------------------------------");
        for (runs = 0; runs < SIM_RUNS; ++runs)
        {
            LinkedList<Process> q = ProcessFactory.generateProcesses(PROCESS_COUNT_MAX);

            System.out.format("Starting Run %d\n", runs);
            System.out.println("Name    Arrival    Duration    Size");
            for (Process p : q)
                System.out.format("  %-8c %-10d %-8d %-8d\n", p.name, p.arrival, p.time, p.size);
            
            System.out.println("------------------------------------------------");
            for (Simulator sim : sims)
            {
                System.out.format("\n%s:\nTime: Memory Map\n", sim.swapStr);
                swapped = sim.swap.simulate(q);
                sim.swappedCount += swapped;
                System.out.format("\nSwapped in a total of %d processes.\n", swapped);
                System.out.println("------------------------------------------------");
            }         
        }    
        System.out.println();
        for (Simulator sim : sims)
            System.out.format("Average processes swapped in by %s algorithm: %f\n", sim.swapStr, sim.swappedCount / (double) runs);
    }
}
