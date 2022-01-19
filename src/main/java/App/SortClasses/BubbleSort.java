package App.SortClasses;

import java.util.Vector;

public class BubbleSort extends Thread implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;
    private int[] arr;

  public BubbleSort(Vector<Integer> array) {
      storageSpaceRequired += mc.getMemorySpace(array);
      arr = new int[array.size()];
      for (int i = 0; i < array.size(); i++) {
          arr[i] = array.get(i);
      }
      start();
    }
    @Override
    public void run() {
        timeForSorting = System.nanoTime();
        sort();
        timeForSorting = System.nanoTime() - timeForSorting;
    }
    
    public void sort () {
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
        System.out.println("The arr after sorting is: \n");
        for(i=0;i<n;i++)
        {
            System.out.print(arr[i]);
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
}
