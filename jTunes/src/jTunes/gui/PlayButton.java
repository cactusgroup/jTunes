package jTunes.gui;

import java.awt.Color;
import java.awt.Insets;

public class PlayButton extends CustomButton {
    private static final String playIconPath = "../resources/playicon.png";
    private static final String pauseIconPath = "../resources/pauseicon.png";
    private boolean playing;
    
    public PlayButton() {
        super(playIconPath,
              ">",
              40,
              Color.WHITE,
              new Insets(0,1,0,1));
        
        playing = false;
    }
    
    public void toggleIcon() {
        if (!playing) {                 // that is, Button clicked to play song
            setIcon(pauseIconPath, "||", 40);
        } else {                        // that is, Button clicked to pause song
            setIcon(playIconPath,  ">", 40);
        }
        playing = !playing;
    }
}
