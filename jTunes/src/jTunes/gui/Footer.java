package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

/* 50 px high
 * +------------------------------+
 * | > Playing: [song title]      | 
 * +------------------------------+
 */

public class Footer extends JPanel {
    private String currentSong = "[song title]";
    private String forwardIconPath = "../resources/forwardicon.png";
    private String backIconPath = "../resources/backicon.png";
    
    private JLabel message;
    
    public Footer(String currentSong) {
        this(currentSong, 350);
    }
    public Footer(String cS, int width) {
        currentSong = cS;
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, 50));
        setBackground(Color.WHITE);
        
        CustomButton playButton = new PlayButton();
        add(playButton, BorderLayout.LINE_START);
        
        message = new JLabel("Playing: " + currentSong);
        add(message, BorderLayout.CENTER);
    }
    
    public void setCurrentSong(String cS) {
        currentSong = cS;
        message.setText("Playing: " + currentSong);
    }
}
