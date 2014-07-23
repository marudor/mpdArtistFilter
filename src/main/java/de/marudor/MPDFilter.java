package de.marudor;

import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.Playlist;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.objects.MPDSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marudor on 20/07/14.
 */
public class MPDFilter {
    public static List<String> artistList = new ArrayList<String>();
    public static filterMode mode;
    public static enum filterMode {
        BLOCK_MODE,
        ALLOW_MODE,
    }

    public static boolean checkSong(Player player) throws MPDPlayerException {
        MPDSong song;
        if ((song = player.getCurrentSong()) == null) return true;
        return checkSong(player,song);
    }
    public static boolean checkSong(Player player, MPDSong song) throws MPDPlayerException {
        String artist = song.getArtistName().toLowerCase();
        System.err.println(artist);
        if (artistList.contains(artist)) {
            return action(player, artist);
        }
        return false;
    }

    public static boolean checkNextSong(MPD mpd) throws MPDPlaylistException, MPDPlayerException {
        Playlist playlist = mpd.getPlaylist();
        if (playlist == null || playlist.getSongList().toArray().length < 2) return false;
        MPDSong currentSong = playlist.getCurrentSong();
        if (currentSong == null || !playlist.getSongList().contains(currentSong)) return false;
        int songIndex = playlist.getSongList().indexOf(currentSong);
        MPDSong nextSong = playlist.getSongList().get((songIndex + 1) % playlist.getSongList().toArray().length);
        return checkSong(mpd.getPlayer(), nextSong);

    }

    private static boolean action(Player player, String artist) throws MPDPlayerException {
        switch (mode) {
            case BLOCK_MODE:
                player.playNext();
                System.err.println(String.format("Blocked {0}, playing next.", artist));
                return true;
            case ALLOW_MODE:
                return false;
        }
        return false;
    }
}
