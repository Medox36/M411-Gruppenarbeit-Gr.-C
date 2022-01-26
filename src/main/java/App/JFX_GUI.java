package App;

import App.Utils.MusicPlayer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <h1>The JFX_GUI</h1>
 * <h3>Class responsible for the GUI of the application.</h3>
 * GUI was created with <a href="https://openjfx.io/">JavaFx</a>
 *
 * @author Lorenzo Giuntini (Medox36)
 * @author Andras Tarlos
 * @since 2022.01.12
 * @version 0.1.16
 */
public class JFX_GUI extends Application {
    private static final String iconLoc = Objects.requireNonNull(Main.class.getResource("/images/sort.png")).toString();

    // application stage is stored so that it can be shown and hidden based on system tray icon operations.
    private Stage stage;

    // label containing a text, which shows which algorithm sorts what file.
    private javafx.scene.control.Label label;

    private ProgressBar pb;

    private ProgressIndicator pi;

    private javafx.scene.control.Button volumeButton;

    private boolean volumeIsMuted;

    private java.awt.SystemTray tray;

    private java.awt.TrayIcon trayIcon;

    private javafx.scene.control.Button closeButton;

    private CheckBox checkBox;

    private MusicPlayer player;

    @Override
    public void start(Stage stage) {
        // stores a reference to the stage.
        this.stage = stage;
        player = new MusicPlayer();

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
        buttonBox.setPadding(new Insets(110, 10, 10, 30));
        buttonBox.setMinWidth(384);
        buttonBox.setPrefWidth(384);

        //
        HBox checkBoxHBox = new HBox();
        checkBoxHBox.setPadding(new Insets(88, 30, 10, 30));

        volumeIsMuted = false;

        //
        checkBox = new CheckBox("Save results in multiple sheets");
        checkBox.setSelected(true);

        //
        pb = new ProgressBar(0.0);
        pb.setMinSize(345,25);
        pb.setPrefSize(345, 25);
        pb.setStyle("-fx-accent: #f5b80f");

        //
        pi = new ProgressIndicator(0.0);
        pi.setMinSize(45, 45);
        pi.setStyle("-fx-accent: #f5b80f");

        //
        label = new javafx.scene.control.Label();
        label.setStyle("-fx-font-weight: bold");

        //
        AtomicReference<Image> img = new AtomicReference<>(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/volume_on_tiny.jpeg"))));
        AtomicReference<ImageView> view = new AtomicReference<>(new ImageView(img.get()));

        //
        javafx.scene.control.Button startButton = new javafx.scene.control.Button("Start");
        startButton.setMinSize(85, 30);
        startButton.setPrefSize(85,30);
        startButton.setOnAction(actionEvent -> {
            startButton.setVisible(false);
            checkBox.setVisible(false);
            volumeButton.setVisible(true);
            label.setText("initializing sorting");
            pb.setProgress(-1.0);
            pi.setProgress(-1.0);
            player.playElevatorMusic();
            Thread t = new Thread(new Sorting(this));
            t.setDaemon(true);
            t.start();
        });

        //
        volumeButton = new javafx.scene.control.Button();
        volumeButton.setVisible(false);
        volumeButton.setGraphic(view.get());
        volumeButton.setMinSize(40, 40);
        volumeButton.setPrefSize(50, 50);
        volumeButton.setOnAction(actionEvent -> {
            if (volumeIsMuted) {
                img.set(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/volume_on_tiny.jpeg"))));
                view.set(new ImageView(img.get()));
                volumeIsMuted = false;
                player.setPlayerMute(false);
            } else {
                img.set(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/volume_off_tiny.jpeg"))));
                view.set(new ImageView(img.get()));
                volumeIsMuted = true;
                player.setPlayerMute(true);
            }
            volumeButton.setGraphic(view.get());
        });

        //
        startButton.setStyle("-fx-background-color: #f5b80f; -fx-border-width: 1px; -fx-border-color: black; -fx-font-weight: bold");
        startButton.setOnMouseEntered(mouseEvent -> startButton.setStyle("-fx-background-color: #a5d24c; -fx-border-color: #62675b; -fx-font-weight: bold"));
        startButton.setOnMouseExited(mouseEvent -> startButton.setStyle("-fx-background-color: #f5b80f; -fx-border-color: black; -fx-font-weight: bold"));
        startButton.setOnMousePressed(mouseEvent -> startButton.setStyle("-fx-background-color: #8db242; -fx-border-color: #42463e; -fx-font-weight: bold"));

        //
        volumeButton.setStyle("-fx-padding: 40,0,0,0");

        //
        closeButton = new javafx.scene.control.Button("Close");
        closeButton.setMinSize(85, 30);
        closeButton.setPrefSize(85,30);
        closeButton.setOnAction(actionEvent -> close());

        //
        closeButton.setStyle("-fx-background-color: #f5b80f; -fx-border-width: 1px; -fx-border-color: black; -fx-font-weight: bold");
        closeButton.setOnMouseEntered(mouseEvent -> closeButton.setStyle("-fx-background-color: #d2604c; -fx-border-color: #5e5353; -fx-font-weight: bold"));
        closeButton.setOnMouseExited(mouseEvent -> closeButton.setStyle("-fx-background-color: #f5b80f; -fx-border-color: black; -fx-font-weight: bold"));
        closeButton.setOnMousePressed(mouseEvent -> closeButton.setStyle("-fx-background-color: #ab4e3e; -fx-border-color: #443c3c; -fx-font-weight: bold"));
        closeButton.setVisible(false);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        //
        progressBox.getChildren().addAll(pb, pi);
        labelBox.getChildren().add(label);
        buttonBox.getChildren().addAll(startButton, closeButton, region, volumeButton);
        checkBoxHBox.getChildren().add(checkBox);

        //
        Group root = new Group();
        root.getChildren().addAll(progressBox, labelBox, buttonBox, checkBoxHBox);

        //
        Scene scene = new Scene(root, Color.CADETBLUE);

        // add a Logo to tha stage
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sort.png"))));

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

        // exits program if window is closed
        stage.setOnCloseRequest(e -> {
            System.out.println("Exiting program...");
            close();
        });

        stage.setResizable(false);

        // set the scene of the stage
        stage.setScene(scene);

        showStage();
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
                tray = java.awt.SystemTray.getSystemTray();
                URL imageLoc = new URL(iconLoc);
                java.awt.Image image = ImageIO.read(imageLoc);
                trayIcon = new java.awt.TrayIcon(image);

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
                exitItem.addActionListener(e -> close());

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

    private void close() {
        Platform.exit();
        tray.remove(trayIcon);
    }

    /**
     * Sets a given text to the label, wich shows what algorithm sorts wich file.
     *
     * @param s Text to be shown in the label
     */
    public synchronized void setLabelText(String s) {
        label.setText(s);
    }

    /**
     * <p>
     * Sets the ProgressBar and ProgressIndicator to the given value.
     * <p>
     * The value being from 0.0 to 1.0.<br>
     * 0.0 = 0%
     * 1.0 = 100%
     *
     * @param progress value of the progress
     */
    public synchronized void setProgress(double progress) {
        pb.setProgress(progress);
        pi.setProgress(progress);
    }

    /**
     *
     * @return Whether the checkbox was selected before starting sorting or not
     */
    public synchronized boolean getCheckBoxState() {
        return checkBox.isSelected();
    }

    /**
     * makes the close-button visible again so the program can be exited by the user, without having to use the TracIcon
     */
    public synchronized void showCloseButton() {
        player.stopMusic();
        closeButton.setVisible(true);

    }

    public void createDialog(String s) {
        player.playRobertWeideMusic();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ExcelFile Waring");
        alert.setHeaderText("An Error occurred in context with the ExcelFile (.xlsx)");
        alert.setContentText(s);
        alert.getDialogPane().setMinWidth(505);
        alert.getDialogPane().setMinHeight(340);
        alert.showAndWait();
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