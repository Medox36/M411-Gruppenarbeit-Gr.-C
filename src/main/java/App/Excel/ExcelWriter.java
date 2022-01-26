package App.Excel;

import App.Sorting;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Vector;

/**
 * <h1>Excel Writer</h1>
 * <h3>Class used to create Excel files (.xlsx), by using the Apache POI</h3>
 * <a href="https://poi.apache.org/">Apache POI</a>
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.3.3
 */
public class ExcelWriter {

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
     * XSSFCell-Array containing all 390 cells of the sheet.<br><br>
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
     * XSSFCell-Array containing total of 360 cells, 90 per sheet/table, 10 per row.<br>
     * First array-dimension is the identifier for the sheet, the second array-dimension is the identifier for the row and
     * the third array-dimension is the cell, in the row of a sheet.
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
    private final int MAX_ROWS_ONE_SHEET = 39;

    /**
     * The maximum of rows in one of four sheets.
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private final int MAX_ROWS_MULTIPLE_SHEETS = 9;

    /**
     * The maximum of cells there can be in a row.
     */
    private final int MAX_CELLS_PER_ROW = 10;


    public ExcelWriter(boolean multipleSheets) {
        workbook = new XSSFWorkbook();
        this.multipleSheets = multipleSheets;
        if (multipleSheets) {
            sheets = new XSSFSheet[4];
            rowies = new XSSFRow[4][MAX_ROWS_MULTIPLE_SHEETS];
            cellies = new XSSFCell[4][MAX_ROWS_MULTIPLE_SHEETS][MAX_CELLS_PER_ROW];
            sheets[0] = workbook.createSheet("Benötigte Zeit (Nanosekunden)");
            sheets[1] = workbook.createSheet("Anzahl Vergleiche");
            sheets[2] = workbook.createSheet("Anzahl Schreibzugriffe");
            sheets[3] = workbook.createSheet("Speicherbedarf (Bits)");
        } else {
            sheets = new XSSFSheet[1];
            rows = new XSSFRow[MAX_ROWS_ONE_SHEET];
            cells = new XSSFCell[MAX_ROWS_ONE_SHEET][MAX_CELLS_PER_ROW];
            sheets[0] = workbook.createSheet("Auswertung");
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
     * <p>
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
     * <p>
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

        //rows, skipping row 0
        for (int i = 1; i < MAX_ROWS_ONE_SHEET; i++) {
            // further rows to skip
            if (i == 9||i == 10||i == 19||i == 20||i == 29||i == 30) {
                continue;
            }
            // cell, skipping cell 0 and 1
            for (int j = 1; j < MAX_CELLS_PER_ROW; j++) {
                long[] arr = data.get(k);
                // check if the syntax logic for the arrays has been followed correctly
                if (arr[0] != k) {
                    throw new DataArraySyntaxException();
                }
                cells[i][j].setCellValue(arr[1]);
                k++;
            }
        }
    }

    /**
     * <p>
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

        // sheets
        for (int i = 0; i < 4; i++) {
            // rows, skipping row 0
            for (int j = 1; j < MAX_ROWS_MULTIPLE_SHEETS; j++) {
                // cells, skipping cell 0
                for (int k = 1; k < MAX_CELLS_PER_ROW; k++) {
                    long[] arr = data.get(h);
                    // check if the syntax logic for the arrays has been followed correctly
                    if (arr[0] != h) {
                        throw new DataArraySyntaxException();
                    }
                    cellies[i][j][k].setCellValue(arr[1]);
                    h++;
                }
            }
        }
    }

    /**
     * <p>
     * Will write the changes to the default Auswertung_Gr-C.xlsx file.
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
        OutputStream out = new FileOutputStream("M411_LB2_GruppeC.xlsx");
        workbook.write(out);
        out.close();
        workbook.close();
    }

    /**
     * <p>
     * Creates a XSSFCellStyle object which contains styling of the borders.
     * <p>
     * Border on the Bottom, the Top, the Right and the Left are all thin, black borders.
     * <p>
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

    /**
     * <p>
     * Creates a XSSFCellStyle object which contains styling of the borders and a background colour.
     * <p>
     * Border on the Bottom, the Top, the Right and the Left are all thin, black borders.
     * <p>
     * XSSFCellStyle can be used to apply a Style to cells.<br><br>
     *
     * @param colour Index of the colour for the background from IndexedColours.
     * @return XSSFCellStyle object containing border styling
     *
     * @see IndexedColors
     */
    private XSSFCellStyle getColorCellStyle(short colour) {
        // create a new CellStyle
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        // get the index for the colour black
        short black = IndexedColors.BLACK.getIndex();
        // bottom border
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(black);
        // right border
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(black);
        // left border
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(black);
        // top border
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(black);

        // set the background colour to the given index
        // Note: yes we know the method called has the word ForegroundColour in it, but it only works this way
        cellStyle.setFillForegroundColor(colour);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // return the CellStyle
        return cellStyle;
    }

    // OneSheet-Methods
    /**
     * <p>
     * Creates 39 rows and to each row 10 cells.
     * Creates all rows and cells in one Excel-Sheet.<br>
     * Total of cells: 390<br>
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
     * Styles the rows, columns and cells.
     * It auto-sizes the columns, which contain text so the text doesn't overlap.
     * It adds borders and colour to te cells, which are used to create the tables.<br><br>
     *
     * @apiNote
     * Used when the data is stored in one sheet containing multiple tables.<br>
     * The rows in between two tables will get no borders.
     */
    private void styleRowsAndColumnsInOneSheet() {
        // auto-size the columns
        sheets[0].autoSizeColumn(0);
        sheets[0].autoSizeColumn(1);
        sheets[0].autoSizeColumn(2);
        sheets[0].autoSizeColumn(3);
        sheets[0].autoSizeColumn(4);
        sheets[0].autoSizeColumn(5);
        sheets[0].autoSizeColumn(6);
        sheets[0].autoSizeColumn(7);
        sheets[0].autoSizeColumn(8);
        sheets[0].autoSizeColumn(9);
        sheets[0].autoSizeColumn(10);

        // get the CellStyle for the borders
        XSSFCellStyle style = getBorderCellStyle();

        // apply the style to all cells off all rows, except for rows 9, 19 and 29
        for (int i = 0; i < MAX_ROWS_ONE_SHEET; i++) {
            // leave out rows 9, 19 and 29
            if (i == 9||i == 19||i == 29) {
                continue;
            }
            for (int j = 0; j < MAX_CELLS_PER_ROW; j++) {
                // apply the style
                cells[i][j].setCellStyle(style);
            }
        }

        // get the CellStyle for colouring the cells blue
        XSSFCellStyle blueColour = getColorCellStyle(IndexedColors.AQUA.getIndex());

        // get the CellStyle for colouring the cells grey
        XSSFCellStyle grayColour = getColorCellStyle(IndexedColors.GREY_40_PERCENT.getIndex());

        // get the CellStyle for colouring the cells light grey
        XSSFCellStyle lightGrayColour = getColorCellStyle(IndexedColors.GREY_25_PERCENT.getIndex());

        // apply the colour to the cells containing the topics
        cells[0][0].setCellStyle(blueColour);
        cells[10][0].setCellStyle(blueColour);
        cells[20][0].setCellStyle(blueColour);
        cells[30][0].setCellStyle(blueColour);

        // apply the colour to the right cells
        for (int i = 0; i < MAX_ROWS_ONE_SHEET; i++) {
            for (int j = 0; j < MAX_CELLS_PER_ROW; j++) {
                // apply to row 0, 10, 20 and 30
                if (i == 0||i == 10||i == 20||i == 30) {
                    if (j % 2 == 0) {
                        cells[i][j].setCellStyle(grayColour);
                    } else {
                        cells[i][j].setCellStyle(lightGrayColour);
                    }
                }
            }
            // apply to all rows except 0, 9, 10, 20 and 30
            if (i != 0 && i !=9 && i != 10 && i != 19 && i !=20 && i !=29 && i != 30) {
                if (i % 2 == 0) {
                    cells[i][0].setCellStyle(grayColour);
                } else {
                    cells[i][0].setCellStyle(lightGrayColour);
                }
            }
        }
    }

    /**
     * <p>
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
        cells[0][0].setCellValue("Benötigte Zeit (Nanosekunden)");
        cells[10][0].setCellValue("Anzahl Vergleiche");
        cells[20][0].setCellValue("Anzahl Schreibzugriffe");
        cells[30][0].setCellValue("Speicherbedarf (Bits)");

        // array containing al the names of the files containing the values wich are sorted.
        String[] files = {"InversTeilsortiert1000", "InversTeilsortiert10000", "InversTeilsortiert100000",
                "Random1000", "Random10000", "Random100000",
                "Teilsortiert1000", "Teilsortiert10000", "Teilsortiert100000"};
        // add th file names as column headers
        for (int i = 1; i < 10; i++) {
            cells[0][(i)].setCellValue(files[i-1]);
            cells[10][(i)].setCellValue(files[i-1]);
            cells[20][(i)].setCellValue(files[i-1]);
            cells[30][(i)].setCellValue(files[i-1]);
        }

        // array containing all the names of the algorithms
        String[] algorithms = {"BinaryTreeSort", "BubbleSort", "HeapSort",
                "InsertionSort", "MergeSort", "QuickSort (Pivot am Anfang)", "QuickSort2 (Random Pivot)", "ShakerSort"};
        // add the algorithm names as row headers
        for (int i = 1; i < 34; i+=10) {
            for (int j = 0; j < 8; j++) {
                cells[i+j][0].setCellValue(algorithms[j]);
            }
        }
    }

    // MultipleSheet-Methods
    /**
     * <p>
     * Creates 9 rows to each of the 4 tables in the 4 Excel-Sheets and to each row 10 cells.<br>
     * Total of cells per sheet/table: 90<br>
     * Total of cells: 360<br>
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
     * Styles the rows, columns and cells.
     * It auto-sizes the columns, which contain text so the text doesn't overlap.
     * It adds borders and colour to te cells, which are used to create the tables.
     * And it adds background colour to the cells in the top left corner wich contain the topic.<br>
     *
     * @apiNote
     * Used when the data is stored in four different sheets each containing one table.
     */
    private void styleRowsAndColumnsInMultipleSheets() {
        for (int i = 0; i < 4; i++) {
            // auto-size the columns
            sheets[i].autoSizeColumn(0);
            sheets[i].autoSizeColumn(1);
            sheets[i].autoSizeColumn(2);
            sheets[i].autoSizeColumn(3);
            sheets[i].autoSizeColumn(4);
            sheets[i].autoSizeColumn(5);
            sheets[i].autoSizeColumn(6);
            sheets[i].autoSizeColumn(7);
            sheets[i].autoSizeColumn(8);
            sheets[i].autoSizeColumn(9);
            sheets[i].autoSizeColumn(10);
        }

        // get the CellStyle for the borders
        XSSFCellStyle style = getBorderCellStyle();

        // get the CellStyles for colouring the cells
        XSSFCellStyle blueColour = getColorCellStyle(IndexedColors.AQUA.getIndex());

        // get the CellStyle for colouring the cells grey
        XSSFCellStyle grayColour = getColorCellStyle(IndexedColors.GREY_40_PERCENT.getIndex());

        // get the CellStyle for colouring the cells light grey
        XSSFCellStyle lightGrayColour = getColorCellStyle(IndexedColors.GREY_25_PERCENT.getIndex());

        // apply the right styles to the cells
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < MAX_ROWS_MULTIPLE_SHEETS; j++) {
                for (int k = 0; k < MAX_CELLS_PER_ROW; k++) {
                    // apply the style
                    if (j == 0 && k > 0) {
                        if (k % 2 == 0) {
                            cellies[i][j][k].setCellStyle(grayColour);
                        } else {
                            cellies[i][j][k].setCellStyle(lightGrayColour);
                        }
                    } else if (j > 0 && k < 1){
                        if (j % 2 == 0) {
                            cellies[i][j][k].setCellStyle(grayColour);
                        } else {
                            cellies[i][j][k].setCellStyle(lightGrayColour);
                        }
                    } else {
                        cellies[i][j][k].setCellStyle(style);
                    }
                }
            }
            // apply the colour to the cells containing the topics
            cellies[i][0][0].setCellStyle(blueColour);
        }
    }

    /**
     * <p>
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
        cellies[0][0][0].setCellValue("Benötigte Zeit (Nanosekunden)");
        cellies[1][0][0].setCellValue("Anzahl Vergleiche");
        cellies[2][0][0].setCellValue("Anzahl Schreibzugriffe");
        cellies[3][0][0].setCellValue("Speicherbedarf (Bits)");

        // array containing al the names of the files containing the values wich are sorted.
        String[] files = {"InversTeilsortiert1000", "InversTeilsortiert10000", "InversTeilsortiert100000",
                "Random1000", "Random10000", "Random100000",
                "Teilsortiert1000", "Teilsortiert10000", "Teilsortiert100000"};
        // add th file names as column headers
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < MAX_CELLS_PER_ROW; j++) {
                cellies[i][0][j].setCellValue(files[j-1]);
            }
        }

        // array containing all the names of the algorithms
        String[] algorithms = {"BinaryTreeSort", "BubbleSort", "HeapSort",
                "InsertionSort", "MergeSort", "QuickSort (Pivot am Anfang)", "QuickSort (Random Pivot)", "ShakerSort"};
        // add the algorithm names as row headers
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < MAX_ROWS_MULTIPLE_SHEETS; j++) {
                cellies[i][j][0].setCellValue(algorithms[j-1]);
            }
        }
    }
}