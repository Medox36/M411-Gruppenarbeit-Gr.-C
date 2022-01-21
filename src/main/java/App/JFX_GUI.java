package App;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
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
 * @version 0.1.7
 */
public class JFX_GUI extends Application {
    private static final String iconLoc = Objects.requireNonNull(Main.class.getResource("/images/sort.png")).toString();

    // application stage is stored so that it can be shown and hidden based on system tray icon operations.
    private Stage stage;

    // label containing a text, wich shows which algorithm sorts what file.
    private javafx.scene.control.Label label;

    @Override
    public void start(Stage stage) {
        // stores a reference to the stage.
        this.stage = stage;

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false);

        // sets up the tray icon (using awt code run on the swing thread).
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

        //
        HBox progressBox = new HBox();
        progressBox.setPadding(new Insets(30, 30, 10, 30));

        //
        HBox labelBox = new HBox();
        labelBox.setPadding(new Insets(72, 30, 10, 30));

        //
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(110, 30, 10, 30));

        //
        ProgressBar pb = new ProgressBar(0.2);
        pb.setMinSize(345,25);
        pb.setPrefSize(345, 25);
        pb.setStyle("-fx-accent: #f5b80f");

        //
        ProgressIndicator pi = new ProgressIndicator(0.2);
        pi.setMinSize(45, 45);
        pi.setStyle("-fx-accent: #f5b80f");

        //
        label = new javafx.scene.control.Label();
        label.setText("Algorithm BinaryTreeSort is sorting InversTeilsortiert100000");
        label.setStyle("-fx-font-weight: bold");

        //
        javafx.scene.control.Button button = new javafx.scene.control.Button("Start");
        button.setMinSize(85, 30);
        button.setPrefSize(85,30);
        button.setOnAction(actionEvent -> {
            button.setVisible(false);
            // TODO start Sorting
        });

        //
        progressBox.getChildren().addAll(pb, pi);
        labelBox.getChildren().add(label);
        buttonBox.getChildren().add(button);

        //
        Group root = new Group();
        root.getChildren().addAll(progressBox, buttonBox, labelBox);

        //
        Scene scene = new Scene(root, Color.CADETBLUE);

        // add a Logo to tha stage
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/sort.png"))));

        // change the title of the stage
        stage.setTitle("Sorting Analytics");

        // define the minimum height and width of the stage
        stage.setMinHeight(220);
        stage.setMinWidth(465);

        // define height and width of the stage
        stage.setHeight(220);
        stage.setWidth(465);

        // set the position of the tage centered relative to the screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setX((d.width-stage.getWidth())/2);
        stage.setY((d.height-stage.getHeight())/2);

        // set the scene of the stage
        stage.setScene(scene);

        if (!java.awt.SystemTray.isSupported()) {
            showStage();
        }
    }

    /**
     * Sets up a system TrayIcon for the application.
     */
    private void addAppToTray() {
        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support. !java.awt.SystemTray.isSupported()
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, starting application in non-tray-mode.");
                Platform.setImplicitExit(true);
            } else {
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
                java.awt.MenuItem openItem = new java.awt.MenuItem("show");
                openItem.addActionListener(e -> {
                    if (stage.isShowing()) {
                        Platform.runLater(this::hideStage);
                        setTrayMenuItemText(openItem, "show");
                    } else {
                        Platform.runLater(this::showStage);
                        setTrayMenuItemText(openItem, "hide");
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
            }
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    /**
     * Sets a given text to the label, wich shows what algorithm sorts wich file.
     *
     * @param s Text to be shown in the label
     */
    public void setLabelText(String s) {
        label.setText(s);
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

    /**
     * Sets the text of a given MenuItem
     *
     * @param item MenuItem of which the text wil be changed
     * @param s change to this text
     */
    private void setTrayMenuItemText(MenuItem item,String s) {
        item.setLabel(s);
    }
}