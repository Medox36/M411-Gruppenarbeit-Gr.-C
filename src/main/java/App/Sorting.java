package App;

import App.SortClasses.*;
import javafx.application.Platform;

import java.util.Vector;

public class Sorting extends Thread{
    private Vector<Vector<Integer>> fileArrays;
    private FileReader reader;
    private SortingInterface[] sortingTypes;
    private Vector<long[]> results;

    private JFX_GUI gui;
    private final boolean noGui;

    public Sorting() {
        super();
        noGui = true;
    }

    public Sorting(JFX_GUI gui) {
        super();
        this.gui = gui;
        noGui = false;
    }

    //4 * 8 * 9
    public void start() {
        results = new Vector<>();
        results.capacity();
        reader = new FileReader();
        fileArrays = reader.getFileArrays();

        sortingTypes = new SortingInterface[fileArrays.size()];
        sortingTypes[0] = new BinaryTreeSort();
        sortingTypes[1] = new BubbleSort();
        sortingTypes[2] = new HeapSort();
        sortingTypes[3] = new InsertionSort();
        sortingTypes[4] = new MergeSort();
        sortingTypes[5] = new QuickSort();
        sortingTypes[6] = new QuickSort2();
        sortingTypes[7] = new ShakerSort();

        for (int i = 0; i < 288; i++) {
            results.add(new long[] {});
        }

        updateSortingResults();

        for (long[] l: results) {
            for (int i = 0; i < l.length; i++) {
                System.out.print(l[i] + ", ");
            }
            System.out.println();
        }
    }

    public void updateSortingResults() {
        for (int i = 0; i < sortingTypes.length-1; i++) {
            for (int j = 0; j < fileArrays.size(); j++) {
                if (!noGui) {
                    String a = "Algorithm " + "getNameOfSortingType " + "is sorting " + "currentFileBeingSortedName";
                    String s = "";
                    Platform.runLater(() -> gui.setLabelText(s));
                }
                sortingTypes[i].run(fileArrays.get(j));
                results.set(i * sortingTypes.length + j, new long[] {(long) i * sortingTypes.length + j, sortingTypes[i].getTimeForSorting()});
                results.set(i * sortingTypes.length + j + 71, new long[] {(long) i * sortingTypes.length + j + 72, sortingTypes[i].getAmountOfComparisons()});
                results.set(i * sortingTypes.length + j + 142, new long[] {(long) i * sortingTypes.length + j + 144, sortingTypes[i].getWriteChanges()});
                results.set(i * sortingTypes.length + j + 213, new long[] {(long) i * sortingTypes.length + j + 216, sortingTypes[i].getStorageSpaceRequired()});
            }
        }
    }

    @Override
    public void run() {
        start();
    }
}