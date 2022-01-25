package App.SortClasses;

import App.ArrayCopier;
import App.SortingInterface;

import java.util.Vector;

/**
 * <h1>InsertionSort</h1>
 * <h3>Best-Case: O(n)<br>Average-Case: O(n^2)<br>Worst-Case: O(n^2)<br>Stable: Yes</h3>
 * <p>InsertionSort is one of the most straight forward algorithms available. <br>It virtually splits the array into a
 * sorted and an unsorted part. <br>Values from the unsorted part are picked and placed <br>at the correct position in
 * the sorted part.</p>
 * <a href="https://upload.wikimedia.org/wikipedia/commons/0/0f/Insertion-sort-example-300px.gif">InsertionSort visualization</a>
 *
 * @see App.ArrayCopier
 * @see App.SortingInterface
 *
 * @author Andras Tarlos
 * @since 2022.01.22
 * @version 0.1.1
 */
public class InsertionSort extends ArrayCopier implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;

    @Override
    public void run(Vector<Integer> array) {
        storageSpaceRequired += mc.getMemorySpace(array);
        int[] arr = ac.copyVectorToArray(array);
        timeForSorting = System.nanoTime();
        sort(arr);
        timeForSorting = System.nanoTime() - timeForSorting;
    }

    /**
     *
     * @param arr is the unsorted array that has to be sorted
     */
    private void sort(int[] arr) {
        int n = arr.length;
        storageSpaceRequired += 32;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            storageSpaceRequired += 96;

            while (j >= 0 && arr[j] > key) {
                amountOfComparisons += 2;
                arr[j + 1] = arr[j];
                writeChanges++;
                j = j - 1;
            }
            arr[j + 1] = key;
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
        return "InsertionSort";
    }
}