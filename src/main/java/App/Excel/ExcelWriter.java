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
 * @version 0.0.2
 */
public class ExcelWriter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelWriter(ExcelFile excelFile) throws IOException {
        workbook = new XSSFWorkbook(new FileInputStream(excelFile));
        sheet = workbook.getSheet("Auswertung");
    }

}
