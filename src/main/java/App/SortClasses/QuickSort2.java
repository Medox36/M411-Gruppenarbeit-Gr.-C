package App.SortClasses;

import App.ArrayCopier;
import App.SortingInterface;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom.*;
import java.util.Vector;

public class QuickSort2 extends ArrayCopier implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;
    private int[] arr;
    private int temp;
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

    private void _quickSort(int leftIndex, int rightIndex) {
        amountOfComparisons += 1;
        if (leftIndex >= rightIndex) {
            return;
        }

        int i = leftIndex;
        int k = rightIndex - 1;
        int pivot;
        if (firstSort) {
            pivot = ThreadLocalRandom.current().nextInt(leftIndex, rightIndex);
            firstSort = false;
        } else {
            pivot = arr[rightIndex];
        }
        storageSpaceRequired += 32 * 3;

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
            if (i<k) {
                temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
                writeChanges += 3;
            }
            amountOfComparisons += 1;
        } while (i < k);

        amountOfComparisons += 1;
        if (arr[i] > pivot ) {
            temp = arr[i];
            arr[i] = arr[rightIndex];
            arr[rightIndex] = temp;
            writeChanges += 3;
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
        return "QuickSort2";
    }
}