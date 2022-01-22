package App.SortClasses;

import App.ArrayCopier;
import App.SortingInterface;

import java.util.Vector;

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
      int i,j,temp,n=7;
      
        for(i=0;i<n;i++)
        {
            for(j=0;j<n-i-1;j++)
            {
                if(arr[j]>arr[j+1])
                {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
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
