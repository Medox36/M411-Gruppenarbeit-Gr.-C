package App.Excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.3
 */
public class ExcelReader {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelReader(ExcelFile excelFile, boolean multipleSheets) throws IOException {
        workbook = new XSSFWorkbook(new FileInputStream(excelFile));
        sheet = workbook.getSheet("Auswertung");
    }

}
