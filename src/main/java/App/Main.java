package App;

import App.Excel.ExcelFile;
import App.Excel.ExcelHandler;
import javafx.application.Application;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
        new Main().startApp(args);
    }

    public void startApp(String[] args) {
        if (args[0].equals("--help") || args[0].equals("-h"))
            System.out.println("--no-gui\t- There will be no GUI shown");
        else if (args[0].equals("--no-gui"))
            new Sorting().start();
        else {
            new Thread(() -> Application.launch(JFX_GUI.class)).start();
            new Sorting().start();
        }
        //GUI

        //new Thread(() -> Application.launch(JFX_GUI.class)).start();


        //Excel

        /*
        try {
            ExcelHandler excelHandler = new ExcelHandler(new ExcelFile(Objects.requireNonNull(Main.class.getResource("/excel/test.xlsx")).toURI()), true);
            excelHandler.initFile();
            excelHandler.writeAndFinish();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        */
        /*
        try {
            Desktop.getDesktop().open(new File(Objects.requireNonNull(Main.class.getResource("/excel/test.xlsx")).toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        */


        //Sorting

        new Sorting().start();
    }

}
