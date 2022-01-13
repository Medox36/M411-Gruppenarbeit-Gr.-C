package App.Excel;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.3
 */
public class ExcelHandler {
    private ExcelFile excelFile;
    private ExcelReader excelReader;
    private ExcelWriter excelWriter;

    public ExcelHandler() {
        try {
            excelFile = new ExcelFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            excelReader = new ExcelReader(excelFile);
            excelWriter = new ExcelWriter(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
