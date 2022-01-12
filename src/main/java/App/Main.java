package App;

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

        //Application.launch(JFX_GUI.class, args);


        //Sorting

        new Sorting().start();
    }

}
