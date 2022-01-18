package App.Excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.13
 */
public class ExcelWriter {

    /**
     * Reference to the ExcelFile which the data will be written to.
     *
     * @see ExcelFile
     */
    private ExcelFile excelFile;

    /**
     *
     * @see org.apache.poi.xssf.usermodel.XSSFWorkbook
     */
    private XSSFWorkbook workbook;

    /**
     * XSSFSheet-Array containing all the sheets,
     * either containing 1 sheet, when all data is in one sheet
     * or containing 4 sheets, when the data is stored in multiple sheets each containing one table.
     *
     * @see org.apache.poi.xssf.usermodel.XSSFSheet
     */
    private XSSFSheet[] sheets;

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
     *
     */
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

    /**
     * Removes all the existing sheets of the given ExcelFile in the constructor
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
            //TODO before styling the data from the algorithms should be written into the tables.
            styleRowsAndColumnsInMultipleSheets();
        } else {
            initColumnsAndRowsInOneSheet();
            writeRowAndColumnNamesOneSheet();
            //TODO before styling the data from the algorithms should be written into the tables.
            styleRowsAndColumnsInOneSheet();
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
     * Used if all Algorithms sort all Files<br>
     * Used when the data is stored in one sheet containing multiple tables.
     */
    private void initColumnsAndRowsInOneSheet() {
        for (int i = 0; i < 39; i++) {
            // creating row
            rows[i] = sheets[0].createRow(i);
            for (int j = 0; j < 11; j++) {
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
     * Used if all Algorithms sort all Files.<br>
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

        // set the height of the selected rows 1, 11, 21 and 31 to 11 Points = 22 Pixels(measurement of Excel)
        for (int i = 0; i < 39; i++) {
            if (i == 1 || i == 11 || i == 21 || i == 31) {
                rows[i].setHeightInPoints(11);
            }
        }

        // get the CellStyle for the borders
        XSSFCellStyle style = getBorderCellStyle();

        // apply the style to all cells off all rows, except for rows 9, 19 and 29
        for (int i = 0; i < 39; i++) {
            // leave out rows 9, 19 and 29
            if (i == 9||i == 19 ||i == 29) {
                continue;
            }
            for (int j = 0; j < 11; j++) {
                // apply the style
                cells[i][j].setCellStyle(style);
            }
        }
    }

    /**
     *
     * @apiNote
     * Used if all Algorithms sort all Files.<br>
     * Used when the data is stored in one sheet containing multiple tables.
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
     * <p>
     * Creates 9 rows to each of the 4 tables in the 4 Excel-Sheets and to each row 11 cells.<br>
     * Total of cells per sheet/table: 99<br>
     * Total of cells: 396<br>
     *
     * @apiNote
     * Used if all Algorithms sort all Files.<br>
     * Used when the data is stored in four different sheets each containing one table.
     */
    public void initColumnsAndRowsInMultipleSheets() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                // creating row
                rowies[i][j] = sheets[i].createRow(j);
                for (int k = 0; k < 11; k++) {
                    // creating cell
                    cellies[i][j][k] = rowies[i][j].createCell(k);
                }
            }
        }
    }

    /**
     *
     *
     * @apiNote
     * Used if all Algorithms sort all Files.<br>
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
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 11; k++) {
                    // apply the style
                    cellies[i][j][k].setCellStyle(style);
                }
            }
        }
    }

    /**
     *
     *
     * @apiNote
     * Used if all Algorithms sort all Files.<br>
     * Used when the data is stored in four different sheets each containing one table.
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