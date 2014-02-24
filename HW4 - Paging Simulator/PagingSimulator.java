/**
 * Homework 4 - Paging Simulator
 * @author Michael Riha
 * 
 * This program simulates the FIFO, second chance, LRU, and random pick 
 * paging algorithms based on various parameters:
 *      
 *      -Processes consist of 10 pages numbered 0 to 9
 *      -Physical memory has 4 page frames
 *      -Disk has 6 page frames
 *      -Process makes random references to pages
 * 
 * Each algorithm is ran 5 times with 100 page references per run. The average
 * hit ratio of pages in memory is calculated during the simulation and printed.
 * For each reference we print the page numbers of the pages in memory, 
 * which page needed to be paged, and which page was evicted 
 */
public class PagingSimulator 
{
    public static final int SIM_RUNS = 5;
    
    private static class Simulator
    {
        private Pager page;
        private String pageStr;
        private double hitRatioSum;
        
        public Simulator(Pager page, String pageStr)
        {
            this.page = page;
            this.pageStr = pageStr;
            this.hitRatioSum = 0.0;
        }
    }    
    
    public static void main(String[] args)
    {
        Simulator[] sims = new Simulator[] 
        {
            new Simulator(new FirstInFirstOutPager(), "First In First Out"),
            new Simulator(new SecondChancePager(), "Second Chance"),
            new Simulator(new LeastRecentlyUsedPager(), "Least Recently Used"),
            new Simulator(new RandomPickPager(), "Random Pick")
        };       
        
        for (int i = 0; i < SIM_RUNS; ++i)
        {
            System.out.println("+------------------------------+");
            System.out.format("| Starting Simulation Run %d    |\n", i);
            System.out.println("+------------------------------+");
            
            for (Simulator sim : sims)
            {
                System.out.println("--------------------------------");
                System.out.format("Using %s Algorithm\n", sim.pageStr);
                System.out.print("--------------------------------");
                
                sim.page.simulate();
                
                double hitRatio = sim.page.getHitRatio();
                sim.hitRatioSum += hitRatio;
                System.out.format("\nHit Ratio: %f\n", hitRatio);
                
                sim.page.reset();
            }
        }        
        System.out.println("--------------------------------");
        System.out.format("Average Hit Ratios over %d runs:\n", SIM_RUNS);
        
        for (Simulator sim : sims)
            System.out.format("    %s: %f\n", sim.pageStr, sim.hitRatioSum / SIM_RUNS);
    }
}
