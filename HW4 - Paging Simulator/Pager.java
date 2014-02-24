import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 * @author Michael Riha
 * An abstract class to represent Paging Algorithms such as FIFO, Second chance,
 * Random Pick, and LRU. Performs simulations and has access to the RAM and 
 * disk page tables.
 * 
 * Prints the memory reference #, memory contents at the time, and any pages
 * that are evicted or promoted to facilitate a memory reference.
 * 
 * Keeps track of hit ratio and number of page references for printing later
 */
public abstract class Pager 
{
    public static final int MEMORY_FRAMES = 4;
    public static final int DISK_FRAMES = 6;
    public static final int MAX_REFERENCES = 100;
    private List<Page> disk;
    private List<Page> memory;
    private Queue<Page> memoryOrdered;
    private int pageRefs = 0;
    private int hits = 0;
    
    public Pager()
    {
        this.disk = new ArrayList<>(DISK_FRAMES);
        this.memory = new ArrayList<>(MEMORY_FRAMES);
        this.memoryOrdered = new LinkedList<>();
    }
    
    /** Reset everything like the Pager was newly constructed */
    public void reset()
    {
        this.disk = new ArrayList<>(DISK_FRAMES);
        this.memory = new ArrayList<>(MEMORY_FRAMES);
        this.memoryOrdered = new LinkedList<>();    
        this.pageRefs = 0;
        this.hits = 0;
    }
    
    /** Simulate the paging algorithm with the given parameters*/
    public void simulate()
    {
        Process proc = new Process(this);
        int pageNum;
        while (pageRefs < MAX_REFERENCES)
        {
            // Print memory contents every time a reference is made
            System.out.format("\nRef %-3d| Memory: ", pageRefs + 1);
            for (Page p : memory)
                System.out.format("%d ", p.number);
            
            pageNum = proc.getNextPageNumber();
            System.out.format(" | Referencing Page %d.", pageNum);
            
            // If the page is already in memory, we just need to update its status
            if (proc.pages[pageNum].inMemory)
            {   // If pageNum is at the front of the queue give it a Second Chance (only for SecondChancePager)
                if (this instanceof SecondChancePager && memoryOrdered.peek().number == pageNum)
                    memoryOrdered.add(memoryOrdered.poll());
                    
                proc.pages[pageNum].lastReferenced = pageRefs;
                ++hits;
            }
            else // get the page from disk and evict a page if necessary
            {   // use indexed for loop to avoid ConcurrentModificationException
                for (int i = 0; i < disk.size(); ++i)
                {
                    Page p = disk.get(i);
                    if (pageNum == p.number) // we found the page we wanted
                        swapPageIntoMemory(p);
                }
            }
            ++pageRefs;
        }        
    }
    
    /**
     * Swap a Page fromDisk with a Page in memory by choosing a Page to evict
     * @param fromDisk the page to swap fromDisk into memory
     */
    private void swapPageIntoMemory(Page fromDisk)
    {        
        fromDisk.inMemory = true;
        fromDisk.lastReferenced = pageRefs;
        int evictIdx = -1;
        
        // if the memory is not full select the first available location. no eviction
        if (memory.size() < MEMORY_FRAMES)
        {            
            evictIdx = memory.size();
            memoryOrdered.add(fromDisk);
            memory.add(evictIdx, fromDisk);
        }
        else
        {   // Select a page to evict from memory and replace with Page fromDisk
            evictIdx = getEvictionIndex();
            Page fromMem = memory.get(evictIdx);
            fromMem.inMemory = false;
            fromMem.lastReferenced = -1;
            memoryOrdered.remove(fromMem);
            System.out.format(" Evicting Page %d.", fromMem.number);
            
            // Move Page fromMem to the disk
            for (int i = 0; i < disk.size(); ++i)
                if (disk.get(i).number == fromDisk.number)
                    disk.set(i, fromMem);
            
            // Overwrite the memory with the Page fromDisk
            memoryOrdered.add(fromDisk);
            memory.set(evictIdx, fromDisk);
        }
        System.out.format(" Promoting Page %d.", fromDisk.number);
    }
    
    public int getPageRefs() { return pageRefs; }
    public double getHitRatio() { return (double) hits / pageRefs; }
    public List<Page> getDiskTable() { return disk; }
    public List<Page> getMemoryTable() { return memory; }
    public Queue<Page> getMemoryOrdered() { return memoryOrdered; }
    
    /**
     * Get the index of the value to evict from memory to allocate a page from disk
     * @return index of the page to evict
     */
    public abstract int getEvictionIndex();
}
