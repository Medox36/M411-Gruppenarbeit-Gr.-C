package App.Excel;

import java.io.File;
import java.net.URI;
import java.util.Objects;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.2
 */
public class ExcelFile extends File {

    public ExcelFile() {
        super(Objects.requireNonNull(ExcelFile.class.getResource("../../excel/Auswertung_Gr-C.xlsx")).toString());
    }

    public ExcelFile(String pathname) {
        super(pathname);
    }

    public ExcelFile(URI uri) {
        super(uri);
    }
}
