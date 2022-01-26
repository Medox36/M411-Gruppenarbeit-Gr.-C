package App;

import javafx.application.Application;

/**
 * <h1>Main</h1>
 *
 * @author Andras Tarlos
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.2.1
 */
public class Main {

    public static void main(String[] args) {
        new Main().startApp(args);
    }

    public void startApp(String[] args) {
        if (args.length != 0) {
            if (args[0].equals("--help") || args[0].equals("-h")) {
                System.out.println("--no-gui\t- There will be no GUI shown");
            } else if (args[0].equals("--no-gui")) {
                System.out.println("--no-gui: No GUI will be shown");
                new Sorting().start();
            } else {
                System.out.println("Unrecognised option");
            }
        } else {
            Application.launch(JFX_GUI.class);
        }
    }
}