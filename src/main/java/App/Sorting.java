package App;

import App.Excel.ExcelHandler;
import App.SortClasses.*;
import javafx.application.Platform;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Vector;

/**
 *
 *
 * @author Andras Tarlos
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.22
 * @version 0.2.1
 */
public class Sorting implements Runnable{
    private Vector<Vector<Integer>> fileArrays;
    private SortingInterface[] sortingTypes;
    private Vector<long[]> results;

    private JFX_GUI gui;
    private final boolean noGui;

    public Sorting() {
        noGui = true;
    }

    public Sorting(JFX_GUI gui) {
        this.gui = gui;
        noGui = false;
    }

    public void start() {
        results = new Vector<>();
        results.capacity();
        fileArrays = new FileReader().getFileArrays();

        sortingTypes = new SortingInterface[9];
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

        new ExcelHandler().initWriteAndFinish(results);
        try {
            Desktop.getDesktop().open(new File(Objects.requireNonNull(Main.class.getResource("/excel/Auswertung_Gr-C.xlsx")).toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void updateSortingResults() {
        double h = 0.0;
        String[] names = new FileReader().getFileNames();
        for (int i = 0; i < sortingTypes.length-1; i++) {
            for (int j = 0; j < fileArrays.size(); j++) {
                if (!noGui) {
                    String s = "Algorithm " + sortingTypes[i].getAlgorithmName() + " is sorting " + names[j];
                    Platform.runLater(() -> gui.setLabelText(s));
                    double p = h;
                    Platform.runLater(() -> gui.setProgress(p));
                }
                sortingTypes[i].run(fileArrays.get(j));
                results.set(i * sortingTypes.length + j, new long[] {(long) i * sortingTypes.length + j, sortingTypes[i].getTimeForSorting()});
                results.set(i * sortingTypes.length + j + 72, new long[] {(long) i * sortingTypes.length + j + 72, sortingTypes[i].getAmountOfComparisons()});
                results.set(i * sortingTypes.length + j + 144, new long[] {(long) i * sortingTypes.length + j + 144, sortingTypes[i].getWriteChanges()});
                results.set(i * sortingTypes.length + j + 216, new long[] {(long) i * sortingTypes.length + j + 216, sortingTypes[i].getStorageSpaceRequired()});
                h += 1.0/72.0;
            }
        }
        if (!noGui) {
            Platform.runLater(() -> gui.setProgress(1.0));
            Platform.runLater(() -> gui.setLabelText("done"));
            Platform.runLater(() -> gui.showCloseButton());
        }
    }

    @Override
    public void run() {
        start();
    }
}