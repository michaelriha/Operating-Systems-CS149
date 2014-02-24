import java.util.Random;

/**
 * @author Michael Riha
 * 
 * A pager which chooses a random page from the memory page table to evict
 */
public class RandomPickPager extends Pager
{
    /**@return a random integer in the range [0, MEMORY_FRAMES-1] */
    @Override
    public int getEvictionIndex() 
    {
       return new Random().nextInt(MEMORY_FRAMES);
    }
}
