package App;

import App.SortClasses.*;
import javafx.application.Application;

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
        //Application.launch(JFX_GUI.class, args);
        //new Main().startApp();
        System.out.println(Main.class.getResourceAsStream("../images/sort.png"));
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
