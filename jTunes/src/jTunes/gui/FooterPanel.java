package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.*;

import jTunes.Resources;

/* 50 px high
 * +------------------------------+
 * | > Playing: [song title]      | 
 * +------------------------------+
 */

public class FooterPanel extends JPanel {
    private String currentSong = "[song title]";
    
    private JLabel message;
    
    public FooterPanel(String currentSong) {
        this(currentSong, 350);
    }
    public FooterPanel(String cS, int width) {
        // initialize current song
        currentSong = cS;
        
        // set up layout, size, and background color
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, 50));
        setBackground(Color.WHITE);
        
        // initialize playback controls
        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBorder(new MatteBorder(0, 2, 0, 2, Color.WHITE));
            Insets insets = new Insets(0, 1, 0, 1);
            CustomButton backButton = new CustomButton(
                    Resources.backIcon,
                    "\u00ab",
                    30,
                    Color.WHITE,
                    insets);
            PlayButton playButton = new PlayButton();
            CustomButton forwardButton = new CustomButton(
                    Resources.forwardIcon,
                    "\u00bb",
                    30,
                    Color.WHITE,
                    insets);
            playButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    playButton.toggleIcon();
                }
            });
        
        buttons.add(backButton, BorderLayout.LINE_START);
        buttons.add(playButton, BorderLayout.CENTER);
        buttons.add(forwardButton, BorderLayout.LINE_END);
        add(buttons, BorderLayout.LINE_START);
        
        message = new JLabel("Playing: " + currentSong);
        add(message, BorderLayout.CENTER);
    }
    
    public void setCurrentSong(String cS) {
        currentSong = cS;
        message.setText("Playing: " + currentSong);
    }
}
