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

    /**
     * Reference to the application stage.<br>
     * Stored so it can be shown and hidden based on TrayIcon operations,
     *
     * @see Stage
     */
    private Stage stage;

    /**
     * Label containing a text, which shows which algorithm sorts what file.
     *
     * @see javafx.scene.control.Label
     */
    private javafx.scene.control.Label label;

    /**
     * Reference to the ProgressBar
     *
     * @see ProgressBar
     */
    private ProgressBar pb;

    /**
     * Reference to the ProgressIndicator
     *
     * @see ProgressIndicator
     */
    private ProgressIndicator pi;

    /**
     * Reference to mute Button
     *
     * @see javafx.scene.control.Button
     */
    private javafx.scene.control.Button muteButton;

    /**
     * Boolean for knowing whither or not the audio is muted by the user
     */
    private boolean volumeIsMuted;

    /**
     * Reference to the SystemTray
     *
     * @see SystemTray
     */
    private java.awt.SystemTray tray;

    /**
     * Reference the TrayIcon
     *
     * @see TrayIcon
     */
    private java.awt.TrayIcon trayIcon;

    /**
     * Reference to the close Button
     *
     * @see javafx.scene.control.Button
     */
    private javafx.scene.control.Button closeButton;

    /**
     * Reference to the CheckBox
     *
     * @see CheckBox
     */
    private CheckBox checkBox;

    /**
     * Reference to the MusicPlayer
     *
     * @see MusicPlayer
     */
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

        // create a HBox for both the progressbar and progress indicator
        HBox progressBox = new HBox();
        progressBox.setPadding(new Insets(30, 30, 10, 30));

        // create a HBox for the (text) label
        HBox labelBox = new HBox();
        labelBox.setPadding(new Insets(72, 30, 10, 30));

        // create a HBox for all the buttons and set the width for it
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(110, 10, 10, 30));
        buttonBox.setMinWidth(384);
        buttonBox.setPrefWidth(384);

        // create a HBox for the checkbox
        HBox checkBoxHBox = new HBox();
        checkBoxHBox.setPadding(new Insets(88, 30, 10, 30));

        volumeIsMuted = false;

        // create a checkbox so the user can decide whither or not he wants the results in multiple sheets
        checkBox = new CheckBox("Save results in multiple sheets");
        checkBox.setSelected(true);

        // create progress bar and set the size and the colour
        pb = new ProgressBar(0.0);
        pb.setMinSize(345,25);
        pb.setPrefSize(345, 25);
        pb.setStyle("-fx-accent: #f5b80f");

        // crate a progress indicator and set the size and the color
        pi = new ProgressIndicator(0.0);
        pi.setMinSize(45, 45);
        pi.setStyle("-fx-accent: #f5b80f");

        // create a label to show text, wich contains information about which algorithm sorts what file
        label = new javafx.scene.control.Label();
        label.setStyle("-fx-font-weight: bold");

        // create AtomicReferences for the Images used in the mute button
        AtomicReference<Image> img = new AtomicReference<>(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/volume_on_tiny.jpeg"))));
        AtomicReference<ImageView> view = new AtomicReference<>(new ImageView(img.get()));

        // create the start button, set the size and what the action event should do
        javafx.scene.control.Button startButton = new javafx.scene.control.Button("Start");
        startButton.setMinSize(85, 30);
        startButton.setPrefSize(85,30);
        startButton.setOnAction(actionEvent -> {
            // hide both start button and checkBox
            startButton.setVisible(false);
            checkBox.setVisible(false);
            // show the mute button
            muteButton.setVisible(true);
            // set the text of the label so the user knows the program is initializing
            label.setText("initializing sorting");
            // negative progress sets bot progress bar and progress indicator into loading state
            // the both will show a loading animation
            pb.setProgress(-1.0);
            pi.setProgress(-1.0);
            // start playing the elevator music
            player.playElevatorMusic();
            // create the sorting thread
            Thread t = new Thread(new Sorting(this));
            // set the thread to be daemon to it terminates when the GUI is closed
            t.setDaemon(true);
            // start the thread
            t.start();
        });

        // create the mute button, set the size and what the action event should do
        muteButton = new javafx.scene.control.Button();
        muteButton.setVisible(false);
        muteButton.setGraphic(view.get());
        muteButton.setMinSize(40, 40);
        muteButton.setPrefSize(50, 50);
        muteButton.setOnAction(actionEvent -> {
            if (volumeIsMuted) {
                // sets the mute button to volume on state -> change image and boolean
                img.set(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/volume_on_tiny.jpeg"))));
                view.set(new ImageView(img.get()));
                volumeIsMuted = false;
                player.setPlayerMute(false);
            } else {
                // sets the mute button to volume off state -> change image and boolean
                img.set(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/volume_off_tiny.jpeg"))));
                view.set(new ImageView(img.get()));
                volumeIsMuted = true;
                player.setPlayerMute(true);
            }
            muteButton.setGraphic(view.get());
        });

        // set the styling of the start button
        // style of the normal state
        startButton.setStyle("-fx-background-color: #f5b80f; -fx-border-width: 1px; -fx-border-color: black; -fx-font-weight: bold");
        // style when the mouse hovers over the button
        startButton.setOnMouseEntered(mouseEvent -> startButton.setStyle("-fx-background-color: #a5d24c; -fx-border-color: #62675b; -fx-font-weight: bold"));
        // reset the style to the normal state when the mouse moves away
        startButton.setOnMouseExited(mouseEvent -> startButton.setStyle("-fx-background-color: #f5b80f; -fx-border-color: black; -fx-font-weight: bold"));
        // style when the button is clicked
        startButton.setOnMousePressed(mouseEvent -> startButton.setStyle("-fx-background-color: #8db242; -fx-border-color: #42463e; -fx-font-weight: bold"));

        // set the padding of the mute button
        muteButton.setStyle("-fx-padding: 40,0,0,0");

        // set the
        closeButton = new javafx.scene.control.Button("Close");
        closeButton.setMinSize(85, 30);
        closeButton.setPrefSize(85,30);
        closeButton.setOnAction(actionEvent -> close());

        // set the styling of the close button
        // style of the normal state
        closeButton.setStyle("-fx-background-color: #f5b80f; -fx-border-width: 1px; -fx-border-color: black; -fx-font-weight: bold");
        // style when the mouse hovers over the button
        closeButton.setOnMouseEntered(mouseEvent -> closeButton.setStyle("-fx-background-color: #d2604c; -fx-border-color: #5e5353; -fx-font-weight: bold"));
        // reset the style to the normal state when the mouse moves away
        closeButton.setOnMouseExited(mouseEvent -> closeButton.setStyle("-fx-background-color: #f5b80f; -fx-border-color: black; -fx-font-weight: bold"));
        // style when the button is clicked
        closeButton.setOnMousePressed(mouseEvent -> closeButton.setStyle("-fx-background-color: #ab4e3e; -fx-border-color: #443c3c; -fx-font-weight: bold"));
        // hide the button
        closeButton.setVisible(false);

        // crate new region, used to move mute button to the right
        Region region = new Region();
        // set the priority of the region
        HBox.setHgrow(region, Priority.ALWAYS);

        // add the progress bar and progress indicator to the progressBox
        progressBox.getChildren().addAll(pb, pi);
        // add the label to the labelBox
        labelBox.getChildren().add(label);
        // add all the buttons to the buttonBox
        buttonBox.getChildren().addAll(startButton, closeButton, region, muteButton);
        // add the checkbox to the checkBockHBox
        checkBoxHBox.getChildren().add(checkBox);

        // create a new group and add all HBoxes
        Group root = new Group();
        root.getChildren().addAll(progressBox, labelBox, buttonBox, checkBoxHBox);

        // create a scene and set its background color
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

        // make the stage not resizable
        stage.setResizable(false);

        // set the scene of the stage
        stage.setScene(scene);

        // show the stage on the screen
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

                // make sure the image is showed correctly
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

    /**
     * Closes the application
     */
    private void close() {
        // close the JavaFX GUI
        Platform.exit();
        // remove the TrayIcon of the application from the SystemTray
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
     * Makes the close-button visible again so the program can be exited by the user, without having to use the TracIcon
     */
    public synchronized void showCloseButton() {
        // stops the currently playing music
        player.stopMusic();
        closeButton.setVisible(true);
    }

    /**
     * Creates an Alert window (javax.swing.* equivalent of Dialog) and shows the error message given as parameter.
     *
     * @param s error message to be shown in the Alert window
     */
    public void createDialog(String s) {
        // play the fail music
        player.playRobertWeideMusic();
        // create the Alert window of type ERROR
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