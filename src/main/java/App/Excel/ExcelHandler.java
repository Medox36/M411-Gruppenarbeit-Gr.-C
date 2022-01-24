package App.Excel;

import java.io.IOException;
import java.util.Vector;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.2.2
 */
public class ExcelHandler {
    private final ExcelWriter excelWriter;

    /**
     * default constructor
     */
    public ExcelHandler() {
        excelWriter = new ExcelWriter(false);
    }

    public ExcelHandler(boolean multipleSheets) {
        excelWriter = new ExcelWriter(multipleSheets);
    }

    public void initWriteAndFinish(Vector<long[]> data) {
        excelWriter.initFile();
        try {
            excelWriter.write(data);
        } catch (DataArraySyntaxException e) {
            e.printStackTrace();
        }
        try {
            excelWriter.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}