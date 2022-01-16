package App.Excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.commons.lang3.ArrayUtils;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.4
 */
public class ExcelWriter {
    private XSSFWorkbook workbook;
    private XSSFSheet[] sheets;

    private XSSFRow[] rows;
    private XSSFCell[][] cells;

    private boolean multipleSheets;

    public ExcelWriter() {
        // TODO create new ExcelFile and crate new XSSFWorkbook
        // TODO create Sheet in the ExcelFile
    }

    public ExcelWriter(ExcelFile excelFile) throws IOException {
        workbook = new XSSFWorkbook(new FileInputStream(excelFile));
        rows = new XSSFRow[39];
        cells = new XSSFCell[39][11];
        sheets[0] = workbook.getSheet("Auswertung");
    }

    public ExcelWriter(ExcelFile excelFile, boolean multipleSheets) throws IOException {
        workbook = new XSSFWorkbook(new FileInputStream(excelFile));
        rows = new XSSFRow[39];
        cells = new XSSFCell[39][11];
        this.multipleSheets = multipleSheets;
        if (multipleSheets) {
            sheets = new XSSFSheet[4];
            sheets[0] = workbook.createSheet("Benötigte Zeit");
            sheets[1] = workbook.createSheet("Anzahl Vergleiche");
            sheets[2] = workbook.createSheet("Anzahl Schreibzugriffe");
            sheets[3] = workbook.createSheet("Speicherbedarf");
        } else {
            sheets = new XSSFSheet[1];
            sheets[0] = workbook.createSheet("Auswertung");
        }
    }

    public void initFile() {
        if (multipleSheets) {
            createAllTablesInMultipleSheets();
        } else {
            createAllTablesInOneSheet();
        }
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void createAllTablesInOneSheet() {
        initColumnsAndRowsInOneSheet();
        styleRowsAndColumnsInOneSheet();
        writeRowAndColumnNamesOneSheet();
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void createAllTablesInMultipleSheets() {
        initColumnsAndRowsInMultipleSheets();
        styleRowsAndColumnsInMultipleSheets();
        writeRowAndColumnNamesMultipleSheet();
    }


    // OneSheet-Methods
    /**
     *
     * used if all Algorithms sort all Files
     */
    private void initColumnsAndRowsInOneSheet() {
        for (int i = 0; i < 38; i++) {
            rows[i] = sheets[0].createRow(i);
            if (i == 1 || i == 11 || i == 21|| i == 31) {
                rows[i].setHeight((short) 10);
            }
            for (int j = 0; j < 11; j++) {
                if (j < 2 && (i == 0 || i == 1 || i == 10 || i == 11 || i == 20 || i == 21 || i == 30 || i == 31)) {
                    cells[i][j] = rows[i].createCell(j);
                } else {
                    cells[i][j] = rows[i].createCell(j, CellType.NUMERIC);
                }
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

        for (int i = 0; i < 38; i++) {
            if (i == 1 || i == 11 || i == 21 || i == 31) {
                rows[i].setHeight((short) 10);
            }
        }
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void writeRowAndColumnNamesOneSheet() {
        cells[0][0].setCellValue("Benbötigte Zeit");
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

        final int[] skip = {0,1,9,10,11,19,20,21,29,30,31};
        String[] algorithms = {"BinaryTreeSort", "BubbleSort", "HeapSort",
                "InsertionSort", "MergeSort", "QuickSort", "ShakerSort"};
        for (int i = 0; i < 38; i++) {
            if (ArrayUtils.contains(skip, i)) {
                continue;
            }
            for (int j = 0; j < 7; j++) {
                cells[i][0].setCellValue(algorithms[j]);
            }
        }
    }


    // MultipleSheet-Methods
    /**
     *
     * used if all Algorithms sort all Files
     */
    public void initColumnsAndRowsInMultipleSheets() {
        for (int i = 0; i< 3; i++) {
            for (int j = 0; j < 9; j++) {
                sheets[i].createRow(j);
            }
        }
        // TODO store references to all rows in every sheet
        for (int i = 0; i< 3; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 11; k++) {
                    if (j < 2 && (k == 0 || k == 1)) {
                        sheets[i].getRow(j).createCell(k);
                    } else {
                        sheets[i].getRow(j).createCell(k, CellType.NUMERIC);
                    }
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

            sheets[i].getRow(1).setHeight((short) 10);
        }
    }

    /**
     *
     * used if all Algorithms sort all Files
     */
    private void writeRowAndColumnNamesMultipleSheet() {
        String[] algorithms = {"BinaryTreeSort", "BubbleSort", "HeapSort",
                "InsertionSort", "MergeSort", "QuickSort", "ShakerSort"};
        for (int i = 2; i < 9; i++) {
            sheets[0].getRow(i).getCell(0).setCellValue(algorithms[i-2]);
            sheets[1].getRow(i).getCell(0).setCellValue(algorithms[i-2]);
            sheets[2].getRow(i).getCell(0).setCellValue(algorithms[i-2]);
            sheets[3].getRow(i).getCell(0).setCellValue(algorithms[i-2]);
        }
        String[] files = {"InversTeilsortiert1000", "InversTeilsortiert10000", "InversTeilsortiert100000",
                "Ramdom1000", "Ramdom10000", "Ramdom100000",
                "Teilsortiert1000", "Teilsortiert10000", "Teilsortiert100000"};
        for (int i = 2; i < 11; i++) {
            sheets[0].getRow(0).getCell(i).setCellValue(files[i-2]);
            sheets[1].getRow(0).getCell(i).setCellValue(files[i-2]);
            sheets[2].getRow(0).getCell(i).setCellValue(files[i-2]);
            sheets[3].getRow(0).getCell(i).setCellValue(files[i-2]);
        }
    }


}
