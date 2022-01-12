package App;

import App.SortClasses.*;

import java.util.Vector;

public class Sorting {
    private SortingInterface[] sortingTypes;
    private Vector<Vector<Integer>> fileArrays;
    private FileReader reader;
    private Vector<long[]> results;

    public Sorting() {

    }

    public void start() {
        reader = new FileReader();
        fileArrays = reader.getFileArrays();

        sortingTypes = new SortingInterface[7];
        sortingTypes[0] = new BinaryTreeSort();
        sortingTypes[1] = new HeapSort();
        sortingTypes[2] = new InsertionSort();
        sortingTypes[3] = new MergeSort();
        sortingTypes[4] = new QuickSort();
        sortingTypes[5] = new Shakersort();
        sortingTypes[6] = new BubbleSort();

        updateSortingResults();
        for (long[] res: results) {
            for (int i = 0; i < 5; i++) {
                System.out.print(res[i] + ", ");
            }
            System.out.println();
        }
    }

    public void updateSortingResults() {
        results = new Vector<>();
        int i = 0;
        for (SortingInterface s: sortingTypes) {
            for (Vector<Integer> v: fileArrays) {
                s.sort(v);
                results.add(new long[] {i++, s.getAmountOfComparisons(), s.getStorageSpaceRequired(), s.getTimeForSorting(), s.getWriteChanges()});
            }
        }
    }
}
