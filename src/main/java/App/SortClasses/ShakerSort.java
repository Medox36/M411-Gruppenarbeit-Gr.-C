package App.SortClasses;

import App.Utils.ArrayCopier;

import java.util.Vector;

/**
 *
 * <h1>Shakersort (Cocktailsort)</h1>
 * <h3>Best-Case: O(n)<br>Average-Case: O(n^2)<br>Worst-Case: O(n^2)<br>Stable: Yes</h3>
 * <p>The cocktail shaker sort is an extension of bubble sort. It operates in two <br>directions that results moving items
 * to the beginning of the list, improving<br> bubble sort. However, only marginal there is only marginal<br> performance improvement.</p>
 * <a href="https://upload.wikimedia.org/wikipedia/commons/e/ef/Sorting_shaker_sort_anim.gif">Shaker sort visualization</a>
 *
 * @see ArrayCopier
 * @see SortingInterface
 *
 * @author Andras Tarlos
 * @since 2022.01.22
 * @version 0.1.1
 */
public class ShakerSort extends ArrayCopier implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;
    private int[] arr;
    private int temp;

    @Override
    public void run(Vector<Integer> array) {
        arr = copyVectorToArray(array);
        storageSpaceRequired += mc.getMemorySpace(array);
        timeForSorting = System.nanoTime();
        sort();
        timeForSorting = System.nanoTime() - timeForSorting;
    }

    /**
     * The first method that will be executed. This makes sure that the program runs until everything is sorted.
     */
    private void sort() {
        storageSpaceRequired += 3 * 32;
        int i = 0, l = arr.length;
        while (i < l) {
            amountOfComparisons++;
            shaker1(i, l);
            l--;
            writeChanges++;
            shaker2(i, l);
            i++;
            writeChanges++;
        }
    }

    /**
     *
     * @param i stands for the index that moves from left to the right
     * @param l stands for the index that moves from right to the left
     */
    private void shaker1(int i, int l) {
        storageSpaceRequired += 32;
        for (int j = i; j < l - 1; j++) {
            amountOfComparisons++;
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                writeChanges += 3;
            }
            writeChanges++;
        }
    }

    /**
     *
     * @param i stands for the index that moves from left to the right
     * @param l stands for the index that moves from right to the left
     */
    private void shaker2(int i, int l) {
        storageSpaceRequired += 32;
        for (int j = l - 1; j >= i; j--) {
            amountOfComparisons++;
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                writeChanges += 3;
            }
            writeChanges++;
        }
    }

    @Override
    public long getWriteChanges() {
        return writeChanges;
    }

    @Override
    public long getTimeForSorting() {
        return timeForSorting;
    }

    @Override
    public long getAmountOfComparisons() {
        return amountOfComparisons;
    }

    @Override
    public long getStorageSpaceRequired() {
        return storageSpaceRequired;
    }

    @Override
    public String getAlgorithmName() {
        return "ShakerSort";
    }
}