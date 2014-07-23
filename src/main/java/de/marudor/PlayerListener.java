package de.marudor;

import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.events.PlayerChangeEvent;
import org.bff.javampd.events.PlayerChangeListener;
import org.bff.javampd.exception.MPDPlayerException;

/**
 * Created by marudor on 20/07/14.
 */
public class PlayerListener implements PlayerChangeListener {
    private MPD mpd;
    public PlayerListener(MPD mpd) {
        this.mpd=mpd;
    }

    @Override
    public void playerChanged(PlayerChangeEvent playerChangeEvent) {
        PlayerChangeEvent.Event event = playerChangeEvent.getEvent();
        System.err.println(event);
        System.err.println(playerChangeEvent.getMsg());
        Player player = (Player) playerChangeEvent.getSource();
        switch(event) {
            case PLAYER_STOPPED:
                break;
            case PLAYER_PAUSED:
                break;
            case PLAYER_STARTED:
            case PLAYER_NEXT:
            case PLAYER_PREVIOUS:
            case PLAYER_SONG_SET:
            case PLAYER_UNPAUSED:
                try {
                    MPDFilter.checkSong(player);
                } catch (MPDPlayerException e) {
                    e.printStackTrace();
                }
            case PLAYER_MUTED:
                break;
            case PLAYER_UNMUTED:
                break;
            case PLAYER_SEEKING:
                break;
        }
    }
}
