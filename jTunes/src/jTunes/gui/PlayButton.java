package jTunes.gui;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import jTunes.Resources;
import jTunes.database.MP3Player_GUI;

@SuppressWarnings("serial")
// This function deals with the Play button in our project. This activates playback and pausing
public class PlayButton extends CustomButton {
    private boolean playing; // boolean to keep track of playing song.
    
    // Constructor for playbutton.
    public PlayButton() {
        super(Resources.playIcon,
              ">",
              40,
              Color.LIGHT_GRAY,
              new Insets(0,1,0,1));
        
        playing = false; // initialize that playing is not active.
    }
    
    // our toggle function.
    public void toggle() {
        if (!playing) {                 // if its not playing, that is, Button clicked to play song
            setIcon(Resources.pauseIcon, "||", 40);
            MP3Player_GUI.play(); // Play the song.
            
            // Setting a timer to repeatedly test completion condition
            // without overwhelming the event handling thread with a loop
            Timer t = new Timer(0, null);
            ActionListener a = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // On song end, revert to play icon
                    // and prepare to play again.
                    if (MP3Player_GUI.isCompleted()) {
                        PlayButton.this.setIcon(Resources.playIcon, ">", 40);
                        playing = !playing; // set that playing is not active
                        // and set the seek time back to the beginning. 
                        MP3Player_GUI.backtoBeginning();
                        
                        // stop the repetition.
                        t.stop();
                    }
                }
            };
            t.addActionListener(a);
            t.setDelay(755); // every 755ms check the status.
            t.start();
        } else {                        // that is, Button clicked to pause song
            setIcon(Resources.playIcon, ">", 40); // Otherwise, set the icon to show pause.
            MP3Player_GUI.pause(); // pause playback
        }
        playing = !playing; // toggle our boolean 
    }
}
