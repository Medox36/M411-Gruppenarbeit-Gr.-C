package App.SortClasses;

import App.ArrayCopier;
import App.SortingInterface;
import java.util.Vector;

/**
 *
 * @author Andras Tarlos
 * @since 2022.01.22
 * @version 0.1
 */
public class MergeSort extends ArrayCopier implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;

    @Override
    public void run(Vector<Integer> array) {
        storageSpaceRequired += mc.getMemorySpace(array);
        int[] arr = ac.copyVectorToArray(array);
        timeForSorting = System.nanoTime();
        sort(arr, 0, arr.length - 1);
        timeForSorting = System.nanoTime() - timeForSorting;
    }

    private void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        storageSpaceRequired += 64;

        int[] LEFT = new int[n1];
        int[] RIGHT = new int[n2];
        storageSpaceRequired += (n1 + n2) * 32L;

        for (int i = 0; i < n1; i++) {
            storageSpaceRequired += 32;
            LEFT[i] = arr[l + i];
        }
        for (int j = 0; j < n2; j++) {
            RIGHT[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;
        int k = l;
        storageSpaceRequired += 96;
        while (i < n1 && j < n2) {
            amountOfComparisons++;
            amountOfComparisons++;
            if (LEFT[i] <= RIGHT[j]) {
                arr[k] = LEFT[i];
                writeChanges++;
                i++;
            } else {
                arr[k] = RIGHT[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            amountOfComparisons++;
            arr[k] = LEFT[i];
            writeChanges++;
            i++;
            k++;
        }

        while (j < n2) {
            amountOfComparisons++;
            arr[k] = RIGHT[j];
            writeChanges++;
            j++;
            k++;
        }
    }

    private void sort(int[] arr, int l, int r) {
        if (l < r) {
            amountOfComparisons++;
            int m =l+ (r-l)/2;
            storageSpaceRequired += 32;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
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
        return "MergeSort";
    }
}