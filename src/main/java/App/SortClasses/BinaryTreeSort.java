package App.SortClasses;

import java.util.Vector;

public class BinaryTreeSort extends Thread implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;
    private Vector<Integer> array;
    private int temp;

    public BinaryTreeSort(Vector<Integer> array) {
        this.array = array;
        this.start();
    }
    @Override
    public void run() {

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