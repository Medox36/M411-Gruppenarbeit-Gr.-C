package App.Excel;

import org.apache.poi.xssf.usermodel.*;

import java.io.*;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.9
 */
public class ExcelWriter {
    private ExcelFile excelFile;

    private XSSFWorkbook workbook;
    private XSSFSheet[] sheets;

    // One-Sheet
    private XSSFRow[] rows;
    private XSSFCell[][] cells;

    // Multiple-Sheets
    private XSSFRow[][] rowies;
    private XSSFCell[][][] cellies;

    private boolean multipleSheets;


    public ExcelWriter() {
        // TODO create new ExcelFile and crate new XSSFWorkbook
        // TODO create Sheet in the ExcelFile
    }

    public ExcelWriter(ExcelFile excelFile, boolean multipleSheets) throws IOException {
        this.excelFile = excelFile;
        workbook = new XSSFWorkbookFactory().create(new FileInputStream(excelFile));
        rows = new XSSFRow[39];
        cells = new XSSFCell[39][11];
        this.multipleSheets = multipleSheets;
        removeExistingSheets();
        if (multipleSheets) {
            sheets = new XSSFSheet[4];
            rowies = new XSSFRow[4][9];
            cellies = new XSSFCell[4][9][11];
            sheets[0] = workbook.createSheet("Benötigte Zeit");
            sheets[1] = workbook.createSheet("Anzahl Vergleiche");
            sheets[2] = workbook.createSheet("Anzahl Schreibzugriffe");
            sheets[3] = workbook.createSheet("Speicherbedarf");
        } else {
            sheets = new XSSFSheet[1];
            sheets[0] = workbook.createSheet("Auswertung");
        }
    }

    private void removeExistingSheets() {
        for (int i = workbook.getNumberOfSheets() - 1; i >= 0; i--) {
            workbook.removeSheetAt(i);
        }
    }

    public void initFile() {
        if (multipleSheets) {
            createAllTablesInMultipleSheets();
        } else {
            createAllTablesInOneSheet();
        }
    }

    public void writeAndFinish() {
        try {
            OutputStream out = new FileOutputStream(excelFile);
            workbook.write(out);
            out.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void createAllTablesInOneSheet() {
        initColumnsAndRowsInOneSheet();
        writeRowAndColumnNamesOneSheet();
        styleRowsAndColumnsInOneSheet();
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void createAllTablesInMultipleSheets() {
        initColumnsAndRowsInMultipleSheets();
        writeRowAndColumnNamesMultipleSheet();
        styleRowsAndColumnsInMultipleSheets();
    }

    // OneSheet-Methods
    /**
     *
     * used if all Algorithms sort all Files
     */
    private void initColumnsAndRowsInOneSheet() {
        for (int i = 0; i < 39; i++) {
            rows[i] = sheets[0].createRow(i);
            for (int j = 0; j < 11; j++) {
                cells[i][j] = rows[i].createCell(j);
            }
        }
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void styleRowsAndColumnsInOneSheet() {
        sheets[0].autoSizeColumn(0);
        sheets[0].autoSizeColumn(2);
        sheets[0].autoSizeColumn(3);
        sheets[0].autoSizeColumn(4);
        sheets[0].autoSizeColumn(5);
        sheets[0].autoSizeColumn(6);
        sheets[0].autoSizeColumn(7);
        sheets[0].autoSizeColumn(8);
        sheets[0].autoSizeColumn(9);
        sheets[0].autoSizeColumn(10);
        sheets[0].setColumnWidth(1, 512);

        for (int i = 0; i < 39; i++) {
            if (i == 1 || i == 11 || i == 21 || i == 31) {
                rows[i].setHeightInPoints(11);
            }
        }
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void writeRowAndColumnNamesOneSheet() {
        cells[0][0].setCellValue("Benötigte Zeit");
        cells[10][0].setCellValue("Anzahl Vergleiche");
        cells[20][0].setCellValue("Anzahl Schreibzugriffe");
        cells[30][0].setCellValue("Speicherbedarf");

        String[] files = {"InversTeilsortiert1000", "InversTeilsortiert10000", "InversTeilsortiert100000",
                "Ramdom1000", "Ramdom10000", "Ramdom100000",
                "Teilsortiert1000", "Teilsortiert10000", "Teilsortiert100000"};
        for (int i = 0; i < 9; i++) {
            cells[0][(i+2)].setCellValue(files[i]);
            cells[10][(i+2)].setCellValue(files[i]);
            cells[20][(i+2)].setCellValue(files[i]);
            cells[30][(i+2)].setCellValue(files[i]);
        }
        String[] algorithms = {"BinaryTreeSort", "BubbleSort", "HeapSort",
                "InsertionSort", "MergeSort", "QuickSort", "ShakerSort"};
        for (int i = 2; i < 33; i+=10) {
            for (int j = 0; j < 7; j++) {
                cells[i+j][0].setCellValue(algorithms[j]);
            }
        }
    }

    // MultipleSheet-Methods
    /**
     *
     * used if all Algorithms sort all Files
     */
    public void initColumnsAndRowsInMultipleSheets() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                rowies[i][j] = sheets[i].createRow(j);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 11; k++) {
                    cellies[i][j][k] = sheets[i].getRow(j).createCell(k);
                }
            }
        }
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void styleRowsAndColumnsInMultipleSheets() {
        for (int i = 0; i < 4; i++) {
            sheets[i].autoSizeColumn(0);
            sheets[i].autoSizeColumn(2);
            sheets[i].autoSizeColumn(3);
            sheets[i].autoSizeColumn(4);
            sheets[i].autoSizeColumn(5);
            sheets[i].autoSizeColumn(6);
            sheets[i].autoSizeColumn(7);
            sheets[i].autoSizeColumn(8);
            sheets[i].autoSizeColumn(9);
            sheets[i].autoSizeColumn(10);
            sheets[i].setColumnWidth(1, 512);

            rowies[i][1].setHeightInPoints(11);
        }
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void writeRowAndColumnNamesMultipleSheet() {
        cellies[0][0][0].setCellValue("Benötigte Zeit");
        cellies[1][0][0].setCellValue("Anzahl Vergleiche");
        cellies[2][0][0].setCellValue("Anzahl Schreibzugriffe");
        cellies[3][0][0].setCellValue("Speicherbedarf");

        String[] algorithms = {"BinaryTreeSort", "BubbleSort", "HeapSort",
                "InsertionSort", "MergeSort", "QuickSort", "ShakerSort"};
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 9; j++) {
                cellies[i][j][0].setCellValue(algorithms[j-2]);
            }
        }
        String[] files = {"InversTeilsortiert1000", "InversTeilsortiert10000", "InversTeilsortiert100000",
                "Ramdom1000", "Ramdom10000", "Ramdom100000",
                "Teilsortiert1000", "Teilsortiert10000", "Teilsortiert100000"};
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 11; j++) {
                cellies[i][0][j].setCellValue(files[j-2]);
            }
        }
    }


}
