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
    
    public Footer(String currentSong) {
        this(currentSong, 350);
    }
    public Footer(String cS, int width) {
        currentSong = cS;
        
        setPreferredSize(new Dimension(width, 50));
        setBackground(Color.WHITE);
        
        setLayout(new BorderLayout());
        add(new JLabel(">"), BorderLayout.LINE_START);
        add(new JLabel("Playing: "+ currentSong), BorderLayout.CENTER);
    }
    
    public void setCurrentSong(String cS) {
        currentSong = cS;
    }
}
