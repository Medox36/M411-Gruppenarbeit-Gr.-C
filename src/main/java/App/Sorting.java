package App;

import App.SortClasses.*;
import java.util.Vector;

public class Sorting {
    private Vector<Vector<Integer>> fileArrays;
    private FileReader reader;
    private Vector<long[]> results;

    public Sorting() {}

    public void start() {
        results = new Vector<>();
        reader = new FileReader();
        fileArrays = reader.getFileArrays();
        long time = System.currentTimeMillis();
        for (int i = 0; i < 64; i++)
            results.add(new long[] {0});
        updateSortingResults();
        System.out.println(System.currentTimeMillis() - time);

    }

    public void updateSortingResults() {

        for (int k = 0; k < fileArrays.size(); k++) {
            long time = System.currentTimeMillis();
            BinaryTreeSort binaryTreeSort = new BinaryTreeSort(fileArrays.get(k));
            HeapSort heapSort = new HeapSort(fileArrays.get(k));
            InsertionSort insertionSort = new InsertionSort(fileArrays.get(k));
            MergeSort mergeSort = new MergeSort(fileArrays.get(k));
            QuickSort quickSort = new QuickSort(fileArrays.get(k));
            ShakerSort shakerSort = new ShakerSort(fileArrays.get(k));
            BubbleSort bubbleSort = new BubbleSort(fileArrays.get(k));
            System.out.println(System.currentTimeMillis() - time);

            while (binaryTreeSort.isAlive() || heapSort.isAlive() || insertionSort.isAlive() || mergeSort.isAlive() || quickSort.isAlive() || shakerSort.isAlive() || bubbleSort.isAlive()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            results.set(k, new long[] {k, binaryTreeSort.getAmountOfComparisons(), binaryTreeSort.getTimeForSorting(), binaryTreeSort.getStorageSpaceRequired(), binaryTreeSort.getWriteChanges()});
            results.set(k + fileArrays.size(), new long[]{k + fileArrays.size(), heapSort.getAmountOfComparisons(), heapSort.getTimeForSorting(), heapSort.getStorageSpaceRequired(), heapSort.getWriteChanges()});
            results.set(k + fileArrays.size() * 2, new long[]{k + fileArrays.size() * 2L, insertionSort.getAmountOfComparisons(), insertionSort.getTimeForSorting(), insertionSort.getStorageSpaceRequired(), insertionSort.getWriteChanges()});
            results.set(k + fileArrays.size() * 3, new long[]{k + fileArrays.size() * 3L, mergeSort.getAmountOfComparisons(), mergeSort.getTimeForSorting(), mergeSort.getStorageSpaceRequired(), mergeSort.getWriteChanges()});
            results.set(k + fileArrays.size() * 4, new long[]{k + fileArrays.size() * 4L, quickSort.getAmountOfComparisons(), quickSort.getTimeForSorting(), quickSort.getStorageSpaceRequired(), quickSort.getWriteChanges()});
            results.set(k + fileArrays.size() * 5, new long[]{k + fileArrays.size() * 5L, shakerSort.getAmountOfComparisons(), shakerSort.getTimeForSorting(), shakerSort.getStorageSpaceRequired(), shakerSort.getWriteChanges()});
            results.set(k + fileArrays.size() * 6, new long[]{k + fileArrays.size() * 6L, bubbleSort.getAmountOfComparisons(), bubbleSort.getTimeForSorting(), bubbleSort.getStorageSpaceRequired(), bubbleSort.getWriteChanges()});
        }
    }
}
