package App.SortClasses;

import App.ArrayCopier;
import App.SortingInterface;

import java.util.Vector;

/**
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.22
 * @version 0.1.1
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

    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            writeChanges += 2;

            heapify(arr, i, 0);
        }
    }

    void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest])
            largest = l;
        amountOfComparisons += 2;

        if (r < n && arr[r] > arr[largest])
            largest = r;
        amountOfComparisons += 2;

        if (largest != i) {
            amountOfComparisons++;
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