package App.SortClasses;

import App.ArrayCopier;
import App.SortingInterface;

import java.util.Vector;

/**
 *
 * @author Andras Tarlos
 * @author Sven Wildhaber
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