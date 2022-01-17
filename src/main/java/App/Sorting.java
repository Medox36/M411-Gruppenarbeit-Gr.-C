package App;

import App.SortClasses.*;
import java.util.Vector;

public class Sorting {
    private Vector<Vector<Integer>> fileArrays;
    private FileReader reader;
    private Vector<long[]> results;
    private Vector<long[]> binarySRes;
    private Vector<long[]> heapSRes;
    private Vector<long[]> insertionSRes;
    private Vector<long[]> mergeSRes;
    private Vector<long[]> quickSRes;
    private Vector<long[]> shakerSRes;
    private Vector<long[]> bubbleSRes;

    public Sorting() {}

    public void start() {
        reader = new FileReader();
        fileArrays = reader.getFileArrays();
        long time = System.currentTimeMillis();
        updateSortingResults();
        System.out.println(System.currentTimeMillis() - time);

        for (long[] res: results) {
            for (int i = 0; i < 5; i++) {
                System.out.print(res[i] + ", ");
            }
            System.out.println();
        }
    }

    public void updateSortingResults() {
        results = new Vector<>();
        binarySRes = new Vector<>();
        heapSRes = new Vector<>();
        insertionSRes = new Vector<>();
        mergeSRes = new Vector<>();
        quickSRes = new Vector<>();
        shakerSRes = new Vector<>();
        bubbleSRes = new Vector<>();

        for (int k = 0; k < fileArrays.size(); k++) {
            BinaryTreeSort binaryTreeSort = new BinaryTreeSort(fileArrays.get(k));
            HeapSort heapSort = new HeapSort(fileArrays.get(k));
            InsertionSort insertionSort = new InsertionSort(fileArrays.get(k));
            MergeSort mergeSort = new MergeSort(fileArrays.get(k));
            QuickSort quickSort = new QuickSort(fileArrays.get(k));
            ShakerSort shakerSort = new ShakerSort(fileArrays.get(k));
            BubbleSort bubbleSort = new BubbleSort(fileArrays.get(k));
            try {
                binaryTreeSort.start();
                binaryTreeSort.join();
                heapSort.start();
                heapSort.join();
                insertionSort.start();
                insertionSort.join();
                mergeSort.start();
                mergeSort.join();
                quickSort.start();
                quickSort.join();
                shakerSort.start();
                shakerSort.join();
                bubbleSort.start();
                bubbleSort.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (binaryTreeSort.isAlive() || heapSort.isAlive() || insertionSort.isAlive() || mergeSort.isAlive() || quickSort.isAlive() || shakerSort.isAlive() || bubbleSort.isAlive()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            binarySRes.add(new long[] {k, binaryTreeSort.getAmountOfComparisons(), binaryTreeSort.getTimeForSorting(), binaryTreeSort.getStorageSpaceRequired(), binaryTreeSort.getWriteChanges()});
            heapSRes.add(new long[]{k + fileArrays.size(), heapSort.getAmountOfComparisons(), heapSort.getTimeForSorting(), heapSort.getStorageSpaceRequired(), heapSort.getWriteChanges()});
            insertionSRes.add(new long[]{k + fileArrays.size() * 2L, insertionSort.getAmountOfComparisons(), insertionSort.getTimeForSorting(), insertionSort.getStorageSpaceRequired(), insertionSort.getWriteChanges()});
            mergeSRes.add(new long[]{k + fileArrays.size() * 3L, mergeSort.getAmountOfComparisons(), mergeSort.getTimeForSorting(), mergeSort.getStorageSpaceRequired(), mergeSort.getWriteChanges()});
            quickSRes.add(new long[]{k + fileArrays.size() * 4L, quickSort.getAmountOfComparisons(), quickSort.getTimeForSorting(), quickSort.getStorageSpaceRequired(), quickSort.getWriteChanges()});
            shakerSRes.add(new long[]{k + fileArrays.size() * 5L, shakerSort.getAmountOfComparisons(), shakerSort.getTimeForSorting(), shakerSort.getStorageSpaceRequired(), shakerSort.getWriteChanges()});
            bubbleSRes.add(new long[]{k + fileArrays.size() * 6L, bubbleSort.getAmountOfComparisons(), bubbleSort.getTimeForSorting(), bubbleSort.getStorageSpaceRequired(), bubbleSort.getWriteChanges()});
        }
        results.addAll(binarySRes);
        results.addAll(heapSRes);
        results.addAll(insertionSRes);
        results.addAll(mergeSRes);
        results.addAll(quickSRes);
        results.addAll(shakerSRes);
        results.addAll(bubbleSRes);
    }
}
