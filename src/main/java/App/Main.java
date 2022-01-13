package App;

import App.Excel.ExcelHandler;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.Vector;

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

        //new ExcelHandler();


        //Sorting

        //new Sorting().start();

        // Memory Testing
        MemoryCalculator mc = new MemoryCalculator();
        Vector<Integer> h = new Vector<>();
        for (int i = 0; i < 100; i++) {
            h.add(1999);
        }
        long k = mc.calcMem(h);
        System.out.println(k);
    }

}
