package App.Excel;

import java.io.File;
import java.io.IOException;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.2.1
 */
public class ExcelFile extends File {

    /**
     * default constructor
     * will create the default file Auswertung_Gr-C.xlsx
     *
     * @throws IOException when the program was unable to create the new file
     */
    public ExcelFile() throws IOException {
        super("M411_LB2_GruppeC.xlsx");
    }
}