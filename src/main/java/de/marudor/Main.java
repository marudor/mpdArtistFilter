package de.marudor;


import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDResponseException;

import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws MPDConnectionException, InterruptedException, MPDPlayerException {
        if (args.length < 2) return;
        switch (args[0].toLowerCase()) {
            case "1":
            case "allow":
                MPDFilter.mode = MPDFilter.filterMode.ALLOW_MODE;
                break;
            case "2":
            case "block":
            case "disallow":
                MPDFilter.mode = MPDFilter.filterMode.BLOCK_MODE;
                break;
        }

        for (int i = 1; i < args.length;i++) MPDFilter.artistList.add(args[i].toLowerCase());
        System.err.println(MPDFilter.artistList);
        MPD mpd = null;
        Player player = null;
        try {
            mpd = new MPD.Builder().server("mpd").build();
            System.out.println("Version:"+mpd.getVersion());
            player = mpd.getPlayer();
            player.addPlayerChangeListener(new PlayerListener(mpd));
            if (!MPDFilter.checkSong(player))
            MPDFilter.checkNextSong(mpd);
            //mpd.close();
        } catch(MPDConnectionException e) {
            System.out.println("Error Connecting:" + e.getMessage());
            e.printStackTrace();
        } catch (MPDResponseException e) {
            System.out.println("Error Connecting:"+e.getMessage());
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



        while (true) {
            Thread.sleep(3000);
        }
    }
}
