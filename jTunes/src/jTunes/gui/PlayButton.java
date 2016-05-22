package jTunes.gui;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import jTunes.Resources;
import jTunes.database.AudioPlayer;

public class PlayButton extends CustomButton {
    private boolean playing;
    
    public PlayButton() {
        super(Resources.playIcon,
              ">",
              40,
              Color.LIGHT_GRAY,
              new Insets(0,1,0,1));
        
        playing = false;
    }
    
    public void toggle(AudioPlayer player) {
        if (!playing) {                 // that is, Button clicked to play song
            setIcon(Resources.pauseIcon, "||", 40);
            player.play();
            
            // revert to play icon on song end
            // and prepare to play again.
            Timer t = new Timer(0, null);
            ActionListener a = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (player.isCompleted()) {
                        PlayButton.this.setIcon(Resources.playIcon, ">", 40);
                        playing = !playing;
                        
                        player.beginning();
                        t.stop();
                    }
                }
            };
            t.addActionListener(a);
            t.setDelay(755);
            t.start();
        } else {                        // that is, Button clicked to pause song
            setIcon(Resources.playIcon, ">", 40);
            player.pause();
        }
        playing = !playing;
    }
}
