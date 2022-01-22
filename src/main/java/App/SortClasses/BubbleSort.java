package App.SortClasses;

import App.ArrayCopier;
import App.SortingInterface;
import java.util.Vector;

/**
 *
 * @author Andras Tarlos
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.22
 * @version 0.2.0
 */
public class BubbleSort extends ArrayCopier implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;

    @Override
    public void run(Vector<Integer> array) {
        int[] arr = copyVectorToArray(array);
        storageSpaceRequired += mc.getMemorySpace(array);
        timeForSorting = System.nanoTime();
        sort(arr);
        timeForSorting = System.nanoTime() - timeForSorting;
    }
    
    public void sort(int[] arr) {
        storageSpaceRequired += 32;
        for (int i = 0; i < arr.length; i++) {
            storageSpaceRequired += 32;
            for (int j = i + 1; j < arr.length; j++) {
                amountOfComparisons++;
                if (arr[i] < arr[j]) {
                    int swap = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swap;
                    storageSpaceRequired += 32;
                    writeChanges += 2;
                }
            }
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
        return "BubbleSort";
    }
}