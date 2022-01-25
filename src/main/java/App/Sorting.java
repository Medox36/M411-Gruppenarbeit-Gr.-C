package App;

import App.Excel.ExcelFile;
import App.Excel.ExcelHandler;
import App.SortClasses.*;
import javafx.application.Platform;

import java.awt.*;
import java.io.IOException;
import java.util.Vector;

/**
 *
 *
 * @author Andras Tarlos
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.22
 * @version 0.2.5
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

        boolean multipleSheets;
        if (noGui) {
            multipleSheets = new UserReader().readBooleanJN("\n\nShould the results be saved in multiple Sheets or in one single Sheet.\n" +
                    "Can't decide? Then choose Y (Yes)");
        } else {
            multipleSheets = gui.getCheckBoxState();
        }

        try {
            ExcelFile file = new ExcelFile();
            if (file.exists()) {
                if (file.delete()) {
                    deleteFileDialog(multipleSheets, file);
                } else {
                    String s = "Either ExcelFile (.xlsx) already exists, but couldn't be deleted, or the ExcelFile is opened in a program and can't be accessed.\n" +
                            "\tPlease close the program in wich the ExcelFile is opened and run the program again or\n" +
                            "\tdelete the ExcelFile (.xlsx) and run the program again.";
                    if (noGui) {
                        System.out.println(s);
                    } else {
                        Platform.runLater(() -> gui.createDialog(s));
                    }
                }
            } else {
                deleteFileDialog(multipleSheets, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFileDialog(boolean multipleSheets, ExcelFile file) throws IOException {
        if (!file.createNewFile()) {
            String s = "ExcelFile (.xlsx-File) couldn't be created.";
            if (noGui) {
                System.out.println(s);
            } else {
                Platform.runLater(() -> gui.createDialog(s));
            }
        } else {
            new ExcelHandler(multipleSheets).initWriteAndFinish(results);
            Desktop.getDesktop().open(file);
        }
    }

    public void updateSortingResults() {
        double h = 0.0;
        String[] names = new FileReader().getFileNames();
        for (int i = 0; i < sortingTypes.length-1; i++) {
            for (int j = 0; j < fileArrays.size(); j++) {
                String s = "Algorithm " + sortingTypes[i].getAlgorithmName() + " is sorting " + names[j];
                if (!noGui) {
                    Platform.runLater(() -> gui.setLabelText(s));
                    double p = h;
                    Platform.runLater(() -> gui.setProgress(p));
                } else {
                    int p = (int) (h*10);
                    int pr = (int) (h*100);
                    String prs = "Progress: [" + "#".repeat(p) + " ".repeat(10-p) + "] " + pr + "%, " + s;
                    System.out.print(prs + " ".repeat(110-prs.length()) + "\r");
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
        } else {
            System.out.println("Progress: [##########] 100%, done!");
        }
    }

    @Override
    public void run() {
        start();
    }
}