package jTunes.gui;

import java.awt.Color;
import java.awt.Insets;

import jTunes.Resources;

public class PlayButton extends CustomButton {
    private boolean playing;
    
    public PlayButton() {
        super(Resources.playIcon,
              ">",
              40,
              Color.WHITE,
              new Insets(0,1,0,1));
        
        playing = false;
    }
    
    public void toggleIcon() {
        if (!playing) {                 // that is, Button clicked to play song
            setIcon(Resources.pauseIcon, "||", 40);
        } else {                        // that is, Button clicked to pause song
            setIcon(Resources.playIcon,  ">", 40);
        }
        playing = !playing;
    }
}
