package App.SortClasses;

import App.Utils.ArrayCopier;

import java.util.Vector;

/**
 * <h1>Bubblesort</h1>
 * <h3>Best-Case: O(n)<br>Average-Case: O(n^2)<br>Worst-Case: O(n^2)<br>Stable: Yes</h3>
 * <p>The Bubblesort is one of the simplest sorting algorithms<br> existing. It repeatedly steps through the list or array,
 * compares<br>adjacent elements and swaps them if they aren't in the correct order.<br> This process is repeated until
 * the given list is sorted. </p>
 * <a href="https://upload.wikimedia.org/wikipedia/commons/c/c8/Bubble-sort-example-300px.gif">BubbleSort visualization</a>
 *
 * @see ArrayCopier
 * @see SortingInterface
 *
 * @author Andras Tarlos
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.22
 * @version 0.2.1
 */
public class BubbleSort extends ArrayCopier implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;

    @Override
    public void run(Vector<Integer> array) {
        int[] arr = copyVectorToArray(array);
        storageSpaceRequired += mc.getMemorySpace(array);
        timeForSorting = System.nanoTime();
        sort(arr);
        timeForSorting = System.nanoTime() - timeForSorting;
    }

    /**
     * sort(int[] arr) uses two for loops to sort the unsorted array
     * @param arr is the array to be sorted
     */
    private void sort(int[] arr) {
        storageSpaceRequired += 32;
        for (int i = 0; i < arr.length; i++) {
            storageSpaceRequired += 32;
            for (int j = i + 1; j < arr.length; j++) {
                amountOfComparisons++;
                if (arr[i] < arr[j]) {
                    int swap = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swap;
                    storageSpaceRequired += 32;
                    writeChanges += 2;
                }
            }
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
        return "BubbleSort";
    }
}