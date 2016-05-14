package jTunes.gui;

import java.awt.Color;

public class PlayButton extends CustomButton {
    private static String playIconPath = "../resources/playicon.png";
    private static String pauseIconPath = "../resources/pauseicon.png";
    
    public PlayButton() {
        super(Color.WHITE,
              playIconPath,
              ">",
              40);
    }
}
