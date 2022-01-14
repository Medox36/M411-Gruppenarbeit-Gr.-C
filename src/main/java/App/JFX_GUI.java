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
 * @version 0.1.5
 */
public class JFX_GUI extends Application {
    private static final String iconLoc = Objects.requireNonNull(Main.class.getResource("/images/sort.png")).toString();

    /* das Anwendungsfenster(application stage) wird gespeichert, um das Anwendungsfenster zu verstecken und
       wieder anzuzeigen, je nach SystemTrayIcon operation.*/
    private Stage stage;

    @Override
    public void start(Stage stage) {
        // abspeichern der Referenz des Fensters(application stage)
        this.stage = stage;

        // sagt dem JavaFX system, dass es sich nicht beenden soll, wenn das letzte Fenster geschlossen wird.
        Platform.setImplicitExit(false);

        // fügt das TrayIcon dem SystemTray hinzu (benutzung von java.awt code, ausführung auf javax.swing Thread).
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

        Group root = new Group();

        Scene scene = new Scene(root,Color.CRIMSON);

        // dem Fenster wird ein Logo zugeteilt
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/sort.png"))));

        // umbenennung des Fensternamens
        stage.setTitle("Sorting Analytics");

        // Höhe und Breite werden definiert
        stage.setHeight(400);
        stage.setWidth(500);

        // das Fenster wird relativ zum Bildschirm zentriert
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setX((d.width-stage.getWidth())/2);
        stage.setY((d.height-stage.getHeight())/2);

        // dem Fenster wird die Scene zugeteilt
        stage.setScene(scene);

    }

    /**
     * Sets up a system tray icon for the application.
     */
    private void addAppToTray() {
        try {
            // sichergehen, das das java.awt.Toolkit initialisiert ist.
            java.awt.Toolkit.getDefaultToolkit();

            /* überprüfen, ob das SystemTray vom aktuellen Betriebssystem unterstützt wird.
               wenn nicht, beendet sich das Programm.*/
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // erstellen des SystemTrayIcon
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(iconLoc);
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            trayIcon.setImageAutoSize(true);

            // wenn der Benutzer einen Doppelklick auf das TrayIcon macht soll das Anwendungsfenster gezeigt werden.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            /* wenn der Benutzer das "show/hide" MenuItem auswählt soll das Fenster je nach aktuellem zustand
               sichtbar oder unsichtbar werden.*/
            java.awt.MenuItem openItem = new java.awt.MenuItem("show/hide");
            openItem.addActionListener(event -> {
                if (stage.isShowing()) {
                    Platform.runLater(this::hideStage);
                } else {
                    Platform.runLater(this::showStage);
                }
            });

            /* Die Konvention für TrayIcons scheint darin zu bestehen, das Standardsymbol zum Öffnen der
               Anwendungsfenster in Fettschrift festzulegen.*/
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            /*Um die Anwendung wirklich zu beenden, muss der Benutzer zum SystemTrayIcon gehen und die Option
              zum Beenden auswählen. Dadurch wird JavaFX heruntergefahren und das SystemTrayIcon entfernt
              (durch Entfernen des SystemTrayIcon wird auch AWT heruntergefahren).*/
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            // erstellen des PopupMenu für das SystemTrayIvon
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // hinzufügen des Anwendung-TrayIcon zum SystemTray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    /**
     * Macht das Hauptfenster sichtbar und stellt sicher, dass es vor allen anderen Fenster angezeigt wird.
     */
    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    /**
     * Macht das hauptfenster unsichtbar.
     */
    private void hideStage() {
        if (stage != null) {
            stage.hide();
        }
    }
}