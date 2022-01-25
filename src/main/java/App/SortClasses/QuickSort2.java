package App.SortClasses;

import App.ArrayCopier;
import App.SortingInterface;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Vector;

/**
 * <h1>Quicksort (with random pivot)</h1>
 * <h3>Best-Case: O(n * log(n))<br>Average-Case: O(n * log(n))<br>Worst-Case: O(n^2)<br>Stable: No</h3>
 * <p>Quicksort is a recursive based algorithm. If it's well implemented it is somewhat faster than
 * merge sort and about two / three times faster than the heapsort. Quicksort works by selecting a
 * 'pivot' element from the array and paritioning the other elements into two sub-arrays, according
 * to weather their value is lower or greater.</p>
 * <a href="https://www.youtube.com/watch?v=Hoixgm4-P4M">Quicksort visualization</a>
 *
 * @author Andras Tarlos
 * @since 2022.01.22
 * @version 0.1.2
 */
public class QuickSort2 extends ArrayCopier implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;
    private int[] arr;
    private boolean firstSort;

    @Override
    public void run(Vector<Integer> array) {
        firstSort = true;
        storageSpaceRequired += mc.getMemorySpace(array);
        arr = copyVectorToArray(array);
        timeForSorting = System.nanoTime();
        _quickSort(0, arr.length - 1);
        timeForSorting = System.nanoTime() - timeForSorting;
    }

    /**
     * This is a recursive methode that uses a value called 'pivot' at a certain index and sorts the given array.
     * @param leftIndex is the index value that moves from left to right
     * @param rightIndex is the index value that moves from right to left
     */
    private void _quickSort(int leftIndex, int rightIndex) {
        amountOfComparisons += 1;
        if (leftIndex >= rightIndex) {
            return;
        }

        int i = leftIndex;
        int k = rightIndex - 1;
        int pivot;
        // Using the first time a random pivot
        if (firstSort) {
            pivot = ThreadLocalRandom.current().nextInt(leftIndex, rightIndex);
            firstSort = false;
        } else {
            pivot = arr[rightIndex];
        }
        storageSpaceRequired += 32 * 3;

        int temp;
        do {
            amountOfComparisons += 2;
            while (arr[i] <= pivot && i < rightIndex) {
                i++;
                amountOfComparisons += 2;
            }
            amountOfComparisons += 2;
            while (arr[k] >= pivot && k > leftIndex) {
                k--;
                amountOfComparisons += 2;
            }
            amountOfComparisons += 1;
            if (i < k) {
                temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
                writeChanges += 2;
            }
            amountOfComparisons += 1;
        } while (i < k);

        amountOfComparisons += 1;
        if (arr[i] > pivot ) {
            temp = arr[i];
            arr[i] = arr[rightIndex];
            arr[rightIndex] = temp;
            writeChanges += 2;
        }

        _quickSort(leftIndex, i - 1);
        _quickSort(i + 1, rightIndex);
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
        return "QuickSort (Random-Pivot)";
    }
}