package App.Excel;

import App.Sorting;
import App.DataArraySyntaxException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Vector;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.2.0
 */
public class ExcelWriter {

    /**
     * Reference to the ExcelFile which the data will be written to.
     *
     * @see ExcelFile
     */
    private final ExcelFile excelFile;

    /**
     * High level representation of an Excel workbook. This object is used for reading or writing a workbook.
     * It is also the top level object for creating new sheets/etc.
     *
     * @see org.apache.poi.xssf.usermodel.XSSFWorkbook
     * @see org.apache.poi.ss.usermodel.Workbook
     */
    private final XSSFWorkbook workbook;

    /**
     * XSSFSheet-Array containing all the sheets,
     * either containing 1 sheet, when all data is stored in one sheet
     * or containing 4 sheets, when the data is stored in multiple sheets each containing one table.
     *
     * @see org.apache.poi.xssf.usermodel.XSSFSheet
     */
    private final XSSFSheet[] sheets;

    // One-Sheet
    /**
     * XSSFRow-Array containing all 39 rows of the sheet.<br><br>
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     *
     * @see org.apache.poi.xssf.usermodel.XSSFRow
     */
    private XSSFRow[] rows;

    /**
     * XSSFCell-Array containing all 429 cells of the sheet.<br><br>
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     *
     * @see org.apache.poi.xssf.usermodel.XSSFCell
     */
    private XSSFCell[][] cells;

    // Multiple-Sheets
    /**
     * <p>
     * XSSFRow-Array containing a total of 36 rows, 9 rows per sheet/table.<br>
     * First array-dimension is the identifier for the sheet and the second array-dimension is the row of the sheet.
     * <p>
     * XSSFRow[x][y] ->
     *      x: from 0 to 3,
     *      y: from 0 to 8.
     * <br><br>
     * @apiNote
     * Used when the data is stored in four different sheets, each containing one table.
     *
     * @see org.apache.poi.xssf.usermodel.XSSFRow
     */
    private XSSFRow[][] rowies;

    /**
     * <p>
     * XSSFCell-Array containing total of 396 cells, 99 per sheet/table, 11 per row.<br>
     * First array-dimension is the identifier for the sheet, the second array-dimension is the identifier for the row and
     * the third array-dimension is the cell in the row of a sheet.
     * <p>
     * XSSFCell[x][y][z] ->
     *      x: from 0 to 3,
     *      y: from 0 to 8.
     *      z: from 0 to 10.
     * <br><br>
     * @apiNote
     * Used when the data is stored in four different sheets containing one table.
     *
     * @see org.apache.poi.xssf.usermodel.XSSFCell
     */
    private XSSFCell[][][] cellies;

    /**
     * <p>
     * Signalizing if the ExcelWriter stores/writes the data in one or four different sheets.
     * <p>
     * If boolean is true: the data will be written to four different sheets.<br>
     * If boolean is false: th data will be written to one single sheet.
     *
     */
    private final boolean multipleSheets;

    /**
     * The maximum of rows in one sheet.
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private final int MAX_ROWS_ONE_SHEET = 43;

    /**
     * The maximum of rows in one of four sheets.
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private final int MAX_ROWS_MULTIPLE_SHEETS = 10;

    /**
     * The maximum of cells there can be in a row.
     */
    private final int MAX_CELLS_PER_ROW = 11;


    public ExcelWriter(ExcelFile excelFile, boolean multipleSheets) throws IOException {
        this.excelFile = excelFile;
        workbook = new XSSFWorkbookFactory().create(new FileInputStream(excelFile));
        this.multipleSheets = multipleSheets;
        removeExistingSheets();
        if (multipleSheets) {
            sheets = new XSSFSheet[4];
            rowies = new XSSFRow[4][MAX_ROWS_MULTIPLE_SHEETS];
            cellies = new XSSFCell[4][MAX_ROWS_MULTIPLE_SHEETS][MAX_CELLS_PER_ROW];
            sheets[0] = workbook.createSheet("Benötigte Zeit");
            sheets[1] = workbook.createSheet("Anzahl Vergleiche");
            sheets[2] = workbook.createSheet("Anzahl Schreibzugriffe");
            sheets[3] = workbook.createSheet("Speicherbedarf");
        } else {
            sheets = new XSSFSheet[1];
            rows = new XSSFRow[MAX_ROWS_ONE_SHEET];
            cells = new XSSFCell[MAX_ROWS_ONE_SHEET][MAX_CELLS_PER_ROW];
            sheets[0] = workbook.createSheet("Auswertung");
        }
    }

    /**
     * Removes all the existing sheets of the ExcelFile given in the constructor
     */
    private void removeExistingSheets() {
        for (int i = workbook.getNumberOfSheets() - 1; i >= 0; i--) {
            workbook.removeSheetAt(i);
        }
    }

    /**
     * Takes care of all tables and sheets necessary for evaluation of the Algorithms.
     * Creates, writes row and column headers and styles the cells for the user to read the tables better.
     */
    public void initFile() {
        if (multipleSheets) {
            initColumnsAndRowsInMultipleSheets();
            writeRowAndColumnNamesMultipleSheet();
            styleRowsAndColumnsInMultipleSheets();
        } else {
            initColumnsAndRowsInOneSheet();
            writeRowAndColumnNamesOneSheet();
            styleRowsAndColumnsInOneSheet();
        }
    }

    /**
     * Will write the data, given in a Vector, into the right place in the tables.<br><br>
     *
     * @param data Vector of type long[], containing data from the sorting through the algorithms.
     *
     * @throws DataArraySyntaxException when the method finds that the array isn't matching the syntax logic.
     *
     * @apiNote
     * Can be used when the data is stored in one sheet or four different sheets.
     *
     * @see Sorting#updateSortingResults()
     */
    public void write(Vector<long[]> data) throws DataArraySyntaxException {
        if (multipleSheets) {
            writeToMultipleSheets(data);
            styleRowsAndColumnsInMultipleSheets();
        } else {
            writeToOneSheet(data);
            styleRowsAndColumnsInOneSheet();
        }
    }

    /**
     * Will write the data, given in a Vector, into the right place in the tables in one single sheet.<br><br>
     *
     * @param data Vector of type long[], containing data from the sorting through the algorithms.
     *
     * @throws DataArraySyntaxException when the method finds that the array isn't matching the syntax logic.
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private void writeToOneSheet(Vector<long[]> data) throws DataArraySyntaxException {
        // counter to iterate through the data Vector<long[]>
        int k = 0;

        // counter to check the syntax logic of the array
        int h = 0;

        //rows, skipping row 0 and 1
        for (int i = 2; i < MAX_ROWS_ONE_SHEET; i++) {
            // further rows to skip
            if (i == 10||i == 11||i == 12||i == 19||i == 21||i == 22||i == 23||i == 32||i == 33||i == 34) {
                continue;
            }
            // cell, skipping cell 0 and 1
            for (int j = 2; j < MAX_CELLS_PER_ROW; j++) {
                long[] arr = data.get(k);
                // check if the syntax logic for the arrays has been followed correctly
                if (arr[0] != h) {
                    throw new DataArraySyntaxException();
                }
                cells[i][j].setCellValue(arr[1]);
                k++;
                h++;
                if (h == 63) {
                    h = 0;
                }
            }
        }
    }

    /**
     * Will write the data, given in a Vector, into the right place in the tables in four multiple sheets.<br><br>
     *
     * @param data Vector of type long[], containing data from the sorting through the algorithms.
     *
     * @throws DataArraySyntaxException when the method finds that the array isn't matching the syntax logic.
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private void writeToMultipleSheets(Vector<long[]> data) throws DataArraySyntaxException {
        // counter to iterate through the data Vector<long[]>
        int h = 0;

        // counter to check the syntax logic of the array
        int l = 0;

        // sheets
        for (int i = 0; i < 4; i++) {
            // rows, skipping row 0 and 1
            for (int j = 2; j < MAX_ROWS_MULTIPLE_SHEETS; j++) {
                // cells, skipping cell 0 and 1
                for (int k = 2; k < MAX_CELLS_PER_ROW; k++) {
                    long[] arr = data.get(h);
                    // check if the syntax logic for the arrays has been followed correctly
                    if (arr[0] != l) {
                        throw new DataArraySyntaxException();
                    }
                    cellies[i][j][k].setCellValue(arr[1]);
                    h++;
                    l++;
                }
            }
            l = 0;
        }
    }

    /**
     * <p>
     * Will write the changes to the .xlsx-file given in the constructor.
     * <p>
     * After writing the workbook wil be closed and the FileOutputStream, which is opened to write to the .xlsx-file
     * will be closed as well.<br><br>
     * 
     * @throws IOException if the file either doesn't exist or it can't be written to.
     * 
     * @apiNote
     * After calling this method the ExelWriter instance must not be used again. Doing so can cause multiple problems, 
     * because a closed Workbook can't be opened again.
     */
    public void finish() throws IOException {
        OutputStream out = new FileOutputStream(excelFile);
        workbook.write(out);
        out.close();
        workbook.close();
    }

    /**
     * Creates a XSSFCellStyle object which contains styling of the borders.
     * Border on the Bottom, the Top, the Right and the Left are all thin, black borders.<br><br>
     *
     * XSSFCellStyle can be used to apply a Style to cells.<br><br>
     *
     * @return XSSFCellStyle object containing border styling
     */
    private XSSFCellStyle getBorderCellStyle() {
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        // index of IndexedColors.BLACK
        short color = IndexedColors.BLACK.getIndex();
        // bottom border
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(color);
        // right border
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(color);
        // left border
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(color);
        // top border
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(color);

        return cellStyle;
    }

    // OneSheet-Methods
    /**
     * <p>
     * Creates 39 rows and to each row 11 cells.
     * Creates all rows and cells in one Excel-Sheet.<br>
     * Total of cells: 429<br>
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private void initColumnsAndRowsInOneSheet() {
        for (int i = 0; i < MAX_ROWS_ONE_SHEET; i++) {
            // creating row
            rows[i] = sheets[0].createRow(i);
            for (int j = 0; j < MAX_CELLS_PER_ROW; j++) {
                // crating cell
                cells[i][j] = rows[i].createCell(j);
            }
        }
    }

    /**
     * <p>
     * Styles the rows, columns and cells of the ExcelFile given in the constructor.
     * It auto-sizes the columns, which contain text so the text doesn't overlap.
     * It sets the width of the 2nd column(in Excel column B), which is used to make the table look better.
     * It sets the height of the rows, which used to make the tables look better.
     * And it adds borders to te cells, which are used to create the tables.
     * The rows in between two tables will get no borders.<br>
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private void styleRowsAndColumnsInOneSheet() {
        // set auto-sizing to the necessary columns
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

        // set the width of the remaining column to 22 Pixels(measurement of Excel)
        sheets[0].setColumnWidth(1, 512);

        // set the height of the selected rows 1, 12, 23 and 34 to 11 Points = 22 Pixels(measurement of Excel)
        for (int i = 0; i < MAX_ROWS_ONE_SHEET; i++) {
            if (i == 1||i == 12||i == 23||i == 34) {
                rows[i].setHeightInPoints(11);
            }
        }

        // get the CellStyle for the borders
        XSSFCellStyle style = getBorderCellStyle();

        // apply the style to all cells off all rows, except for rows 9, 19 and 29
        for (int i = 0; i < MAX_ROWS_ONE_SHEET; i++) {
            // leave out rows 9, 21 and 32
            if (i == 10||i == 21||i == 32) {
                continue;
            }
            for (int j = 0; j < MAX_CELLS_PER_ROW; j++) {
                // apply the style
                cells[i][j].setCellStyle(style);
            }
        }
    }

    /**
     * Writes the column headers and the row headers into the correct cells.
     * So it becomes clear what the data in the table is about.<br>
     * The column headers contain the file names and the topic and
     * the row headers contain the algorithm names.<br><br>
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private void writeRowAndColumnNamesOneSheet() {
        // add the topics
        cells[0][0].setCellValue("Benötigte Zeit");
        cells[11][0].setCellValue("Anzahl Vergleiche");
        cells[22][0].setCellValue("Anzahl Schreibzugriffe");
        cells[33][0].setCellValue("Speicherbedarf");

        // array containing al the names of the files containing the values wich are sorted.
        String[] files = {"InversTeilsortiert1000", "InversTeilsortiert10000", "InversTeilsortiert100000",
                "Ramdom1000", "Ramdom10000", "Ramdom100000",
                "Teilsortiert1000", "Teilsortiert10000", "Teilsortiert100000"};
        // add th file names as column headers
        for (int i = 0; i < 9; i++) {
            cells[0][(i+2)].setCellValue(files[i]);
            cells[11][(i+2)].setCellValue(files[i]);
            cells[22][(i+2)].setCellValue(files[i]);
            cells[33][(i+2)].setCellValue(files[i]);
        }

        // array containing all the names of the algorithms
        String[] algorithms = {"BinaryTreeSort", "BubbleSort", "HeapSort",
                "InsertionSort", "MergeSort", "QuickSort", "QuickSort2", "ShakerSort"};
        // add the algorithm names as row headers
        for (int i = 2; i < 36; i+=11) {
            for (int j = 0; j < 8; j++) {
                cells[i+j][0].setCellValue(algorithms[j]);
            }
        }
    }

    // MultipleSheet-Methods
    /**
     * <p>
     * Creates 9 rows to each of the 4 tables in the 4 Excel-Sheets and to each row 11 cells.<br>
     * Total of cells per sheet/table: 99<br>
     * Total of cells: 396<br>
     *
     * @apiNote
     * Used when the data is stored in four different sheets each containing one table.
     */
    public void initColumnsAndRowsInMultipleSheets() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < MAX_ROWS_MULTIPLE_SHEETS; j++) {
                // creating row
                rowies[i][j] = sheets[i].createRow(j);
                for (int k = 0; k < MAX_CELLS_PER_ROW; k++) {
                    // creating cell
                    cellies[i][j][k] = rowies[i][j].createCell(k);
                }
            }
        }
    }

    /**
     * <p>
     * Styles the rows, columns and cells of the ExcelFile given in the constructor.
     * It auto-sizes the columns, which contain text so the text doesn't overlap.
     * It sets the width of the 2nd column(in Excel column B), which is used to make the table look better.
     * It sets the height of the rows, which used to make the tables look better.
     * And it adds borders to te cells, which are used to create the tables.
     * The rows in between two tables will get no borders.<br>
     *
     * @apiNote
     * Used when the data is stored in four different sheets each containing one table.
     */
    private void styleRowsAndColumnsInMultipleSheets() {
        for (int i = 0; i < 4; i++) {
            // set auto-sizing to the necessary columns
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

            // set the width of the remaining column to 22 Pixels(measurement of Excel)
            sheets[i].setColumnWidth(1, 512);

            // set the height of the 2nd row on each sheet to 11 Points = 22 Pixels(measurement of Excel)
            rowies[i][1].setHeightInPoints(11);
        }

        // get the CellStyle for the borders
        XSSFCellStyle style = getBorderCellStyle();

        // apply the style to all cells off all rows of all sheets
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < MAX_ROWS_MULTIPLE_SHEETS; j++) {
                for (int k = 0; k < MAX_CELLS_PER_ROW; k++) {
                    // apply the style
                    cellies[i][j][k].setCellStyle(style);
                }
            }
        }
    }

    /**
     * Writes the column headers and the row headers into the correct cells.
     * So it becomes clear what the data in the table is about.<br>
     * The column headers contain the file names and the topic and
     * the row headers contain the algorithm names.<br><br>
     *
     * @apiNote
     * Used when the data is stored in four different sheets each containing one table.
     */
    private void writeRowAndColumnNamesMultipleSheet() {
        // add the topics
        cellies[0][0][0].setCellValue("Benötigte Zeit");
        cellies[1][0][0].setCellValue("Anzahl Vergleiche");
        cellies[2][0][0].setCellValue("Anzahl Schreibzugriffe");
        cellies[3][0][0].setCellValue("Speicherbedarf");

        // array containing al the names of the files containing the values wich are sorted.
        String[] files = {"InversTeilsortiert1000", "InversTeilsortiert10000", "InversTeilsortiert100000",
                "Ramdom1000", "Ramdom10000", "Ramdom100000",
                "Teilsortiert1000", "Teilsortiert10000", "Teilsortiert100000"};
        // add th file names as column headers
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < MAX_CELLS_PER_ROW; j++) {
                cellies[i][0][j].setCellValue(files[j-2]);
            }
        }

        // array containing all the names of the algorithms
        String[] algorithms = {"BinaryTreeSort", "BubbleSort", "HeapSort",
                "InsertionSort", "MergeSort", "QuickSort", "QuickSort2", "ShakerSort"};
        // add the algorithm names as row headers
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < MAX_ROWS_MULTIPLE_SHEETS; j++) {
                cellies[i][j][0].setCellValue(algorithms[j-2]);
            }
        }
    }
}