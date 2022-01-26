package App;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

/**
 * <h1>MusicPlayer</h1>
 * This class is responsible for playing music during the sorting process.
 * The two music files can be found in "resources/music/"
 *
 * @author Tarlos Andras
 * @author Lorenzo Giuntini (Medo36)
 * @since 2022.01.26
 * @version 0.1
 */
public class MusicPlayer {
    private MediaPlayer player;

    /**
     * Starts to play the elevator music theme
     */
    public void playElevatorMusic() {
        Media media_elevator = new Media(Objects.requireNonNull(getClass().getResource("/music/elevator.mp3")).toString());
        player = new MediaPlayer(media_elevator);
        player.play();
    }

    /**
     * Stops the elevator music theme
     */
    public void stopElevatorMusic() {
        player.stop();
    }

    /**
     * Mutes or unmutes the elevator theme
     *
     * @param muted is a boolean value, with that you can mute or unmute the elevator music theme
     */
    public void setPlayerMute(boolean muted) {
        player.setMute(muted);
    }

    /**
     * Starts to play a seven seconds long music from robert_weide
     */
    public void playRobertWeideMusic() {
        Media media_robert = new Media(Objects.requireNonNull(getClass().getResource("/music/robert_weide.mp3")).toString());
        player = new MediaPlayer(media_robert);
        player.play();
    }
}
