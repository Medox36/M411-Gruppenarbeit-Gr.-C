package App.SortClasses;

import App.SortingInterface;

import java.util.Vector;

public class BubbleSort implements SortingInterface {

    @Override
    public Vector<Integer> sort(Vector<Integer> array) {
        return null;
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