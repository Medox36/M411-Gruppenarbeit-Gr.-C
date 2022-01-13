package App.SortClasses;

import java.util.Vector;

public class QuickSort implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;

    @Override
    public void sort(Vector<Integer> array) {
        storageSpaceRequired += array.size();
        timeForSorting = System.currentTimeMillis();
        _quickSort(0, array.size() - 1, array);
        timeForSorting = System.currentTimeMillis() - timeForSorting;
    }


    private void _quickSort(int leftIndex, int rightIndex, Vector<Integer> arr) {
        amountOfComparisons += 2;
        if (leftIndex >= rightIndex) {
            return;
        }

        int i = leftIndex;
        int k = rightIndex - 1;
        int pivot = arr.get(rightIndex);
        storageSpaceRequired += 32 * 3;

        do {
            amountOfComparisons += 2;
            while (arr.get(i) <= pivot && i < rightIndex) {
                i++;
                amountOfComparisons += 2;
            }
            amountOfComparisons += 2;
            while (arr.get(k) >= pivot && k > leftIndex) {
                k--;
                amountOfComparisons += 2;
            }
            amountOfComparisons += 2;
            if (i<k) {
                int temp = arr.get(i);
                arr.set(i, arr.get(k));
                arr.set(k, temp);
                writeChanges += 2;
            }
            amountOfComparisons += 2;
        } while (i < k);

        amountOfComparisons += 2;
        if (arr.get(i) > pivot ) {
            int temp = arr.get(i);
            arr.set(i, arr.get(rightIndex));
            arr.set(rightIndex, temp);
            writeChanges += 2;
        }

        _quickSort(leftIndex, i - 1, arr);
        _quickSort(i + 1, rightIndex, arr);
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


}