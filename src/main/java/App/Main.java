package App;

import App.Excel.ExcelFile;
import App.Excel.ExcelHandler;
import javafx.application.Application;

import java.net.URISyntaxException;
import java.util.Objects;

/**
 *
 *
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.2.0
 */
public class Main {

    public static void main(String[] args) {
        new Main().startApp();
    }

    public void startApp() {
        //GUI

        //new Thread(() -> Application.launch(JFX_GUI.class)).start();


        //Excel

        /*
        try {
            ExcelHandler excelHandler = new ExcelHandler(new ExcelFile(Objects.requireNonNull(Main.class.getResource("/excel/test.xlsx")).toURI()), false);
            excelHandler.initFile();
            excelHandler.writeAndFinish();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        */


        //Sorting

        new Sorting().start();
    }

}
