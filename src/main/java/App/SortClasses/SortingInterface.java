package App.SortClasses;

import App.Utils.ArrayCopier;
import App.Utils.MemoryCalculator;

import java.util.Vector;

/**
 * <h1>SortingInterface</h1>
 * The interface that hast the basic methods for the sorting classes to implement
 *
 * @author Andras Tarlos
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.1.6
 */
public interface SortingInterface {
    MemoryCalculator mc = new MemoryCalculator();
    ArrayCopier ac = new ArrayCopier();

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

    /**
     * @return The Name of the sorting-algorithm
     */
    String getAlgorithmName();
}