package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.*;

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
        
        JPanel buttons = new JPanel(new BorderLayout());
        CustomButton backButton = new CustomButton(Color.WHITE,
                                                   backIconPath,
                                                   "<<",
                                                   30);
        CustomButton playButton = new PlayButton();
        CustomButton forwardButton = new CustomButton(Color.WHITE,
                                                      forwardIconPath,
                                                      ">>",
                                                      30);
        Insets insets = new Insets(0, 1, 0, 1);
        backButton.setBorder(new EmptyBorder(insets));
        playButton.setBorder(new EmptyBorder(insets));
        forwardButton.setBorder(new EmptyBorder(insets));
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showConfirmDialog(null, "test");
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
