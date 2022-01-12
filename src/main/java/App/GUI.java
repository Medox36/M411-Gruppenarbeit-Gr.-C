package App;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.Objects;

/**
 *
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.12
 * @version 0.0.1
 */
public class GUI {

    public GUI(String args) {
        super(new ImageIcon(Objects.requireNonNull(Main.class.getResource("../../resources/images/sort.png"))));
        tray = SystemTray.getSystemTray();
    }

    public void startGui() {

    }

    private void addIfTrayIsSupported() {
        if (SystemTray.isSupported()) {
            try {
                SystemTray.getSystemTray().add(this);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else {
            runWithoutTray();
        }
    }

    private void runWithoutTray() {

    }
}