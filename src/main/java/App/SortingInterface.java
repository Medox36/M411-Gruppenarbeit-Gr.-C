package App;

import App.MemoryCalculator;

import java.util.Vector;

/**
 *
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.4
 */
public interface SortingInterface {
    MemoryCalculator mc = new MemoryCalculator();

    void run(Vector<Integer> array);
    /**
     * @return The amount of write changes
     */
    long getWriteChanges();
    /**
     * @return The elapsed time for sorting
     */
    long getTimeForSorting();
    /**
     * @return The amount of comparisons
     */
    long getAmountOfComparisons();
    /**
     * @return The needed storage space in bits
     */
    long getStorageSpaceRequired();
}