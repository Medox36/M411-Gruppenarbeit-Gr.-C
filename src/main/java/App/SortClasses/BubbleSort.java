package App.SortClasses;

import java.util.Vector;

public class BubbleSort extends Thread implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;
    private Vector<Integer> array;
    private int temp;

  public BubbleSort(Vector<Integer> array) {
        storageSpaceRequired += mc.getMemorySpace(array);
        arr = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            arr[i] = array.get(i);
        }
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
                if(array[j]>array[j+1])
                {
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        System.out.println("The array after sorting is: \n");
        for(i=0;i<n;i++)
        {
            System.out.print(array[i]);
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
