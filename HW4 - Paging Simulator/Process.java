import java.util.List;
import java.util.Random;

/**
 * @author Michael Riha
 * 
 * A Process with several pages of memory which randomly selects the next page
 * to reference. Takes into account locality of reference to place a certain
 * probability on the next page being within 1 delta
 */
public class Process 
{
    public static final double LOC_REF_PROBABILITY = .70;
    public static final int PROCESS_PAGES = 10;
    public Page[] pages;
    public int currentPage;
    
    /** 
       Create pages for this process from 0 through PROCESS_PAGES. Then put
       * all of the pages onto disk (for simplicity -- we don't care about
       * the disk paging order, just the memory)
       @param pager a Paging Algorithm that has memory and disk to load pages into 
     */
    public Process(Pager pager)
    {
        List<Page> disk = pager.getDiskTable();
        
        currentPage = -1;
        pages = new Page[PROCESS_PAGES];
        for (int i = 0; i < PROCESS_PAGES; ++i)
        {
            pages[i] = new Page(i);
            disk.add(i, pages[i]);
        }
    }
    
    /**
     * Get the next page to reference randomly. Use locality of reference to place
     * a 70% probability on the next page being within 1 page of the current page
     * Advances currentPage to the page number that is returned by this method
     * @return index of the next page to reference
     */
    public int getNextPageNumber()
    {
        Random r = new Random();
        
        // No references have been made yet -- select a random page [0, PROCESS_PAGES]
        if (currentPage == -1)
            currentPage = r.nextInt(PROCESS_PAGES);
        else
        {
            int delta = r.nextInt(PROCESS_PAGES);
            // Non-local reference - delta of |d| > 1
            if (delta >= PROCESS_PAGES * LOC_REF_PROBABILITY)
            {
                delta = r.nextInt(PROCESS_PAGES / 2 - 1) + 2; // range [2,5]
                if (r.nextDouble() > 0.50)
                    delta = -delta; // [2,5] or [-5, -2]
            }
            else // local reference - delta of [-1, 0, 1]
                delta = r.nextInt(3) - 1;

            currentPage = (currentPage + delta < 0) 
                    ? PROCESS_PAGES - 1 + (currentPage +  delta)
                    : (currentPage + delta) % PROCESS_PAGES;
        }
        return currentPage;
    }
}
