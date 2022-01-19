package App.Excel;

import App.DataArraySyntaxException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Vector;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.6
 */
public class ExcelHandler {
    private ExcelFile excelFile;
    private ExcelReader excelReader;
    private ExcelWriter excelWriter;

    /**
     * default constructor
     */
    public ExcelHandler() {
        try {
            excelFile = new ExcelFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            excelReader = new ExcelReader(excelFile, false);
            excelWriter = new ExcelWriter(excelFile, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * used if another Excel-File should be used
     *
     * @param excelFile an .xlsx-File
     */
    public ExcelHandler(ExcelFile excelFile, boolean multipleSheets) {
        this.excelFile = excelFile;
        try {
            excelReader = new ExcelReader(excelFile, multipleSheets);
            excelWriter = new ExcelWriter(excelFile, multipleSheets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initFile() {
        excelWriter.initFile();
    }

    public void write(Vector<long[]> data) {
        try {
            excelWriter.write(data);
        } catch (DataArraySyntaxException e) {
            e.printStackTrace();
        }
    }

    public void writeAndFinish() {
        try {
            excelWriter.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
