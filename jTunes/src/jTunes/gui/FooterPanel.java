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
import jTunes.database.MP3Player_GUI;

/* 50 px high
 * +------------------------------+
 * | > Playing: [song title]      | 
 * +------------------------------+
 */

@SuppressWarnings("serial")
// This class defines the lower half of our GUI, where the play buttons
// and other functionalities are placed. 
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
        setBackground(Color.LIGHT_GRAY);
        
        // initialize playback controls
        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBorder(new MatteBorder(0, 2, 0, 2, Color.LIGHT_GRAY));
            Insets insets = new Insets(0, 1, 0, 1);
            CustomButton backButton = new CustomButton(
                    Resources.backIcon,
                    "\u00ab",
                    30,
                    Color.LIGHT_GRAY,
                    insets);
            PlayButton playButton = new PlayButton(); // Making a new Playbutton
            CustomButton forwardButton = new CustomButton(
                    Resources.forwardIcon,
                    "\u00bb",
                    30,
                    Color.LIGHT_GRAY,
                    insets);
            
            // registering mouse click listeners for each playback control button
            backButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    MP3Player_GUI.back5();
                }
            });
            playButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    playButton.toggle();
                }
            });
            forwardButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    MP3Player_GUI.fwd5();
                }
            });
        
        // add our playback controls to the "buttons" grouping pane 
        buttons.add(backButton, BorderLayout.WEST);
        buttons.add(playButton, BorderLayout.CENTER);
        buttons.add(forwardButton, BorderLayout.EAST);
        add(buttons, BorderLayout.WEST); // goes on the left
        
        // instantiate song title label
        message = new JLabel("Playing: " + currentSong);
        add(message, BorderLayout.CENTER); // goes in the center
    }
    
    // this method sets our JLabel's text
    public void setCurrentSong(String cS) {
        currentSong = cS; // Displays on the GUI the current song.
        message.setText("Playing: " + currentSong);
    }
    
}
