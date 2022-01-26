package App.SortClasses;

import App.Utils.ArrayCopier;

import java.util.Vector;

/**
 * <h1>Heap Sort</h1>
 * <h3>Best-Case: O(n * log(n))<br>Average-Case: O(n * log(n))<br>Worst-Case: O(n * log(n))<br>Stable: No</h3>
 * <p>The Heapsort is a comparison-based sorting algorithm. It also can be regared as an improved selection sort,
 * like selection sort, heap sort divides its input into a sorted and unsorted region and iteratively shrinks the
 * unsorted region by extracting th largest element from it and inserting it ino the sorted region.</p>
 * <a href="https://www.youtube.com/watch?v=MtQL_ll5KhQ">Heap Sort visualization</a>
 *
 * @see ArrayCopier
 * @see SortingInterface
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.22
 * @version 0.1.4
 */
public class HeapSort extends ArrayCopier implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;

    @Override
    public void run(Vector<Integer> array) {
        storageSpaceRequired += mc.getMemorySpace(array);
        timeForSorting = System.nanoTime();
        sort(copyVectorToArray(array));
        timeForSorting = System.nanoTime() - timeForSorting;
    }

    private void sort(int[] arr) {
        int n = arr.length;
        storageSpaceRequired += 32;

        for (int i = n / 2 - 1; i >= 0; i--) {
            storageSpaceRequired += 32;
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            writeChanges += 2;
            storageSpaceRequired += 64;
            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        storageSpaceRequired += 3 * 32;

        amountOfComparisons += 2;
        if (l < n && arr[l] > arr[largest])
            largest = l;

        amountOfComparisons += 2;
        if (r < n && arr[r] > arr[largest])
            largest = r;

        amountOfComparisons++;
        if (largest != i) {
            storageSpaceRequired += 32;
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            writeChanges += 2;

            heapify(arr, n, largest);
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
        return "HeapSort";
    }
}