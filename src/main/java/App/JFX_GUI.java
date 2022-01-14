package App;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.1.6
 */
public class JFX_GUI extends Application {
    private static final String iconLoc = Objects.requireNonNull(Main.class.getResource("/images/sort.png")).toString();

    // application stage is stored so that it can be shown and hidden based on system tray icon operations.
    private Stage stage;

    @Override
    public void start(Stage stage) {
        // stores a reference to the stage.
        this.stage = stage;

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false);

        // sets up the tray icon (using awt code run on the swing thread).
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

        Group root = new Group();

        Scene scene = new Scene(root,Color.CRIMSON);

        // add a Logo to tha stage
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/sort.png"))));

        // change the title of the stage
        stage.setTitle("Sorting Analytics");

        // define the minimum height and width of the stage
        stage.setMinHeight(400);
        stage.setMinWidth(500);

        // define height and width of the stage
        stage.setHeight(400);
        stage.setWidth(500);

        // set the position of the tage centered relative to the screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setX((d.width-stage.getWidth())/2);
        stage.setY((d.height-stage.getHeight())/2);

        // set the scene of the stage
        stage.setScene(scene);

    }

    /**
     * Sets up a system tray icon for the application.
     */
    private void addAppToTray() {
        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(iconLoc);
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            trayIcon.setImageAutoSize(true);

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            // if the user selects the default menu item (which includes the app name),
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("show/hide");
            openItem.addActionListener(e -> {
                if (stage.isShowing()) {
                    Platform.runLater(this::hideStage);
                } else {
                    Platform.runLater(this::showStage);
                }
            });

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shut down JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(e -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            // set up the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    /**
     * Shows the application stage and ensures that it is brought ot the front of all stages.
     */
    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    /**
     * Hides the application stage.
     */
    private void hideStage() {
        if (stage != null) {
            stage.hide();
        }
    }
}