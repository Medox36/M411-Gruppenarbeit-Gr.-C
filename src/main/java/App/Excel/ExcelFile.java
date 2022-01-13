package App.Excel;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.2
 */
public class ExcelFile extends File {

    public ExcelFile() throws URISyntaxException {
        super(Objects.requireNonNull(ExcelFile.class.getResource("/excel/Auswertung_Gr-C.xlsx")).toURI());
    }

    public ExcelFile(String pathname) {
        super(pathname);
    }

    public ExcelFile(URI uri) {
        super(uri);
    }
}
