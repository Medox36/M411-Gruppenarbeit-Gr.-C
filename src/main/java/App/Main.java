package App;

import App.SortClasses.*;

import java.util.Vector;

/**
 *
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.3
 */
public class Main {
    private SortingInterface[] sortingTypes;
    private Vector<Vector<Integer>> fileArrays;
    private FileReader reader;
    private Vector<long[]> results;

    public static void main(String[] args) {
        new Main().startApp();
    }

    public void startApp() {
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
        /*
        for (long[] res: results) {
            for (int i = 0; i < 4; i++) {
                System.out.print(res[i] + ", ");
            }
            System.out.println();
        }
         */
    }

    public void updateSortingResults() {
        results = new Vector<>();
        for (SortingInterface s: sortingTypes) {
            for (Vector<Integer> v: fileArrays) {
                s.sort(v);
                results.add(new long[] {s.getAmountOfComparisons(), s.getStorageSpaceRequired(), s.getTimeForSorting(), s.getWriteChanges()});
            }
        }
    }
}
