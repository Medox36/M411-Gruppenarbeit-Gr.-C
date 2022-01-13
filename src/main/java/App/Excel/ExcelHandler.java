package App.Excel;

import java.io.IOException;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.2
 */
public class ExcelHandler {
    private ExcelFile excelFile;
    private ExcelReader excelReader;
    private ExcelWriter excelWriter;

    public ExcelHandler() {
        excelFile = new ExcelFile();
        try {
            excelReader = new ExcelReader(excelFile);
            excelWriter = new ExcelWriter(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
